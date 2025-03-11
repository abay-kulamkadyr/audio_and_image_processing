package org.qulad;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;

public class BMP {
    private static BMP theBMP = null;
    private final Image imageOriginal;
    private final int imageWidth;
    private final int imageHeight;
    private WritableImage imageBW;
    private WritableImage dithered;
    private WritableImage autoLevel;
    private int[] histogram;

    private BMP(String path) {
        File fileIn = new File(path);
        imageOriginal = new Image(fileIn.toURI().toString());
        imageWidth = (int) imageOriginal.getWidth();
        imageHeight = (int) imageOriginal.getHeight();
        applyGreyScaleTransform();
        applyDithering();
        autoLevelCheck();
    }

    public static BMP instance(String path) throws FileNotFoundException {
        if (theBMP == null) {
            theBMP = new BMP(path);
        }
        return theBMP;
    }

    public Image getOriginalImage() {
        return imageOriginal;
    }

    public void applyGreyScaleTransform() {
        int BW;
        PixelReader pixelReader = imageOriginal.getPixelReader();
        imageBW = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = imageBW.getPixelWriter();
        for (int cols = 0; cols < imageHeight; cols++) {
            for (int rows = 0; rows < imageWidth; rows++) {
                Color color = pixelReader.getColor(rows, cols);
                double r = color.getRed();
                double g = color.getGreen();
                double b = color.getBlue();
                int red = (int) (255 * r);
                int green = (int) (255 * g);
                int blue = (int) (255 * b);
                if (red > 255) {
                    red = 255;
                }
                if (green > 255) {
                    green = 255;
                }
                if (blue > 255) {
                    blue = 255;
                }
                BW = ((red) + (green) + (blue)) / 3;
                red = BW;
                green = BW;
                blue = BW;
                color = Color.rgb(red, green, blue);
                pixelWriter.setColor(rows, cols, color);
            }
        }
    }

    public void applyDithering() {
        int[][] ditherMatrix = {{0, 32, 8, 40, 2, 34, 10, 42}, {48, 16, 56, 24, 50, 18, 58, 26}, {12, 44, 4, 36, 14, 46, 6, 38}, {60, 28, 52, 20, 62, 30, 54, 22}, {3, 35, 11, 43, 1, 33, 9, 41}, {51, 19, 59, 27, 49, 17, 57, 25}, {15, 47, 7, 39, 13, 45, 5, 37}, {63, 31, 55, 23, 61, 29, 53, 21}};
        int dimensions = ditherMatrix.length;
        double rescale = 256.0 / (dimensions * dimensions);
        Color black = Color.rgb(0, 0, 0);
        Color white = Color.rgb(255, 255, 255);
        PixelReader pixelReader = imageBW.getPixelReader();
        dithered = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = dithered.getPixelWriter();
        int length = ditherMatrix.length;
        for (int cols = 0; cols < imageHeight; cols++) {
            for (int rows = 0; rows < imageWidth; rows++) {
                int i = cols % length;
                int j = rows % length;
                if ((int) (pixelReader.getColor(rows, cols).getBlue() * 255 / rescale) > ditherMatrix[i][j]) {
                    pixelWriter.setColor(rows, cols, white);
                } else {
                    pixelWriter.setColor(rows, cols, black);
                }
            }
        }
    }

    public WritableImage getGreyScaleImage() {
        return imageBW;
    }

    public WritableImage getDitheredImage() {
        return dithered;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    private int getMaxValue(int[] array) {
        int index = 255;
        int max = array[index];
        while (max == 0) {
            index--;
            max = array[index];
        }
        return index;
    }

    private int getMinValue(int[] array) {
        int index = 0;
        int min = array[index];

        while (min == 0) {
            index++;
            min = array[index];
        }
        return index;
    }

    public void stretchLevels() {

        int r, g, b;
        double[] holdCgYCo;
        int[] RGB;

        int minBrightness = getMinValue(histogram);
        int maxBrightness = getMaxValue(histogram);
        int contrast;

        contrast = maxBrightness - minBrightness;
        PixelReader pixelReaderOriginal = imageOriginal.getPixelReader();
        PixelWriter pixelWriter = autoLevel.getPixelWriter();
        for (int cols = 0; cols < imageHeight; cols++) {
            for (int rows = 0; rows < imageWidth; rows++) {
                // get the rgb value for the given pixel
                r = (int) (pixelReaderOriginal.getColor(rows, cols).getRed() * 255);
                g = (int) (pixelReaderOriginal.getColor(rows, cols).getGreen() * 255);
                b = (int) (pixelReaderOriginal.getColor(rows, cols).getBlue() * 255);

                // transform RGB values to YCoCg
                holdCgYCo = liftingMatrixMultiply(r, g, b);
                // apply scaling to the Y channel
                holdCgYCo[1] = ((holdCgYCo[1] * 255) - minBrightness) / contrast;
                // convert the result back to the RGB values
                RGB = convertBackToRGB(holdCgYCo[0], holdCgYCo[1], holdCgYCo[2]);
                // get the color from rgb
                Color color = Color.rgb(RGB[0], RGB[1], RGB[2]);
                pixelWriter.setColor(rows, cols, color);
            }
        }
    }

    public void autoLevelCheck() {
        int length = 256;
        histogram = new int[length];
        PixelReader pixelReader = imageBW.getPixelReader();

        autoLevel = new WritableImage(imageWidth, imageHeight);
        for (int cols = 0; cols < imageHeight; cols++) {
            for (int rows = 0; rows < imageWidth; rows++) {
                int i = (int) (pixelReader.getColor(rows, cols).getBlue() * 255);
                histogram[i]++;
            }
        }

        stretchLevels();
    }

    private int[] convertBackToRGB(double Cg, double Y, double Co) {
        int[] RGB = new int[3];
        double temp = Y - Cg;
        RGB[0] = (int) (temp + Co);
        RGB[1] = (int) (Y + Cg);
        RGB[2] = (int) (temp - Co);
        if (RGB[0] < 0) {
            RGB[0] = 0;
        }
        if (RGB[1] < 0) {
            RGB[1] = 0;
        }
        if (RGB[2] < 0) {
            RGB[2] = 0;
        }

        if (RGB[0] > 255) {
            RGB[0] = 255;
        }
        if (RGB[1] > 255) {
            RGB[1] = 255;
        }
        if (RGB[2] > 255) {
            RGB[2] = 255;
        }
        return RGB;
    }

    private double[] liftingMatrixMultiply(double R, double G, double B) {
        double[] CgYCo = new double[3];

        CgYCo[2] = R - B;
        double temp = B + CgYCo[2] / 2;
        CgYCo[0] = G - temp;
        CgYCo[1] = temp + CgYCo[0] / 2;

        return CgYCo;
    }

    public WritableImage getAutoLevel() {
        return autoLevel;
    }
}
