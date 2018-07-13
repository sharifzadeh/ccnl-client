package edu.dartmouth.ccnl.ridmp.ui;

import com.sun.glass.ui.*;
import edu.dartmouth.ccnl.ridmp.Contrast;
import edu.dartmouth.ccnl.ridmp.GaborShape;
import edu.dartmouth.ccnl.ridmp.ShapeConfiguration;
import edu.dartmouth.ccnl.ridmp.ShapeUtil;
import edu.dartmouth.ccnl.ridmp.conf.Configuration;
import edu.dartmouth.ccnl.ridmp.dto.FeatureTargetAwardTO;
import edu.dartmouth.ccnl.ridmp.dto.PersonTO;
import edu.dartmouth.ccnl.ridmp.dto.RewardTO;
import edu.dartmouth.ccnl.ridmp.dto.ScheduleTO;
import edu.dartmouth.ccnl.ridmp.rules.FeatureReward;
import edu.dartmouth.ccnl.ridmp.rules.Reward;
import edu.dartmouth.ccnl.ridmp.service.command.CommandInterface;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

/**
 * Created by amir on 12/19/2014.
 */
public class RidUI {

    private int row = 6;
    private int col = 8;
    private int size;

    private Timer rewardTimer;
    private Timer restTimer;
    private volatile boolean a = false;

    private int startBlock;

    int c = 0;

    private int totalShapeCounts = 0;

    private int selectedShapeIndex;

    private boolean visible = true;
    private boolean isRest = false;
    private boolean inRest = false;

    boolean check = true;

    private static ShapeConfiguration shapeConfiguration;

    private static Registry transactionRegistry;
    private static final ThreadLocal<CommandInterface> transactionRegistryThreadLocal =
            new ThreadLocal<CommandInterface>();

    private Stage stage;
    private Scene scene;
    private Group root;
    private Canvas canvas;

    boolean myFlag;

    private static Image imageAward;

    private static GaborShape offImage;

    private static RidUI instance;

    private int shapeChooseCount;

    private long tsl = 20000;

    private VBox vb = null;

    int trialCount = 1;
    int blockCount = 1;

    URI beep;

    Media media = null;

    private boolean featureAlarm = true;

    private int selectedShapeNumberForReversal;

    private int numberOfReversal = 0;

    private int previousNumberForReversal = 0;

    private Toolkit kit;

    private Cursor cursor;

    private static Image cursorImage;

    private static ImageCursor imageCursor;

    private boolean userDecided = false;

    private boolean isExperiment = false;

    private int shapeChosen = 0;

    private int renewCount = 0;

    private boolean isTraining = true;

    private int trainingCount = 0;

    private boolean startedTraining = true;

    private String currentFeature;
    private double currentProbability;
    private int currentProbabilityIndex;

    private boolean clikcedOnRest = false;

    Contrast[] contrasts = new Contrast[Configuration.length];

    private List<FeatureTargetAwardTO> updateTargets = new ArrayList<FeatureTargetAwardTO>();

    boolean state = false;

    private int attemptedId;

    private CommandInterface commandInterface;
    private PersonTO personTO;
    private ScheduleTO scheduleTO;

    private boolean restAnswer=false;

    private RidUI() {
        if (media == null)
            media = new Media("http://ccnl.dartmouth.edu:8080/ridmp/beep5.mp3");
        if (imageCursor == null) {
            cursorImage = new Image("http://ccnl.dartmouth.edu:8080/ridmp/cursor2.jpg");
            imageCursor = new ImageCursor(cursorImage, 128, 128);
        }
    }

    public static RidUI getInstance() {
        if (instance == null)
            instance = new RidUI();
        return instance;
    }

    public void setAttemptedId(int attemptedId) {
//        if (attemptedId == 0)
//            this.attemptedId = 0;
//        else
            this.attemptedId = attemptedId;
    }
//    private static Image showAward() {
//        try {
//            Image image = new Image(new FileInputStream("C:\\java\\RIDMPAward\\gold-dollar-sign.jpg"));
//            return image;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private static GaborShape createOffImage(int size) {
        GaborShape image = new GaborShape(size, size);
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                pixelWriter.setColor(i, j, Color.rgb(128, 128, 128));
        return image;
    }

    private Blend blueEffect() {
        Blend blend = new Blend();
        blend.setMode(BlendMode.BLUE);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.BLUE);
//        ds.setOffsetX(5);
//        ds.setOffsetY(5);
//        ds.setRadius(5);
//        ds.setSpread(0.2);

        blend.setBottomInput(ds);

