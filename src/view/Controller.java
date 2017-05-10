package view;

import controller.AppData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.Student;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    @FXML
    private Button submit = new Button();
    @FXML
    private Button cancel = new Button();
    @FXML
    private TextField name = new TextField();
    @FXML
    private TextField vID = new TextField();
    @FXML
    private TextField email = new TextField();
    @FXML
    private RadioButton bridges = new RadioButton();
    private Student student = new Student();
    private Main main = new Main();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //name = new TextField();
        name.setPromptText("Enter name");

        vID.getText();
        vID.setPromptText("Valencia ID");

       // email = new TextField();
        email.setPromptText("email@mail.valenciacollege.edu");
        submitBtn();
        setCancelBtn();

        System.out.println(vID.getText());

    }

    @FXML public void submitBtn(){
        submit.setOnAction(e -> {

            if (vID.getText().equals("")) {
                vID.clear();
                name.clear();
                email.clear();
            } else {
            student = new Student(name.getText(), vID.getText(), email.getText(), bridges.isSelected());
            System.out.println(student.getName());
            System.out.println("submit was clicked");
                try {
                    if (AppData.getAppData().isExist(student.getvID())) {
                        AppData.getAppData().addSingleStudent(student);
                        System.out.println("from the Controller: " + AppData.getAppData().getStudent().getName());
                        main.pageSwitch("signInview.fxml");
                    } else {
                        AppData.getAppData().addInitData(student);
                        main.pageSwitch("signInview.fxml");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @FXML public void setCancelBtn(){
        cancel.setOnAction(event -> {
            try {
                main.pageSwitch("initialScreen.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
