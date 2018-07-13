package edu.dartmouth.ccnl.ridmp.ui;

import edu.dartmouth.ccnl.ridmp.conf.Configuration;
import edu.dartmouth.ccnl.ridmp.dto.PersonTO;
import edu.dartmouth.ccnl.ridmp.dto.ScheduleTO;
import edu.dartmouth.ccnl.ridmp.dto.TargetTO;
import edu.dartmouth.ccnl.ridmp.service.command.CommandInterface;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

/**
 *
 * Created by ccnl on 12/31/2014.
 */
public class PersonAuthForm extends Application {
    private static Registry transactionRegistry;
    private static final ThreadLocal<CommandInterface> transactionRegistryThreadLocal =
            new ThreadLocal<CommandInterface>();

    // 6+yx/.

    private static CommandInterface commandInterface;

    private boolean result;

    private static void lookupRegistry() {
        try {
            if (transactionRegistry == null)
                transactionRegistry = LocateRegistry.getRegistry(Configuration.REMOTE_HOST, Configuration.REMOTE_PORT);
            commandInterface = (CommandInterface) transactionRegistry.lookup(Configuration.REMOTE_ID);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void showDialog(String message) {
        final Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);  // Override default
        grid.setStyle("-fx-background-color: black");

        Scene scene = new Scene(grid, 220, 80); // Manage scene size
        dialogStage.setTitle("Authentication");
        Label label = new Label(message);
        label.setStyle("-fx-text-fill: yellow");
        grid.addRow(0, label);

        Button btnConfirm = new Button("OK");
        btnConfirm.setStyle("-fx-text-fill: crimson");
        grid.addRow(1);
        grid.addColumn(0, btnConfirm);
        btnConfirm.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                dialogStage.close();
            }
        });

        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private void reversalDialog(String message, final Stage uiStage, final CommandInterface commandInterface, final PersonTO personTO, final ScheduleTO scheduleTO) {
        final Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);  // Override default
        grid.setStyle("-fx-background-color: black");

        Scene scene = new Scene(grid, 220, 100); // Manage scene size
        dialogStage.setTitle("Authentication");
        Label label = new Label(message);
        label.setStyle("-fx-text-fill: yellow");
        grid.addRow(0, label);

        Button btnConfirm = new Button("Yes");
        btnConfirm.setStyle("-fx-text-fill: crimson");
        grid.addRow(1);
        grid.addColumn(0, btnConfirm);
        btnConfirm.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                    RidUI.getInstance().init(uiStage);
                dialogStage.close();
            }
        });

        Button btnCancel = new Button("No");
        btnCancel.setStyle("-fx-text-fill: crimson");
        grid.addColumn(2, btnCancel);
        btnCancel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                result = false;
                dialogStage.close();
            }
        });

        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @Override
    public void start(final Stage stage) throws Exception {

        lookupRegistry();

        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: black");

        grid.setAlignment(Pos.CENTER);  // Override default
        grid.setHgap(10);
        grid.setVgap(12);

        final Label[] labels = new Label[2];

        String[] labelValues = {"DID", "Ticket"};
        for (int i = 0; i < 2; i++) {
            labels[i] = new Label(labelValues[i]);
            labels[i].setStyle("-fx-text-fill: green");
        }

        final TextField[] textFields = new TextField[2];
        for (int i = 0; i < 2; i++) {
            textFields[i] = new TextField();
            //textFields[i].setStyle("-fx-text-fill: white;");
        }

        int c = 0;
        for (int i = 0; i < 2; i++) {
            grid.addRow(i, labels[c]);
            grid.addColumn(1, textFields[c++]);
        }

        final CheckBox term = new CheckBox();
        term.setText("Accept Terms and Conditions");
        term.setStyle("-fx-text-fill: white");
//        grid.addRow(5);
        grid.addRow(3);
        grid.addColumn(1, term);

        Button btnConfirm = new Button("Confirm");
        btnConfirm.setStyle("-fx-text-fill: green");
        grid.addRow(4);
        grid.addColumn(1, btnConfirm);

        btnConfirm.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                String did = textFields[0].getText().trim().toLowerCase();
                String pin = textFields[1].getText().trim();

                if (did.length() == 0 || pin.length() == 0) {
                    showDialog("You must enter your DID and pin.");
                    return;
                }

                boolean acceptedTerm = term.isSelected();
                if (!acceptedTerm) {
                    showDialog("You must accept terms and conditions!");
                    return;
                }

                PersonTO personTO = new PersonTO();
                personTO.setdId(did);
                personTO.setTicket(pin);

                try {
                     personTO = commandInterface.authorized(personTO);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (personTO != null && personTO.isAuthorized()) {
                    personTO.setAuthorizedDate(new Date());

                    Stage uiStage = new Stage();
                    uiStage.initStyle(StageStyle.TRANSPARENT);

                    TargetTO targetTO = new TargetTO();
                    targetTO.setPerId(personTO.getPerId());

                    ScheduleTO scheduleTO = null;
                    try {
                        scheduleTO = commandInterface.loadSchedule();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    if (scheduleTO == null) {
                        showDialog("No schedule data found!");
                        return;
                    }

//                    TODO
                    RidUI.getInstance().setCommandInterface(commandInterface);
                    RidUI.getInstance().setPerson(personTO);
                    RidUI.getInstance().setSchedule(scheduleTO);
//                    RidUI.getInstance().setStage(stage);

                    int lastAttemptId = 0;
                    try {
                        lastAttemptId = commandInterface.lastAttemptedPerson(personTO.getPerId());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    lastAttemptId++;
                    RidUI.getInstance().setAttemptedId(lastAttemptId);
                    RidUI.getInstance().init(uiStage);
//                    RidUI.getInstance().start(uiStage, commandInterface, personTO, scheduleTO);

                    stage.close();
                } else
                    showDialog("Invalid DID or Ticket! Please try again.");

            }
        });

        Scene scene = new Scene(grid, 500, 250); // Manage scene size
        stage.setTitle("Computational Cognitive Neuroscience Lab - Authentication Service");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(PersonAuthForm.class, args);
    }
}
