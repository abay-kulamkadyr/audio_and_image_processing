package org.qulad;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerImage2 implements Initializable {
    private final String path = ControllerFileImage.path;
    public ImageView leftView;
    public ImageView rightView;
    public Button exitProgram;
    public Button next;

    public void exitEvent() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerImage.openImage(leftView, path);
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
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        App.setRoot("ImageView3");
        stage.centerOnScreen();
        stage.show();
    }
}
