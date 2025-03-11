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

public class ControllerImage3 implements Initializable {
    private final String path = ControllerFileImage.path;
    public Button exitProgram;
    public ImageView leftView;
    public ImageView rightView;
    public Button next;

    public void exitEvent() {
        System.exit(0);
    }

    public void nextEvent(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        App.setRoot("originalFileDisplay");
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ControllerImage.splitTwoViews(leftView, path, rightView);
        try {
            rightView.setImage(BMP.instance(path).getAutoLevel());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
