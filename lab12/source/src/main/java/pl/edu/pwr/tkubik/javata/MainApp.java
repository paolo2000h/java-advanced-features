package pl.edu.pwr.tkubik.javata;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainApp extends Application {

    private static final int NUM_ROWS = 20;
    private static final int NUM_COLUMNS = 35;
    private Engine engine = new Engine();
    private ArrayList<ArrayList<Boolean>> currentState;
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) throws ScriptException, FileNotFoundException {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        engine.loadScript("gameOfLife.js");

        currentState = new ArrayList<>();

        for (int row = 0; row < NUM_ROWS; row++) {
            ArrayList<Boolean> rowList = new ArrayList<>();
            for (int col = 0; col < NUM_COLUMNS; col++) {
                ToggleButton toggleButton = new ToggleButton();
                toggleButton.setPrefSize(10, 10);
                toggleButton.getStyleClass().add("custom-button");
                toggleButton.setSelected(false);
                int finalRow = row;
                int finalCol = col;
                toggleButton.setOnAction(event -> {
                    boolean selected = toggleButton.isSelected();
                    rowList.set(finalCol, selected);
                });
                rowList.add(false);
                gridPane.add(toggleButton, col, row);
            }
            currentState.add(rowList);
        }

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.BOTTOM_LEFT);

        vbox.getChildren().addAll(gridPane);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Button Grid");
        primaryStage.show();

        ToggleButton startButton = new ToggleButton("Start/Stop");
        startButton.setPrefSize(100, 50);
        startButton.setOnAction(event -> {
            if (startButton.isSelected()) {
                timeline.play();
            } else {
                timeline.stop();
            }
        });
        vbox.getChildren().add(startButton);
        VBox.setMargin(startButton, new Insets(10));

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            ArrayList<ArrayList<Boolean>> nextState = null;
            try {
                nextState = engine.iteration(currentState, 0, 0);
            } catch (ScriptException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            updateButtons(nextState, gridPane);
            currentState = nextState;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

    }

    private void updateButtons(ArrayList<ArrayList<Boolean>> nextState, GridPane gridPane) {
        int numRows = nextState.size();
        int numColumns = nextState.get(0).size();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                ToggleButton toggleButton = (ToggleButton) gridPane.getChildren().get(row * numColumns + col);
                boolean selected = nextState.get(row).get(col);
                toggleButton.setSelected(selected);
                int finalCol = col;
                int finalRow = row;
                toggleButton.setOnAction(event -> {
                    boolean newSelected = toggleButton.isSelected();
                    currentState.get(finalRow).set(finalCol, newSelected);
                });
            }
        }
    }
}
