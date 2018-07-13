package edu.dartmouth.ccnl.ridmp.controlpannel;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * Created by ccnl on 12/26/2014.
 */
public class RIDMPControlPanel extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tabs");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 250, Color.WHITE);

        TabPane tabPane = new TabPane();

        HBox hbox = new HBox();

        Tab metaDataTab = new Tab("Meta Data");
        metaDataTab.setContent(hbox);
        tabPane.getTabs().add(metaDataTab);

        Tab ruleTab = new Tab("Rules and Conditions");
        ruleTab.setContent(hbox);
        tabPane.getTabs().add(ruleTab);

        Tab assignmentTab = new Tab("Assignments");
        assignmentTab.setContent(hbox);
        tabPane.getTabs().add(assignmentTab);

        BorderPane borderPane = new BorderPane();

        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
