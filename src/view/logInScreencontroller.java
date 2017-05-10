package view;

import com.jfoenix.controls.JFXButton;
import controller.AppData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class logInScreencontroller implements Initializable {

    @FXML
    private TextField vID = new TextField();

    @FXML
    private JFXButton submitBtn = new JFXButton();

    @FXML
    private JFXButton cancelBtn = new JFXButton();

    @FXML
    private Label warning = new Label("User doesn't exist. Check Valencia ID");
    private Student student;
    private Main main = new Main();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        warning.setVisible(false);
        submitButton();
        setCancelBtn();
    }

    @FXML public void submitButton(){
        submitBtn.setOnAction(event -> {
            if (vID.getText().isEmpty()) {
                vID.clear();
                warning.setVisible(true);
            } else {
                student = new Student(vID.getText());
                System.out.println(student.getvID());
                System.out.println("submit was clicked");
                Main main = new Main();
                try {
                    if (AppData.getAppData().isExist(student.getvID())) {
                        AppData.getAppData().addSingleStudent(student);
                        main.pageSwitch("signInview.fxml");
                    } else {
                        vID.clear();
                        warning.setVisible(true);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @FXML public void setCancelBtn(){
        cancelBtn.setOnAction(event -> {
            try {
                main.pageSwitch("initialScreen.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
