package org.qulad;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStereo implements Initializable {
    public AreaChart<Double, Double> leftChannelPlot;
    public AreaChart<Double, Double> rightChannelPlot;
    public Button applyTransFromBtn;
    public TextArea report;
    public Button exitBtn;
    public Button moveToPart2;
    String path = ControllerFileAudio.path;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            WAVE.instance(path).read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        leftChannelPlot.setTitle("Left channel");
        rightChannelPlot.setTitle("Right channel");
        System.out.println(WAVE.instance(path).getNumChannels());
        leftChannelPlot.setCreateSymbols(false);
        leftChannelPlot.getXAxis().setTickLabelsVisible(false);
        XYChart.Series<Double, Double> leftSeries = WAVE.instance(path).getLeftChannelSeries();
        leftSeries.setName("Left Channel");
        leftChannelPlot.getData().add(leftSeries);

        rightChannelPlot.setCreateSymbols(false);
        rightChannelPlot.getXAxis().setTickLabelsVisible(false);
        XYChart.Series<Double, Double> rightSeries = WAVE.instance(path).getRightChannelSeries();
        rightSeries.setName("Right channel");
        rightChannelPlot.getData().add(WAVE.instance(path).getRightChannelSeries());
        report.setText("Number of samples: " + WAVE.instance(path).getTotalNumberOfSamples() + "\n" + "Sampling frequency: " + WAVE.instance(path).getSamplingFrequency() + "Hz");
        report.setEditable(false);
        report.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
    }

    public void transformBtnEvent() {
        XYChart.Series<Double, Double> leftSeries = WAVE.instance(path).getLeftTransformedSeries();
        leftSeries.setName("Transformed left channel using Fade in and Fade out");
        leftChannelPlot.getData().add(leftSeries);
        XYChart.Series<Double, Double> rightSeries = WAVE.instance(path).getRightTransformedSeries();
        rightSeries.setName("Transformed right channel using Fade in and Fade out");
        rightChannelPlot.getData().add(rightSeries);
    }

    public void exitEvent() {
        System.exit(0);
    }

    public void movetoPart2Event(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        App.setRoot("fileChooserImage");
        stage.centerOnScreen();
        stage.show();
    }
}
