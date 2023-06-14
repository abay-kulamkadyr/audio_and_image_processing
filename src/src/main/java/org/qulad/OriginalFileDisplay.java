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

public class OriginalFileDisplay implements Initializable {
    private Scene scene;
    public ImageView oringinalImage;
    public Button refresh;
    public Button exitBtn;
    private String path= ControllerFileImage.path;

    public void refreshEvent(ActionEvent actionEvent) throws IOException {
       // Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/main/resources/org/qulad/ImageView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //scene = new Scene(root, 1600, 1100);
        //stage.setScene(scene);
        App.setRoot("ImageView");
        stage.centerOnScreen();
        stage.show();
    }

    public void exitProgramEvent(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            oringinalImage.setImage(BMP.instance(path).getOriginalImage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
