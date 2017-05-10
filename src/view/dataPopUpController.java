package view;

import com.jfoenix.controls.JFXDatePicker;
import controller.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Activities;
import view.Main;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Created by hswea on 12/23/2016.
 */
public class dataPopUpController implements Initializable{
    @FXML
    private ListView<String> event = new ListView<>();
    @FXML
    private JFXDatePicker selectDate = new JFXDatePicker();
    @FXML
    private JFXDatePicker selectTime = new JFXDatePicker();
    @FXML
    private Button submit = new Button("Submit");
    @FXML
    private Button cancel = new Button("Cancel");

    private Activities activity;
    private Main main = new Main();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectTime.setTime(LocalTime.of(10, 00));
        System.out.println("Data popup Controller: " + getFormattedTime( selectTime.getTime().toString() ));
        event.setItems(eventOptions());
        setSubmit();
        setCancel();
    }

    private String getFormattedTime(String time) {

        StringTokenizer number = new StringTokenizer(time, ": ");
        StringBuilder newNum = new StringBuilder();

        while (number.hasMoreTokens()) {
            newNum.append(number.nextToken());
        }
        return newNum.toString();
    }

    public void setSubmit(){
        submit.setOnAction(event1 -> {
            System.out.println("From Data popup Controller: "+ getFormattedTime( selectTime.getTime().toString() ));
            activity = new Activities(selectDate.getValue().toString(), event.getSelectionModel().getSelectedItem(), getFormattedTime(selectTime.getTime().toString()));
            AppData.getAppData().addActivity(activity);
        });
    }
    public void setCancel(){
        cancel.setOnAction(event1 -> {
            try {
                main.pageSwitch("signInview.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public ObservableList<String> eventOptions(){
        ObservableList<String> events = FXCollections.observableArrayList(
                "Resource Fair", "Welcome Rally", "Wellness Welcome", "Night Event", "Volunteer Fair"
        );
        return events;
    }
}
