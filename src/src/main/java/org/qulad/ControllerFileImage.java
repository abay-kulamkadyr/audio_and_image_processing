package org.qulad;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerFileImage implements Initializable {
    public static String path;
    public Button bmpDialog;
    List<String> lstFile;

    public void bmpDialogEvent(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP files", lstFile));
        fileChooser.setTitle("Select a bmp file");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            path = file.getAbsolutePath();
            BMP.instance(path);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            App.setRoot("originalFileDisplay");
            stage.centerOnScreen();
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lstFile = new ArrayList<>();
        lstFile.add("*.bmp");
    }
}
