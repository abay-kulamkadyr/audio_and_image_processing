package org.qulad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMono implements Initializable  {

    public Button applyFadeTransform;
    public Button SelectNewWave;
    public Button MoveToPart2;
    public AnchorPane anchorPane;
    public XYChart.Series<Double,Double> monoSeries;
    public TextArea report;
    public Button exit;
    private Scene scene;
    @FXML
    private AreaChart<Double, Double> linechart;
    String path= ControllerFileAudio.path;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        linechart.setCreateSymbols(false);
        linechart.getXAxis().setTickLabelsVisible(false);
        monoSeries= WAVE.instance(path).getMonoSeries();
        monoSeries.setName("Audio wave");
        linechart.getData().add(monoSeries);
        report.setText("Number of samples: "+ WAVE.instance(path).getTotalNumberOfSamples()+"\n"+"Sampling frequency: "+ WAVE.instance(path).getSamplingFrequency()+"Hz");
        report.setEditable(false);
        report.setFont(Font.font("Helvetica", FontWeight.BOLD,13));
    }

    public void applyTransformBtnAction(ActionEvent actionEvent) {
        XYChart.Series<Double,Double> monoSeries= WAVE.instance(path).getMonoTransformedSeries();
        monoSeries.setName("Transformed audio using Fade in and Fade out");
        linechart.getData().add(monoSeries);
    }


    public void moveToPart2Action(ActionEvent actionEvent) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/main/resources/org/qulad/fileChooserImage.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //scene = new Scene(root, 300, 200);
        //stage.setScene(scene);
        App.setRoot("fileChooserImage");
        stage.centerOnScreen();
        stage.show();
    }

    public void exitEvent(ActionEvent actionEvent) {
        System.exit(0);
    }
}
