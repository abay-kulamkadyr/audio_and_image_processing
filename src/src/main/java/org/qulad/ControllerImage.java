package org.qulad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControllerImage implements Initializable {
  public Canvas bitmap;
  public ImageView leftView;
  public ImageView rightView;
  public Button exitProgram;
  public Button next;
  private Scene scene;
  private String path = ControllerFileImage.path;

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
      rightView.setImage(BMP.instance(path).getGreyScaleImage());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void exitEvent(ActionEvent actionEvent) {
    System.exit(0);
  }

  public void nextEvent(ActionEvent actionEvent) throws IOException {
    // Parent root =
    // FXMLLoader.load(getClass().getClassLoader().getResource("src/main/resources/org/qulad/ImageView2.fxml"));
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    // scene = new Scene(root, 1600, 1100);
    App.setRoot("ImageView2");
    // stage.setScene(scene);
    stage.centerOnScreen();
    stage.show();
  }
}