//        DropShadow ds1 = new DropShadow();
//        ds1.setColor(Color.web("#f77777"));
//        ds1.setRadius(20);
//        ds1.setSpread(0.2);
//
//        Blend blend2 = new Blend();
//        blend2.setMode(BlendMode.MULTIPLY);
//
//        InnerShadow is = new InnerShadow();
//        is.setColor(Color.web("#f00000"));
//        is.setRadius(9);
//        is.setChoke(0.8);
//        blend2.setBottomInput(is);
//
//        InnerShadow is1 = new InnerShadow();
//        is1.setColor(Color.web("#f66666"));
//        is1.setRadius(5);
//        is1.setChoke(0.4);
//        blend2.setTopInput(is1);
//
//        Blend blend1 = new Blend();
//        blend1.setMode(BlendMode.MULTIPLY);
//        blend1.setBottomInput(ds1);
//        blend1.setTopInput(blend2);
//
//        blend.setTopInput(blend1);
        return blend;
    }

    private Blend applyEffect(Color color) {
        Blend blend = new Blend();
        blend.setMode(BlendMode.BLUE);

        DropShadow ds = new DropShadow();

        ds.setColor(color);
        blend.setBottomInput(ds);
        return blend;
    }

    private Blend redEffect() {
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(254, 235, 66, 0.3));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setRadius(5);
        ds.setSpread(0.2);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.web("#f13a00"));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.web("#feeb42"));
        is.setRadius(9);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.web("#f13a00"));
        is1.setRadius(5);
        is1.setChoke(0.4);
        blend2.setTopInput(is1);

        Blend blend1 = new Blend();
        blend1.setMode(BlendMode.MULTIPLY);
        blend1.setBottomInput(ds1);
        blend1.setTopInput(blend2);

        blend.setTopInput(blend1);
        return blend;
    }

    class ApplyTask extends TimerTask {
        private ImageView imageView;
        private FeatureTargetAwardTO targetTO;
        private List<FeatureTargetAwardTO> targets;
        private int row;
        private int col;

        public ApplyTask(ImageView imageView, FeatureTargetAwardTO targetTO, List<FeatureTargetAwardTO> targets, int row, int col) {
            this.imageView = imageView;
            this.targetTO = targetTO;
            this.targets = targets;
            this.row = row;
            this.col = col;
        }


        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
//            System.out.println("Entered");
            GaborShape shape = ((GaborShape) imageView.getImage());
            if (shape == null)
                return;

            targetTO.setPersonAttemptedId(attemptedId);

            targetTO.setTargetedTime(new Date());

            targetTO.setProbability(currentProbability);
            targetTO.setFeatureType(currentFeature);

            boolean reward = shape.isReward();
//            System.out.println(reward);
            if (reward)
                targetTO.setAward(1);
            else
                targetTO.setAward(0);

            targetTO.setRowSelected(row);
            targetTO.setColumnSelected(col);

            double[] color1 = {255, 255};
            double[] color2 = {0, 0};
            double constantRate = 50;

            shapeChosen++;

            selectedShapeNumberForReversal++;
            shapeChooseCount++;
            totalShapeCounts++;
            startBlock++;

            if (shape.isReward()) {
                imageView.setEffect(applyEffect(Color.GREEN));
                // URL url = RidUI.class.getResource("beep5.mp3");
                //File file = new File()
                // String uriString = new File("C:\\java\\RIDMPAward\\beep5.mp3").toURI().toString();
                MediaPlayer player = new MediaPlayer(media);
                player.play();
            } else
                imageView.setEffect(applyEffect(Color.RED));

            try {
                int awardId = commandInterface.saveTarget(targetTO);
//                System.out.println(awardId + " : " + targetTO.getRowSelected() + " : " + targetTO.getColumnSelected());
                targetTO.setAwardId(awardId);
                targets.add(targetTO);
                updateTargets.add(targetTO);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            imageView.setImage(offImage);
            imageView.setOnMouseEntered(null);


            // imageView  = getShape(size, color1, color2, constantRate, shape);

//            showCircles(gridPane);

            //  imageView.setImage(newShape);
            //imageView.setOnMouseEntered(null);
//            if (reward) {
//                imageView.setImage(offImage);
//                imageView.setOnMouseEntered(null);
//            } else
//                imageView.setEffect(null);
        }
    }

    Timer timerAssignTarget = new Timer();
    TimerTask timerTask;
    private void assignTargets(final ImageView assignedImageView, final FeatureTargetAwardTO assignedTargetTO,
                               final List<FeatureTargetAwardTO> assignedTargets, final long assignedShapeSchedule, final int assignedRow, final int assignedCol, int size) {

//        timerAssignTarget = new Timer();
//        final TimerTask timerTask = new ApplyTask(assignedImageView, assignedTargetTO, assignedTargets, assignedRow, assignedCol);

        assignedImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                scene.setCursor(Cursor.CROSSHAIR);
                assignedImageView.setEffect(applyEffect(Color.BLUE));
                if (((GaborShape) assignedImageView.getImage()).isReward()) {
                }
                timerTask = new ApplyTask(assignedImageView, assignedTargetTO, assignedTargets, assignedRow, assignedCol);
                timerAssignTarget.schedule(timerTask, assignedShapeSchedule);
            }
        });
        assignedImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                scene.setCursor(Cursor.DEFAULT);
                assignedImageView.setEffect(null);
                timerTask.cancel();
            }
        });

        /*
        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                double orgSceneX, orgSceneY;
                double orgTranslateX, orgTranslateY;
                Circle circle_Blue = new Circle(50.0f, Color.BLUE);
                circle_Blue.setCursor(Cursor.CROSSHAIR);
                circle_Blue.setTranslateX(300);
                circle_Blue.setTranslateY(100);

                DropShadow dropShadow = new DropShadow();
                dropShadow.setBlurType(BlurType.GAUSSIAN);
                dropShadow.setColor(Color.AQUA);
                dropShadow.setOffsetX(5.0);
                dropShadow.setOffsetY(5.0);
                dropShadow.setRadius(10.0);


              //  ((Circle)(t.getSource())).toFront();
                imageView.setEffect(dropShadow);
            }
        });*/
    }

    private void reversalDialog(String message,
                                final GridPane grid,
                                final Stage stage, final Reward reward, final int blockCount, final int trialCount, final FeatureTargetAwardTO targetTO,
                                final List<FeatureTargetAwardTO> targets, final boolean flag, final long shapeSchedule, final int size, final int leftMargin, final int upMargin) {
        final Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        GridPane reversalGrid = new GridPane();
        reversalGrid.setAlignment(Pos.CENTER);  // Override default
        reversalGrid.setStyle("-fx-background-color: black");

        Scene scene = new Scene(reversalGrid, 220, 100); // Manage scene size
        dialogStage.setTitle("Authentication");
        Label label = new Label(message);
        label.setStyle("-fx-text-fill: yellow");
        reversalGrid.addRow(0, label);

        Button btnConfirm = new Button("Yes");
        btnConfirm.setStyle("-fx-text-fill: crimson");
        reversalGrid.addRow(1);
        reversalGrid.addColumn(0, btnConfirm);
        btnConfirm.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                dialogStage.close();
                //  shapeChooseCount = 0;
//                if (!visible)
//                renewBlock(null, grid, stage, reward, blockCount, trialCount, targetTO, targets, flag, shapeSchedule, size, leftMargin, upMargin);
            }
        });

        Button btnCancel = new Button("No");
        btnCancel.setStyle("-fx-text-fill: crimson");
        reversalGrid.addColumn(2, btnCancel);
        btnCancel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                dialogStage.close();
            }
        });

        dialogStage.setScene(scene);
        dialogStage.show();
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
                System.exit(0);
            }
        });

        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private void kickInactiveUser(final GridPane grid, final int leftMargin, final int upMargin, final String message) {
        final Timer timer = new java.util.Timer();
        final int l = leftMargin * (upMargin / 2);
        final int u = leftMargin * 2;
        grid.getChildren().clear();

        timer.schedule(new TimerTask() {
            int t = 0;

            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (t == 2) {
                            timer.cancel();
                            grid.getChildren().clear();
                            timer.cancel();
                            stage.close();
                            return;
                        }

                        final Label label = new Label(message);
                        label.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 20px");
                        GridPane.setConstraints(label, u, l);
                        grid.getChildren().add(label);
                        t++;
                    }
                });
            }
        }, 0, 2000);

        stage.show();
    }

    private void showTimer(final GridPane grid, final int leftMargin, final int upMargin) {
        final Timer timer = new java.util.Timer();

        final Integer STARTTIME = 120;
        final Timeline timeline;
        final Label timerLabel = new Label();
        final IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
        timerLabel.textProperty().bind(timeSeconds.asString());
        timerLabel.setTextFill(Color.BLACK);
        timerLabel.setStyle("-fx-font-size: 40px;");

        timeSeconds.set(STARTTIME);
        timeline = new Timeline();

        final int l = leftMargin * (upMargin / 2);
        final int u = leftMargin * 2;

        timer.schedule(new TimerTask() {

            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (!inRest) {
                            timer.cancel();
                            init(stage);
                        }
                        timeline.getKeyFrames().
                                add(new KeyFrame(Duration.seconds(STARTTIME + 1),
                                        new KeyValue(timeSeconds, 0)));
                        timeline.playFromStart();
                        VBox vb = new VBox();             // gap between components is 20
                        vb.setAlignment(Pos.CENTER);        // center the components within VBox

//                        vb.setPrefWidth(scene.getWidth());
                        vb.getChildren().add(timerLabel);

//                        vb.setLayoutY(50);
                        GridPane.setConstraints(vb, u, l);
                        grid.getChildren().add(vb);
                        inRest = false;
                        isRest = false;
                    }
                });
            }
        }, 0, (STARTTIME) * 1000);
    }

    private void doFinalize(final GridPane grid, final int leftMargin, final int upMargin) {
        final Timer timer = new java.util.Timer();
        grid.getChildren().clear();
        final int l = leftMargin * (upMargin / 2);
        final int u = leftMargin * 2;
        timer.schedule(new TimerTask() {
            int t = 0;

            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (t == 2) {
                            timer.cancel();
                            stage.close();
                            return;
                        }
                        final Label label = new Label("Thank you for participating this program!");
                        label.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 40px");
                        GridPane.setConstraints(label, u, l);
                        grid.getChildren().add(label);
                        t++;
                    }
                });
            }
        }, 0, 3000);

        stage.show();
    }

    private void featureAlarm(final GridPane grid, final int leftMargin, final int upMargin, final String feature) {
        final Timer timer = new java.util.Timer();

        final int l = leftMargin * (upMargin / 2);
        final int u = leftMargin * 2;

        timer.schedule(new TimerTask() {
            int t = 0;

            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (t == 2) {
                            timer.cancel();
                            grid.getChildren().clear();
                            init(stage);
                            return;
                        }
                        String f = feature;
                        if (f.equals("ornt"))
                            f = "Orientation";
                        else
                            f = "Special Frequency";

                        final Label label = new Label("The informative feature is " + f);
                        label.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 20px");
                        GridPane.setConstraints(label, u, l);
                        grid.getChildren().add(label);
                        t++;
                    }
                });
            }
        }, 0, 2000);

        stage.show();
    }

    private void train(final GridPane grid, final Stage stage, final int leftMargin, final int upMargin) {
        grid.getChildren().clear();

        int l = leftMargin * (upMargin / 2);
        int u = leftMargin * 2;

        String message;
        if (startedTraining)
            message = "Are you ready to start the training?";
        else
            message = "Do you want to continue the training?";

        startedTraining = false;
        // Training
        final Label startTrainLabel = new Label(message);
        startTrainLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 24px");
        GridPane.setConstraints(startTrainLabel, u, l);
        grid.getChildren().add(startTrainLabel);

        final Button startTrainButton = new Button("Click");
        startTrainButton.setStyle("-fx-text-fill: black");
        GridPane.setConstraints(startTrainButton, u + 1, l);
        grid.getChildren().add(startTrainButton);

        // Experiment
        final Label startExperimentLabel = new Label("Do you want to start the experiment?");
        startExperimentLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 24px");
        GridPane.setConstraints(startExperimentLabel, u, l + 1);
        grid.getChildren().add(startExperimentLabel);

        final Button startExperimentButton = new Button("Click");
        startExperimentButton.setStyle("-fx-text-fill: black");
        GridPane.setConstraints(startExperimentButton, u + 1, l + 1);
        grid.getChildren().add(startExperimentButton);

        final Timer timer = new java.util.Timer();

        scene.setCursor(Cursor.DEFAULT);

        timer.schedule(new TimerTask() {
            int t = 0;

            public void run() {
                Platform.runLater(new Runnable() {

                    public void run() {
//                        if (t > 1 && isExperiment) {
//                            userDecided = true;
//                            inRest = false;
//                            init(stage, commandInterface, personTO, scheduleTO);
//                        }

                        startTrainButton.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
                                timer.cancel();
                                grid.getChildren().clear();
                                isExperiment = false;
                                //isTraining = true;
                                trainingCount = 0;
                                init(stage);
                            }
                        });

                        startExperimentButton.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
                                timer.cancel();
                                grid.getChildren().clear();
                                isExperiment = true;
                                isTraining = false;
                                init(stage);
                            }
                        });
                        t++;
                    }
                });
            }
        }, 0, 20000);
        stage.show();
    }

    private void rest(final GridPane grid, final Stage stage, final int leftMargin, final int upMargin) {
        renewCount = 0;
        grid.getChildren().clear();

        clikcedOnRest = false;

        shapeChosen = 0;

        int l = leftMargin * (upMargin / 2);
        int u = leftMargin * 2;

        final Label label = new Label("Are you ready to continue?");
        label.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 24px");
        GridPane.setConstraints(label, u, l);
        grid.getChildren().add(label);

        final Button btnCancel = new Button("No");
        btnCancel.setStyle("-fx-text-fill: black");
        GridPane.setConstraints(btnCancel, u + 1, l + 1);
        grid.getChildren().add(btnCancel);

        final Button btnConfirm = new Button("Yes");
        btnConfirm.setStyle("-fx-text-fill: black");
        GridPane.setConstraints(btnConfirm, u + 1, l);
        grid.getChildren().add(btnConfirm);

        final Timer timer = new java.util.Timer();

        isRest = false;

        scene.setCursor(Cursor.DEFAULT);

        timer.schedule(new TimerTask() {
            int t = 0;

            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (t > 1 && !userDecided) {
//                            timer.cancel();
//                            kickInactiveUser(grid, leftMargin, upMargin, "You failed to answer during given time period!");
//                            scheduleTO.setScheduleId(1 * 25 * 1000);
                            grid.getChildren().remove(label);
                            grid.getChildren().remove(btnCancel);
                            grid.getChildren().remove(btnConfirm);
                            userDecided = true;
//                    VBox vb = showTimer();
//                    GridPane.setConstraints(vb, leftMargin + 2, upMargin + 2);
//                    grid.getChildren().add(vb);
                            inRest = false;
                            init(stage);
                        }

                        btnCancel.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
                                grid.getChildren().remove(label);
                                grid.getChildren().remove(btnCancel);
                                grid.getChildren().remove(btnConfirm);
                                inRest = true;
                                clikcedOnRest = true;
                                userDecided = true;
                                restAnswer = false;
                                timer.cancel();
                                init(stage);
                            }
                        });

                        btnCancel.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
                                scene.setCursor(Cursor.DEFAULT);
                            }
                        });

                        btnCancel.setOnMouseExited(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
                                scene.setCursor(Cursor.DEFAULT);
                            }
                        });

                        btnConfirm.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent me) {
//                                scheduleTO.setScheduleId(1 * 25 * 1000);
                                grid.getChildren().remove(label);
                                grid.getChildren().remove(btnCancel);
                                grid.getChildren().remove(btnConfirm);
                                clikcedOnRest = true;
                                restAnswer = false;
                                userDecided = true;
                                timer.cancel();
//                    VBox vb = showTimer();
//                    GridPane.setConstraints(vb, leftMargin + 2, upMargin + 2);
//                    grid.getChildren().add(vb);
                                inRest = false;
                                init(stage);
                            }
                        });
                        t++;
                    }
                });
            }
        }, 0, 20000);
        stage.show();
    }

    public void init(final Stage stage) {

//        timerAssignTarget = null
        this.stage = stage;

        shapeChooseCount = 0;

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        double w = primScreenBounds.getWidth();
        double h = primScreenBounds.getHeight();

        size = (int) h / col;

        try {
            shapeConfiguration = commandInterface.loadShape();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        offImage = createOffImage(size);
        // imageAward = showAward();

        Group root = new Group();
        this.root = root;
        final Scene scene = new Scene(root, w, h);
//        stage.setX(w);
//        stage.setY(h);

//        final Canvas canvas = new Canvas(size * (col + 2), size * (row + 1));
        final Canvas canvas = new Canvas(w, h);
        root.getChildren().add(canvas);

        this.canvas = canvas;

//        final Scene scene = new Scene(root, size * (col + 2), size * (row + 1));
        scene.setCursor(Cursor.CROSSHAIR);
        stage.setScene(scene);
        stage.setTitle("RIDMP: ");

        this.scene = scene;

        final GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color:gray ");

        double wg = 2 * w / size;
        double hg = 2 * h / size;
        grid.setHgap(wg);
        grid.setVgap(hg);

        final int leftMargin = (int) (2 * (w - col * size) / size) - 1;
        final int upMargin = (int) (2 * (h - row * size) / size);

        scene.setRoot(grid);


        int blockNumbers = 4;

        String[] rewardTypes = {"feature", "probability"};

//        TODO

//        final double[] probabilities = {.8, .5};


        final Timer timer = new java.util.Timer();

        final Integer blockNumber = scheduleTO.getBlockNumber();
        final Integer trialNumber = scheduleTO.getTrialNumber();
        final long trialSchedule = scheduleTO.getTrialSchedule();
        final long shapeSchedule = scheduleTO.getShapeSchedule();

        String orntStr = personTO.getOrientations();
        orntStr = StringUtils.remove(orntStr, " ");
        String[] ornts = StringUtils.split(orntStr, ',');
        final double[] orientations = new double[Configuration.length];
        for (int i = 0; i < Configuration.length; i++)
            orientations[i] = Double.valueOf(ornts[i]);

        String frg = personTO.getSpecialFrequencies();
        frg = StringUtils.remove(frg, " ");
        String[] frgs = StringUtils.split(frg, ',');
        final double[] specialFrequencies = new double[Configuration.length];
        for (int i = 0; i < Configuration.length; i++)
            specialFrequencies[i] = Double.valueOf(frgs[i]);

        String probStr = personTO.getProbabilities();
        probStr = StringUtils.remove(probStr, " ");
        String[] probs = StringUtils.split(probStr, ',');
        final double[] probabilities = new double[Configuration.length];
        for (int i = 0; i < Configuration.length; i++)
            probabilities[i] = Double.valueOf(probs[i]);

//        final String rule = "ProbabilityRule";

        //                {.8, .5, .2};

        final Reward
                //reward = null;
                // if (rewardTypes[0].equals("feature"))
                reward = new FeatureReward("ornt", probabilities);

        RewardTO rewardTO = personTO.getRewardTO();
        final String rule = rewardTO.getRewardName();
        final String rewardType = rewardTO.getRewardType();
        final double[] rewardProbabilities = ((FeatureReward) reward).getRewardProbabilities();

        final Integer numberOfChoice = personTO.getNumberOfChoice();

        final Integer reversal = personTO.getReversal();

        final Label label = new Label("Feature");

        final int[] selectedShapesPerBlock = new int[blockNumber];
        totalShapeCounts = 0;
        selectedShapeIndex = 0;

        userDecided = false;

        timer.schedule(new TimerTask() {
            boolean flag = true;
            boolean isBlockCountPlus = false;
            double[] newornts = orientations;
            double[] sfs = specialFrequencies;
//                int trialCount = 1;
//                int blockCount = 1;

            List<FeatureTargetAwardTO> targets = new ArrayList<FeatureTargetAwardTO>();

            public void run() {
                Platform.runLater(new Runnable() {
                    PersonTO person = personTO;

                    public void run() {
//                        System.out.println("blockCount = " + blockCount);
                        //TODO
//                        if (blockCount == blockNumber) {
//
//                        }
                        if (restAnswer && !clikcedOnRest) {
                            timer.cancel();
                            kickInactiveUser(grid, leftMargin, upMargin, "You failed to answer the question!");
                            return;
                        }

                        if (startedTraining) {
                            currentFeature = rewardType;
                            currentProbabilityIndex = 0;
                            currentProbability = rewardProbabilities[0];
                            timer.cancel();
                            train(grid, stage, leftMargin, upMargin);
                            return;
                        } else if (isTraining) {
                            if (trainingCount > 4) {
                                timer.cancel();
                                train(grid, stage, leftMargin, upMargin);
                                return;
                            }
                        }

//                        if (selectedShapeNumberForReversal == 220) {
//                            System.out.println();
//                        }
//                        else if (isExperiment) {
//
//                        }
//                        if (!isExperiment || trainingCount < 4) {
//                            if (startedTraining) {
//                                timer.cancel();
//                                train(grid, stage, leftMargin, upMargin);
//                            } else if (trainingCount > 4) {
//                                timer.cancel();
//                                train(grid, stage, leftMargin, upMargin);
//                            } else if (isTraining) {
//                                timer.cancel();
//                                isTraining = false;
//                                train(grid, stage, leftMargin, upMargin);
//                            }
//                        }

//                        else if (trainingCount < 4) {
//                            isExperiment = false;
//                            trainingCount++;
//                        }

                        if (featureAlarm) {
                            featureAlarm = false;
                            timer.cancel();
                            featureAlarm(grid, leftMargin, upMargin, rewardType);
                        } else if (inRest) {
                            timer.cancel();
                            isBlockCountPlus = false;
                            showTimer(grid, leftMargin, upMargin);
//                                GridPane.setConstraints(vb, leftMargin + 2, upMargin + 2);
//                                grid.getChildren().add(vb);
                        } else {
                            if (vb != null)
                                grid.getChildren().remove(vb);

//                            TODO
                            if (numberOfReversal >= reversal
                                    ) {
                                timer.cancel();
//                                System.out.println("done");
                                person.setStatus(4);

                                doFinalize(grid, leftMargin, upMargin);
                                try {
                                    commandInterface.exportAwardCSV(personTO);
                                    commandInterface.updatePerson(person);
                                    commandInterface.sentEmail(personTO, 4);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                return;
                                //stage.close();

                                // stage.close();
//                                showDialog("Thank you for participating this program!");
                                //  return;
                            }

                            if (!visible && c == 1) {
                                grid.getChildren().remove(label);
                            }

                            FeatureTargetAwardTO targetTO = new FeatureTargetAwardTO();
                            targetTO.setPerId(person.getPerId());
                            targetTO.setPersonTO(person);

                            targetTO.setBlockNumber(blockCount);
                            targetTO.setTrialNumber(trialCount);


//                        Random r = new Random();
                            int reversal = scheduleTO.getReversal();
//                        int low = reversal - 10;
//                        int high = reversal;
//                        int t = r.nextInt(high - low) + low;

//                        if (
////                                shapeChooseCount == 0 ||
//
//                                ((
////                                        shapeChooseCount > t &&
//                                        shapeChooseCount >= reversal)
//                        ) && blockCount >= (Math.round(blockNumber / 2.0))) {
//                            System.out.println(shapeChooseCount + " =====================================");
//                            stage.close();
//
////                            if (!visible)
////                                reversalDialog("Are you ready to continue?", grid, stage, reward, blockCount, trialCount, targetTO, targets, flag, shapeSchedule, size, leftMargin, upMargin);
//
//                            shapeChooseCount = 0;
//                        }



//                            else
//                            if (blockCount == blockNumber) {
//                                System.out.println("dsfdsdsf");
//                            }
//                            else if (blockCount != blockNumber)
//                            {

//                                targetTO.setRule(rule);

//                                System.out.println("numberOfChoice = " + numberOfChoice);
//                                System.out.println("selectedShapeNumberForReversal = " + selectedShapeNumberForReversal);

                                currentProbability = probabilities[currentProbabilityIndex];
                                if (rule.equals("ProbabilityRule")) {
                                    if (selectedShapeNumberForReversal > numberOfChoice) {
                                        double temp = probabilities[0];
                                        probabilities[0] = probabilities[1];
                                        probabilities[1] = temp;
//                                        if (currentProbabilityIndex == 0)
//                                            currentProbabilityIndex = 1;
//                                        else
//                                            currentProbabilityIndex = 0;
//                                        currentProbability = probabilities[currentProbabilityIndex];

                                        selectedShapeNumberForReversal = 0;
                                        previousNumberForReversal = 0;
                                        numberOfReversal++;

                                        if (isRest && numberOfReversal > 0 &&
                                                numberOfReversal == (reversal / 2 + 1)
                                                && isBlockCountPlus
                                                ) {
                                            isRest = false;
                                            timer.cancel();
                                            scene.setCursor(Cursor.CROSSHAIR);
                                            restAnswer = true;
                                            rest(grid, stage, leftMargin, upMargin);
                                            return;
                                        }
                                    }

                                } else if (rule.equals("FeatureRule")) {
                                    if (selectedShapeNumberForReversal > numberOfChoice) {
                                        state = !state;
                                        double temp;
                                        if (currentFeature.equals("ornt")) {
                                            currentFeature = "sf";
//                                            temp = sfs[0];
//                                            sfs[0] = sfs[1];
//                                            sfs[1] = temp;
                                        } else if (currentFeature.equals("sf")) {
                                            currentFeature = "ornt";
                                            // swap
//                                            temp = newornts[0];
//                                            newornts[0] = newornts[1];
//                                            newornts[1] = temp;
                                        }
                                        selectedShapeNumberForReversal = 0;
                                        previousNumberForReversal = 0;
                                        numberOfReversal++;
                                        if (isRest && numberOfReversal > 0 &&
                                                numberOfReversal == (reversal / 2 + 1)
                                                && isBlockCountPlus
                                                ) {
                                            isRest = false;
                                            restAnswer = true;
                                            timer.cancel();
                                            scene.setCursor(Cursor.CROSSHAIR);
                                            rest(grid, stage, leftMargin, upMargin);
                                            return;
                                        }
                                    }
                                }

                                trainingCount++;
                                renewBlock(rule, currentFeature, currentProbability,
                                        orientations, specialFrequencies, rewardProbabilities,
                                        grid, stage, reward, reversal, blockNumber, blockCount,
                                        trialCount, targetTO, targets, flag, shapeSchedule, size, leftMargin, upMargin, state);

                                trialCount++;
                                isRest = true;

//                                TODO
//                                if (shapeChosen <=2 && renewCount >=2 ) {
//                                    timer.cancel();
//                                    kickInactiveUser(grid, leftMargin, upMargin, "You failed to continue the experiment!");
//                                    return;
//                                }

//                            selectedShapesPerBlock[selectedShapeIndex] = totalShapeCounts;
//                            System.out.println("selectedShapeIndex = " + selectedShapeIndex);
//                            System.out.println("totalShapeCounts = " + totalShapeCounts);

//                            if (selectedShapeIndex >= 3) {
//                                System.out.println(selectedShapesPerBlock[selectedShapeIndex]);
//                                System.out.println(selectedShapesPerBlock[selectedShapeIndex - 2]);
//                                if (Math.abs(selectedShapesPerBlock[selectedShapeIndex] - selectedShapesPerBlock[selectedShapeIndex - 2]) < 5)
//                                    stage.close();
//                                showDialog("You failed to select enough shapes");
//                                return;
//                            }
                                c++;
                                selectedShapeIndex++;
//                            if (startBlock < 3) {
//                                stage.close();
//                                showDialog("You failed to select enough shapes");
//                            }

//                            }

//                        if (!visible) {
//                            try {
//                                commandInterface.saveTargets(targets);
//                            } catch (RemoteException e) {
//                                e.printStackTrace();
//                            }

                            if (trialCount == trialNumber) {
                                trialCount = 1;
                                blockCount++;
                                isBlockCountPlus = true;
                            } else if (trialCount == trialNumber + 1) {
                                trialCount = 1;
                                flag = !flag;
                                targets = new ArrayList<FeatureTargetAwardTO>();
                            }
                        }
                        startBlock = 0;
                        shapeChosen = 0;

                        com.sun.glass.ui.Robot robot = Application.GetApplication().createRobot();
                        robot.mouseMove(10, 10);
//                        try {
//                            new Robot().mouseMove(10, 10);
//                            scene.setCursor(Cursor.DEFAULT);
////                            scene.setCursor(Cursor.DEFAULT);
//                        } catch (AWTException e) {
//                            e.printStackTrace();
//                        }
                    }
                });
            }
        }, 0, trialSchedule);
    }

    private boolean renewBlock(String rule, String currentFeature, double currentProbability,
                               double[] orientations, double[] specialFrequencies, double[] rewardProbabilities,
                               GridPane grid, Stage stage, Reward reward, int reversal, int blockNumber, int blockCount,
                               int trialCount, FeatureTargetAwardTO targetTO,
                               List<FeatureTargetAwardTO> targets, boolean flag,
                               long shapeSchedule, int size, int leftMargin, int upMargin, boolean state) {

        renewCount++;
        previousNumberForReversal = selectedShapeNumberForReversal;
        c++;

        scene.setCursor(Cursor.CROSSHAIR);

        stage.setTitle("RIDMP# " + blockCount + " : " + trialCount);

//                {35, 45, 55};
//                {.1, .11, .12};
        String authorize;
        String cnt = shapeConfiguration.getContrastStr();
        cnt = StringUtils.remove(cnt, " ");
        String[] cnts = StringUtils.split(cnt, ',');

        //        TODO: changes to 2
        double[] color1 = new double[Configuration.length];
        color1[0] = color1[1] =
//                color1[2] =
                Double.valueOf(cnts[0]);
        double[] color2 = {0, 0};
        color2[0] = color2[1] =
//                color2[2] =
                Double.valueOf(cnts[1]);
        double constantRate = Double.valueOf(cnts[2]);

        int probabilityNumber = 1; // 1..3

//        row = 8;
//        col = 6;

        Image[][] images = null;

        if (flag)
            ((FeatureReward) reward).setFeature("ornt");
        else
            ((FeatureReward) reward).setFeature("sf");

        for (int i = 0; i < Configuration.length; i++)
            contrasts[i] = new Contrast(color1[i] - (i * constantRate), color2[i] + (i * constantRate));

//        if (rule.equals("FeatureRule"))
        images = new ShapeUtil().featureReward(size, row, col, color1, color2, constantRate, specialFrequencies, orientations, rewardProbabilities, currentFeature, state);
//        else
//            images = new ShapeUtil().featureReward(size, row, col, color1, color2, constantRate, specialFrequencies, orientations, rewardProbabilities, currentFeature);
//            images = new ShapeUtil().probabilityReward(size, row, col, color1, color2, constantRate, specialFrequencies, orientations, currentProbability, currentFeature);

        GaborShape gaborShape;
        String contrast;
//        System.out.println("\n==================================================================================\n");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                gaborShape = (GaborShape) images[i][j];
                targetTO.setOrientation(gaborShape.getOrientation());
                targetTO.setSpecialFrequency(gaborShape.getSpecialFrequency());
//                contrast = (int) color1[0] + ", " + (int) color2[0] + ", " + (int) constantRate;
                targetTO.setContrast(contrasts[0].getColor1()[0] + "," + contrasts[1].getColor1()[0]);

                ImageView imageView = new ImageView(images[i][j]);
//                timerAssignTarget = new Timer();
                assignTargets(imageView, targetTO, targets, shapeSchedule, i + 1, j + 1, size);
//                timerAssignTarget = null;
                GridPane.setConstraints(imageView, j + leftMargin, i + upMargin);
                grid.getChildren().add(imageView);
//                System.out.print(rewardProbabilities[0] + ":" + gaborShape.isReward() + ", ");
            }
//            System.out.println();
        }
        images = null;
        stage.show();
        return flag;
    }

    public int getShapeChooseCount() {
        return shapeChooseCount;
    }

    public void setShapeChooseCount(int shapeChooseCount) {
        this.shapeChooseCount = shapeChooseCount;
    }

    public void setCommandInterface(CommandInterface commandInterface) {
        this.commandInterface = commandInterface;
    }

    public void setPerson(PersonTO personTO) {
        this.personTO = personTO;
    }

    public void setSchedule(ScheduleTO scheduleTO) {
        this.scheduleTO = scheduleTO;
    }

//    public void setStage(Stage stage) {
//        this.stage = stage;
//    }
}