package view;

import controller.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Activities;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Created by hswea on 12/16/2016.
 */
public class signIncontroller implements Initializable  {
    @FXML
    private Button doneBtn = new Button();
    @FXML
    private Button addBtn = new Button();
    @FXML
    private TableView<Activities> tableView = new TableView<>();
    @FXML
    private TableColumn<Activities, String> dateCol;
    @FXML
    private TableColumn<Activities, String> eventCol;
    @FXML
    private TableColumn<Activities, String> timeInCol;
    @FXML
    private TableColumn<Activities, String> timeOutCol;
    @FXML
    private Label personName = new Label();
    @FXML
    private Label totalHours = new Label();
    @FXML
    private Label totalHoursRemaining = new Label();
    @FXML
    private PieChart pieChart = new PieChart();
    @FXML
    private BarChart<String, Integer> barChart;
    private Main main = new Main();

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Label name: " + AppData.getAppData().dbStudent().getName());
        personName.setText(AppData.getAppData().dbStudent().getName());

        //Date Column
        dateCol = new TableColumn<>("Date");
        dateCol.setMinWidth(200);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        //Event Column
        eventCol = new TableColumn<>("Event");
        eventCol.setMinWidth(300);
        eventCol.setCellValueFactory(new PropertyValueFactory<>("event"));

        //Time In Column
        timeInCol = new TableColumn<>("In");
        timeInCol.setMinWidth(200);
        timeInCol.setCellValueFactory(new PropertyValueFactory<>("timeIn"));

        //Time Out Column
        timeOutCol = new TableColumn<>("Out");
        timeOutCol.setMinWidth(200);
        timeOutCol.setCellValueFactory(new PropertyValueFactory<>("timeOut"));

        tableView.setItems(AppData.getAppData().getStudentActivities());
        tableView.getColumns().addAll(dateCol, eventCol, timeInCol, timeOutCol);
        showBarChart();

        totalHours.setText(String.valueOf(hoursTotal()) + " hrs");
        totalHoursRemaining.setText(String.valueOf(hoursRemaining()) + " hrs");

        setAddBtn();
        setDoneBtn();

        //<editor-fold desc="Description">
       /* for (Activities s : AppData.getAppData().getStudentActivities()
                ) {
            System.out.println(s.getTimeIn());
            System.out.println(s.getEvent());
            System.out.println(s.getDate());

        }*/
        //</editor-fold>
    }

    // Add button send user to Add Data screen
    public void setAddBtn(){
        addBtn.setOnAction(event -> {
            try {
                main.pageSwitch("dataPopUp.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ObservableList<Activities> activities(){
       ObservableList<Activities> data = FXCollections.observableArrayList(
       new Activities("2017-01-12", "Night of the Bull", "6:00pm", "9:00pm"));
        return data;
    }

    // Populating the BarChart
    public void showBarChart(){
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        //<editor-fold desc="Description">
        //CategoryAxis xAxis = new CategoryAxis();
        //xAxis.setCategories(FXCollections.observableArrayList(events));
        //NumberAxis yAxis = new NumberAxis("Hours",0,50,5);

        /*for (Activities activity: AppData.getAppData().getStudentActivities()) {
            String event = activity.getEvent();
            if(event.equals(activity.getEvent())){
                int sum = 0;
                sum += activity.hoursWorked(activity.getTimeOut(), activity.getTimeIn());
                series.getData().add(new XYChart.Data<>(activity.getEvent(), sum));
            } else{
                int sum;
                sum = activity.hoursWorked(activity.getTimeOut(), activity.getTimeIn());
                series.getData().add(new XYChart.Data<>(activity.getEvent(), sum));
            }
        }*/
        //</editor-fold>

        ArrayList<Activities> data = new ArrayList<>(AppData.getAppData().getStudentActivities());
        Map<String, Integer> chartData = data.stream().collect(
                Collectors.groupingBy((Activities a) -> a.getEvent(),
                        Collectors.summingInt(a -> a.hoursWorked(a.getTimeOut(), a.getTimeIn())))
        );
        for (Map.Entry<String, Integer> dataSet : chartData.entrySet()) {
            series.getData().add(new XYChart.Data<>(dataSet.getKey(), dataSet.getValue()));
        }
        barChart.getData().add(series);
    }

    // Done button returns users to the main screen
    public void setDoneBtn(){
        doneBtn.setOnAction(event -> {
            try {
                main.pageSwitch("mainScreen.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Calculate the total hours worked so far
    public int hoursTotal(){
        int sum = 0;

        for (Activities a: AppData.getAppData().getStudentActivities()
             ) {
            sum += a.hoursWorked(a.getTimeOut(),a.getTimeIn());
            System.out.println("Hours worked: " + a.hoursWorked(a.getTimeOut(), a.getTimeIn()));
        }
        System.out.println("The sum is there:" + sum);
        return sum;
    }

    // Calculate the total hours remaining
    public int hoursRemaining(){
        int sum = 0;
        for (Activities a: AppData.getAppData().getStudentActivities()
                ) {
            sum += a.hoursWorked(a.getTimeOut(),a.getTimeIn());
        }
        return 30 - sum;
    }
}


