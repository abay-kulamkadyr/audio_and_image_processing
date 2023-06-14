package org.qulad;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerImage2 implements Initializable {
    public Canvas bitmap;
    public ImageView leftView;
    public ImageView rightView;
    public Button exitProgram;
    public Button next;
    private Scene scene;
    private String path= ControllerFileImage.path;

    public void exitEvent(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            BMP.instance(ControllerFileImage.path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            leftView.setFitWidth(BMP.instance(path).getImageWidth());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            leftView.setFitHeight(BMP.instance(path).getImageHeight());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            leftView.setImage(BMP.instance(path).getGreyScaleImage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            rightView.setFitWidth(BMP.instance(path).getImageWidth());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rightView.setFitHeight(BMP.instance(path).getImageHeight());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rightView.setImage(BMP.instance(path).getDitheredImage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void nextEvent(ActionEvent actionEvent) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/main/resources/org/qulad/ImageView3.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //scene = new Scene(root, 1600, 1100);
        //stage.setScene(scene);
        App.setRoot("ImageView3");
        stage.centerOnScreen();
        stage.show();
    }
}
