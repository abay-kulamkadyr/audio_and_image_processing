package org.qulad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class OriginalFileDisplay implements Initializable {
  public ImageView originalImage;
  public Button refresh;
  public Button exitBtn;
  private final String path = ControllerFileImage.path;

  public void refreshEvent(ActionEvent actionEvent) throws IOException {
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    App.setRoot("ImageView");
    stage.centerOnScreen();
    stage.show();
  }

  public void exitProgramEvent() {
    System.exit(0);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      originalImage.setImage(BMP.instance(path).getOriginalImage());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
