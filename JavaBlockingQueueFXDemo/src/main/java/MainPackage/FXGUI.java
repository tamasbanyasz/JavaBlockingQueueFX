package MainPackage;

import CounterPackage.Counter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXGUI extends Application {

    private XYChart.Series<String, Number> mainSeries;
    private XYChart.Series<String, Number> deductedSeries;

    @Override
    public void start(Stage primaryStage) {
        Counter counter = Main.getCounter();
        Counter deductedCounter = Main.getDeductedCounter();

        // Diagram 1 – Original data
        BarChart<String, Number> chartMain = createChart("Real-time value", mainSeries = new XYChart.Series<>());
        mainSeries.setName("Summerized value");
        addDefaultData(mainSeries);

        // Diagram 2 – Subtracted values
        BarChart<String, Number> chartDeducted = createChart("Subtracted value", deductedSeries = new XYChart.Series<>());
        deductedSeries.setName("Substracted value");
        addDefaultData(deductedSeries);

        
        TextField intField = new TextField("0");
        TextField floatField = new TextField("0");
        TextField doubleField = new TextField("0");

        Button deductButton = new Button("Substraction");
        deductButton.setOnAction(event -> {
        	try {
        		int intVal = Integer.parseInt(intField.getText());
        		float floatVal = Float.parseFloat(floatField.getText());
        		double doubleVal = Double.parseDouble(doubleField.getText());

        		if (counter.getAtomicInteger() >= intVal &&
        		    counter.getAtomicFloat() >= floatVal &&
        		    counter.getAtomicDouble() >= doubleVal) {

        		    // Subtract
        		    counter.subtractAtomicValue(intVal);
        		    counter.subtractAtomicFloat(floatVal);
        		    counter.subtractAtomicDouble(doubleVal);

        		    // Add to other Counter
        		    deductedCounter.addAtomicValue(intVal);
        		    deductedCounter.addAtomicFloat(floatVal);
        		    deductedCounter.addAtomicDouble(doubleVal);
        		} else {
        		    System.out.println("Not enough value ...");
        		}

        	} catch (NumberFormatException ex) {
        	    System.out.println("Wrong format");
        	}

        });

        HBox inputRow = new HBox(10,
                new VBox(new Label("Integer"), intField),
                new VBox(new Label("Float"), floatField),
                new VBox(new Label("Double"), doubleField),
                deductButton
        );
        inputRow.setPadding(new Insets(10));

        HBox charts = new HBox(20, chartMain, chartDeducted);
        VBox root = new VBox(20, charts, inputRow);
        Scene scene = new Scene(root, 900, 500);

        // Refresh timer
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            mainSeries.getData().get(0).setYValue(counter.getAtomicInteger()); 
            mainSeries.getData().get(1).setYValue((double) counter.getAtomicFloat()); 
            mainSeries.getData().get(2).setYValue(counter.getAtomicDouble()); // 
            deductedSeries.getData().get(0).setYValue(deductedCounter.getAtomicInteger());
            deductedSeries.getData().get(1).setYValue((double) deductedCounter.getAtomicFloat());
            deductedSeries.getData().get(2).setYValue(deductedCounter.getAtomicDouble());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Real-time values operate");
        primaryStage.show();
    }

    private BarChart<String, Number> createChart(String title, XYChart.Series<String, Number> series) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle(title);
        xAxis.setLabel("Type");
        yAxis.setLabel("Value");
        chart.getData().add(series);
        chart.setLegendVisible(false);
        chart.setMinWidth(400);
        return chart;
    }

    private void addDefaultData(XYChart.Series<String, Number> series) {
        series.getData().add(new XYChart.Data<>("Integer", 0));
        series.getData().add(new XYChart.Data<>("Float", 0));
        series.getData().add(new XYChart.Data<>("Double", 0));
    }
}
