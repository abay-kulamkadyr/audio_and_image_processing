package org.qulad;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerFileAudio implements Initializable {

    public static String path;
    List<String> lstFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lstFile = new ArrayList<>();
        lstFile.add("*.wav");
    }

    public void openFileDialog(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wav files", lstFile));
        fileChooser.setTitle("Select a wav file");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            path = file.getAbsolutePath();
            WAVE.instance(path).read();
            if (WAVE.instance(path).getNumChannels() == 1) {

                App.setRoot("mono");
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                // stage.setScene(scene);                s
                stage.centerOnScreen();
                stage.show();
            } else {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                App.setRoot("stereo");
                stage.centerOnScreen();
                stage.show();
            }
        }
    }
}
