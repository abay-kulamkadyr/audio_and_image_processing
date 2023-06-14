package org.qulad;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerImage3 implements Initializable {
    public Button exitProgram;
    public ImageView leftView;
    public ImageView rightView;
    public Button next;
    private Scene scene;
    private String path= ControllerFileImage.path;

    public void exitEvent(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void nextEvent(ActionEvent actionEvent) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/main/resources/org/qulad/originalFileDisplay.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //scene = new Scene(root, 1600, 1100);
        //stage.setScene(scene);
        App.setRoot("originalFileDisplay");
        stage.centerOnScreen();
        stage.show();
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
            leftView.setImage(BMP.instance(path).getOriginalImage());
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
            rightView.setImage(BMP.instance(path).getAutoLevel());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
