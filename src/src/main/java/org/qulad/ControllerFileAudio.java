package org.qulad;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControllerFileAudio implements Initializable {

  public AnchorPane chooseFileBtn;
  List<String> lstFile;

  private Scene scene;

  public static String path;

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
        // Parent root =
        // FXMLLoader.load(getClass().getClassLoader().getResource("src/main/resources/org/qulad/stereo.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // scene = new Scene(root, 1200, 1000);
        App.setRoot("stereo");
        // stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
      }
    }
  }
}
