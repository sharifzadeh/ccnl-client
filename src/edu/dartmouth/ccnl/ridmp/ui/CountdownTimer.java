package edu.dartmouth.ccnl.ridmp.ui;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CountdownTimer extends Application {
    @Override public void start(final Stage stage) throws Exception {
        final CountDown      countdown       = new CountDown(10);
        final CountDownLabel countdownLabel  = new CountDownLabel(countdown);

        final Button         countdownButton = new Button("  Start  ");
        countdownButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                countdownButton.setText("Restart");
                countdown.start();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(countdownLabel, countdownButton);
        layout.setAlignment(Pos.BASELINE_RIGHT);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 20; -fx-font-size: 20;");

        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}

class CountDownLabel extends Label {
    public CountDownLabel(final CountDown countdown) {
        textProperty().bind(Bindings.format("%3d", countdown.timeLeftProperty()));
    }
}

class CountDown {
    private final ReadOnlyIntegerWrapper timeLeft;
    private final ReadOnlyDoubleWrapper  timeLeftDouble;
    private final Timeline               timeline;

    public ReadOnlyIntegerProperty timeLeftProperty() {
        return timeLeft.getReadOnlyProperty();
    }

    public CountDown(final int time) {
        timeLeft       = new ReadOnlyIntegerWrapper(time);
        timeLeftDouble = new ReadOnlyDoubleWrapper(time);

        timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(timeLeftDouble, time)
                ),
                new KeyFrame(
                        Duration.seconds(time),
                        new KeyValue(timeLeftDouble, 0)
                )
        );

        timeLeftDouble.addListener(new InvalidationListener() {
            @Override public void invalidated(Observable o) {
                timeLeft.set((int) Math.ceil(timeLeftDouble.get()));
            }
        });
    }

    public void start() {
        timeline.playFromStart();
    }
}