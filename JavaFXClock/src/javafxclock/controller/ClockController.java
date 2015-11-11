package javafxclock.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafxclock.model.Time;

/**
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class ClockController implements Initializable {

    @FXML
    private Label timeLabel;
        @FXML
    private Label weekdayLabel;
    private Timeline tl = new Timeline();
    private Time time = new Time();
    private Time alarmTime;
    private boolean ticking=false;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        // TODO
    }

    @FXML
    private void handleSyncButtonAction(ActionEvent event) {
        time.sync();
    }

    @FXML
    private void handleIncrementAction(ActionEvent event) {
        Button sourceBtn = (Button) event.getSource();
        if (sourceBtn.getId().equals("plusHoursButton")) {
            time.increment(time.getHour());
        } else if (sourceBtn.getId().equals("plusMinutesButton")) {
            time.increment(time.getMinute());
        }
    }
   @FXML
    private void handleAlarmToggleButtonAction(ActionEvent event) {
        alarmTime = new Time(time.getHour().getValue(), time.getMinute().getValue(), time.getSecond().getValue(), time.getDay().getValue());
    }
   @FXML
    private void handleStartStopButtonAction(ActionEvent event) {
      startStopAction();
    }

    @FXML
    private void handleDecrementAction(ActionEvent event) {
        Button sourceBtn = (Button) event.getSource();
        if (sourceBtn.getId().equals("minusHoursButton")) {
            time.decrement(time.getHour());
        } else if (sourceBtn.getId().equals("minusMinutesButton")) {
            time.decrement(time.getMinute());
        }
    }
    /**
     * starts and stops the timeline animation
     */
    private void startStopAction() {
          if(ticking) {
            tl.pause();
            ticking=false;
        }
        else {
            tl.play();
            ticking=true;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tl.setCycleCount(Timeline.INDEFINITE);
        //bind label with time
        timeLabel.textProperty().bind(time.total);
        //bind label with day
        timeLabel.textProperty().bind(time.dStr);
        //add actions to timeLine
        tl.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            //we define what should happen every second
            public void handle(ActionEvent event) {
                time.tick();
                System.out.println(time.toString()); //toString is nicer readable
            }
        }));
        //start cllock first time
       startStopAction();
    }

}
