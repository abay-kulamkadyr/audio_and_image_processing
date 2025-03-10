package org.qulad;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javafx.scene.chart.XYChart;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WAVE {
  private final int bytesPerFrame;
  private final int totalFrameLength;
  private AudioInputStream audioInputStream;
  private final int numChannels;
  private int[] leftChannel;
  private int[] rightChannel;
  private final int samplingFrequency;
  private int totalNumberOfSamples;
  private static final int SAMPLES_PER_PIXEL = 50;
  private static final int MONO_CHANNEL = 1;
  private static final int DUAL_CHANNEL = 2;
  private XYChart.Series<Double, Double> monoSeries;
  private XYChart.Series<Double, Double> leftChannelSeries;
  private XYChart.Series<Double, Double> rightChannelSeries;
  private XYChart.Series<Double, Double> monoTransformedSeries;
  private XYChart.Series<Double, Double> leftTransformedSeries;
  private XYChart.Series<Double, Double> rightTransformedSeries;

  AudioFormat decoded;
  private static WAVE theWave = null;

  private WAVE(String path) {
    File fileIn = new File(path);
    try {
      audioInputStream = AudioSystem.getAudioInputStream(fileIn);
    } catch (UnsupportedAudioFileException | IOException e) {
      e.printStackTrace();
    }
    AudioFormat audioFormat = audioInputStream.getFormat();
    numChannels = audioInputStream.getFormat().getChannels();
    float frameRate = audioFormat.getFrameRate();
    float sampleRate = audioInputStream.getFormat().getSampleRate();
    bytesPerFrame = audioFormat.getFrameSize();
    totalFrameLength = (int) audioInputStream.getFrameLength();
    AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_UNSIGNED;
    decoded =
        new AudioFormat(
            encoding,
            sampleRate,
            audioInputStream.getFormat().getSampleSizeInBits(),
            numChannels,
            bytesPerFrame,
            frameRate,
            false);

    samplingFrequency = (int) audioInputStream.getFormat().getSampleRate();

    if (numChannels == MONO_CHANNEL) {
      totalNumberOfSamples = totalFrameLength;
    } else if (numChannels == DUAL_CHANNEL) {
      totalNumberOfSamples = totalFrameLength * 2;
    }
  }

  public static WAVE instance(String path) {
    if (theWave == null) {
      theWave = new WAVE(path);
    }
    return theWave;
  }

  public void read() throws IOException {
    int[] monoChannel;
    try (AudioInputStream audioInputStreamDecoded =
        AudioSystem.getAudioInputStream(decoded, audioInputStream)) {

      byte[] rawData = new byte[bytesPerFrame * totalFrameLength];
      monoChannel = new int[totalFrameLength];

      while (audioInputStreamDecoded.read(rawData) != -1) {
        long value;
        int start = 0;
        int end = bytesPerFrame;
        leftChannel = new int[totalFrameLength];
        rightChannel = new int[totalFrameLength];
        byte[] leftChannelBytes;
        byte[] rightChannelBytes;
        long leftChannelValue;
        long rightChannelValue;

        for (int i = 0; i < totalFrameLength; i++) {
          value = 0;
          byte[] arrayFrameSlice = Arrays.copyOfRange(rawData, start, end);

          if (numChannels == DUAL_CHANNEL) {
            leftChannelValue = 0;
            rightChannelValue = 0;
            leftChannelBytes = Arrays.copyOfRange(arrayFrameSlice, 0, (bytesPerFrame / 2));
            rightChannelBytes =
                Arrays.copyOfRange(arrayFrameSlice, bytesPerFrame / 2, arrayFrameSlice.length);

            // convert the bytes to their corresponding values
            for (int j = 0; j < leftChannelBytes.length; j++) {
              leftChannelValue += ((long) leftChannelBytes[j] & 0xffL) << (8 * j);
              rightChannelValue += ((long) rightChannelBytes[j] & 0xffL) << (8 * j);
            }
            leftChannel[i] = (int) leftChannelValue;
            rightChannel[i] = (int) rightChannelValue;
          } else if (numChannels == MONO_CHANNEL) {
            // Since little endian, interpret least array pos as least sig byte
            for (int j = 0; j < arrayFrameSlice.length; j++) {
              value += ((long) arrayFrameSlice[j] & 0xffL) << (8 * j);
            }
            monoChannel[i] = (int) value;
          }

          start += bytesPerFrame;
          end += bytesPerFrame;
        }
      }
    }
    if (numChannels == DUAL_CHANNEL) {
      leftChannelSeries = calculateSeries(leftChannel);
      rightChannelSeries = calculateSeries(rightChannel);
      double[] leftTransformedValues = applyFade(leftChannel);
      double[] rightTransformedValues = applyFade(rightChannel);
      leftTransformedSeries = calculateSeries(leftTransformedValues);
      rightTransformedSeries = calculateSeries(rightTransformedValues);

    } else if (numChannels == MONO_CHANNEL) {
      monoSeries = calculateSeries(monoChannel);
      double[] monoTransformedValues = applyFade(monoChannel);
      monoTransformedSeries = calculateSeries(monoTransformedValues);
    }
  }

  /*
     Creates an array in the range [min, man] (equally spaced)
  */
  private static double[] linSpace(double min, double max, int numPoints) {
    double[] ret = new double[numPoints];
    for (int i = 0; i < numPoints; i++) {
      ret[i] = min + i * (max - min) / (numPoints - 1);
    }
    return ret;
  }

  public double[] applyFade(int[] channel) {
    int half = channel.length / 2;
    double[] transformed = new double[channel.length];
    double[] dB = linSpace(-20, 0, half);
    for (int i = 0; i < half; i++) {
      transformed[i] = channel[i] * Math.pow(10, dB[i] / 20.0);
    }
    double[] dB1 = linSpace(0, -20, half);
    for (int i = 0; i < half; i++) {
      transformed[i + half] = channel[i + half] * Math.pow(10, dB1[i] / 20.0);
    }
    return transformed;
  }

  public XYChart.Series<Double, Double> calculateSeries(int[] channel) {
    double[] normalize = new double[channel.length];

    int max_value = Arrays.stream(channel).max().getAsInt();
    int min_value = Arrays.stream(channel).min().getAsInt();

    // normalize values between [-0.5,0.5]
    for (int i = 0; i < normalize.length; i++) {
      normalize[i] = (((channel[i] - min_value) * 1.0) / (max_value - min_value)) - 0.5;
    }

    // get the averages to get a smaller array
    double[] averages = new double[normalize.length / SAMPLES_PER_PIXEL];

    int currentSampleCounter = 0;
    int arrayCellPosition = 0;
    float currentCellValue = 0.0f;
    for (double v : normalize) {
      if (currentSampleCounter < SAMPLES_PER_PIXEL - 1) {
        currentSampleCounter++;
        currentCellValue += v;

      } else {
        averages[arrayCellPosition] = currentCellValue / currentSampleCounter;
        currentSampleCounter = 0;
        currentCellValue = 0;
        arrayCellPosition++;
      }
    }

    XYChart.Series<Double, Double> series = new XYChart.Series<>();
    for (int i = 0; i < averages.length; i++) {
      series.getData().add(new XYChart.Data<>(i * 10000.0, averages[i]));
    }

    return series;
  }

  public XYChart.Series<Double, Double> calculateSeries(double[] channel) {
    double[] normalize = new double[channel.length];
    // normalize values between [-0.5,0.5]

    // zi = (xi – min(x)) / (max(x) – min(x))
    double max_value = Arrays.stream(channel).max().getAsDouble();
    double min_value = Arrays.stream(channel).min().getAsDouble();

    for (int i = 0; i < normalize.length; i++) {
      normalize[i] = (channel[i] - min_value) / (max_value - min_value) - 0.5;
    }

    // get the averages to get a smaller array
    double[] averages = new double[normalize.length / SAMPLES_PER_PIXEL];

    int currentSampleCounter = 0;
    int arrayCellPosition = 0;
    float currentCellValue = 0.0f;

    for (double v : normalize) {

      if (currentSampleCounter < SAMPLES_PER_PIXEL - 1) {
        currentSampleCounter++;
        currentCellValue += v;

      } else {
        averages[arrayCellPosition] = currentCellValue / currentSampleCounter;
        arrayCellPosition++;
        currentSampleCounter = 0;
        currentCellValue = 0;
      }
    }
    XYChart.Series<Double, Double> series = new XYChart.Series<>();

    series.setName("audio wave");
    for (int i = 0; i < averages.length; i++) {
      series.getData().add(new XYChart.Data<>(i * 10000.0, averages[i]));
    }
    return series;
  }

  public XYChart.Series<Double, Double> getMonoSeries() {
    return monoSeries;
  }

  public XYChart.Series<Double, Double> getLeftChannelSeries() {
    return leftChannelSeries;
  }

  public XYChart.Series<Double, Double> getRightChannelSeries() {
    return rightChannelSeries;
  }

  public XYChart.Series<Double, Double> getMonoTransformedSeries() {
    return monoTransformedSeries;
  }

  public XYChart.Series<Double, Double> getLeftTransformedSeries() {
    return leftTransformedSeries;
  }

  public XYChart.Series<Double, Double> getRightTransformedSeries() {
    return rightTransformedSeries;
  }

  public int getNumChannels() {
    return numChannels;
  }

  public int getTotalNumberOfSamples() {
    return totalNumberOfSamples;
  }

  public int getSamplingFrequency() {
    return samplingFrequency;
  }
}
