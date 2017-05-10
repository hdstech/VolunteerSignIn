package view;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class initialScreencontroller implements Initializable{

    @FXML
    private JFXButton signInBtn = new JFXButton();

    @FXML
    private JFXButton newUserBtn = new JFXButton();
    private Main main = new Main();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSignInBtn();
        setNewUserBtn();
    }

    @FXML
    private void setSignInBtn() {
        signInBtn.setOnAction(event -> {
            try {
                main.pageSwitch("logInScreen.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void setNewUserBtn(){
        newUserBtn.setOnAction(event -> {
            try {
                main.pageSwitch("mainScreen.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}