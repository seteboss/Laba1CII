package org.openjfx;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import service.AnnealingAlgorithm;

public class PrimaryController {

    @FXML
    private TextField numberOfQueens;

    @FXML
    private TextField tStart;

    @FXML
    private TextField tFinish;

    @FXML
    private TextField alpha;

    @FXML
    private TextField step;

    @FXML
    private TextField outputConf;

    @FXML
    private TextField inputArr;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button startButton;

    @FXML
    private LineChart<Number,Number> graph;

    @FXML
    private NumberAxis axisX;

    @FXML
    void initialize(){

        AnnealingAlgorithm annealingAlgorithm = new AnnealingAlgorithm();

        startButton.setOnAction(actionEvent -> {
            try {

                outputConf.clear();
                inputArr.clear();
                XYChart.Series<Number, Number> numberSeries = initGraph();
                annealingAlgorithm.setN(Integer.parseInt(numberOfQueens.getText()));
                annealingAlgorithm.setInitialTemperature(Double.parseDouble(tStart.getText()));
                annealingAlgorithm.setFinalTemperature(Double.parseDouble(tFinish.getText()));
                annealingAlgorithm.setAlpha(Double.parseDouble(alpha.getText()));
                annealingAlgorithm.setStep(Integer.parseInt(step.getText()));
                annealingAlgorithm.calculate();
                renderGridPane(annealingAlgorithm.getN());
                List<Integer> accommodation = annealingAlgorithm.getBest().getAccommodation();
                for (int i = 0; i < annealingAlgorithm.getN(); i++) {
                    Label label = new Label("X");
                    GridPane.setHgrow(label, Priority.SOMETIMES);
                    GridPane.setVgrow(label, Priority.SOMETIMES);
                    gridPane.add(label, i, accommodation.get(i));
                }
                outputConf.appendText(((Integer)annealingAlgorithm.getBest().getEnergy()).toString());
                inputArr.appendText(annealingAlgorithm.getBest().getAccommodation().toString());
                renderGraph(numberSeries, annealingAlgorithm);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

    }

    XYChart.Series<Number,Number> initGraph(){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("X - temperature; Y - energy");
        axisX.setAutoRanging(false);
        axisX.setTickLabelFormatter(new NumberAxis.DefaultFormatter(axisX) {
            @Override
            public String toString(Number value) {
                return String.format("%7.1f", -value.doubleValue());
            }
        });
        return series;
    }

    void renderGraph(XYChart.Series<Number,Number> series,  AnnealingAlgorithm annealingAlgorithm){
        series.getData().clear();
        graph.getData().clear();
        for (int i = 0; i < annealingAlgorithm.getEnergies().size(); i++){
            series.getData().add(new XYChart.Data<>(annealingAlgorithm.getTemperatures().get(i), annealingAlgorithm.getEnergies().get(i)));
        }
        double lowerBound = annealingAlgorithm.getTemperatures().get(0);
        double upperBound = annealingAlgorithm.getTemperatures().get(annealingAlgorithm.getEnergies().size()-1);
        upperBound += (upperBound - lowerBound)/50;
        axisX.setLowerBound(lowerBound);
        axisX.setUpperBound(upperBound);
        graph.getData().add(series);
    }

    //отрисовка сетки для поля
    private void renderGridPane(int size) {
        gridPane.setGridLinesVisible(false);
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getChildren().clear();
        for (int i = 0; i < size; i++) {
            gridPane.getColumnConstraints()
                .add(new ColumnConstraints(-1, -1, -1, Priority.ALWAYS, HPos.CENTER, false));
            gridPane.getRowConstraints()
                .add(new RowConstraints(-1, -1, -1, Priority.ALWAYS, VPos.CENTER, false));
        }
        gridPane.setGridLinesVisible(true);
    }
}
