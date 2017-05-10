package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.web.PromptData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hswea on 12/23/2016.
 */
public class Student {
    private String name;
    private String vID;
    private String email;
    private Boolean bridges = false;

    public Student(){
    }

    public Student(String name, String vID, String email, Boolean bridges) {
        setName(name);
        setvID(vID);
        setEmail(email);
        setBridges(bridges);
    }
    public Student(String vID){
        setvID(vID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getvID() {
        return vID;
    }

    public void setvID(String vID) {
        vID = vID.substring(0,1).toUpperCase() + vID.substring(1);
        Pattern pattern = Pattern.compile("V[0-9]{8}");
        Matcher match = pattern.matcher(vID);
        if(match.find()) {
            this.vID = vID;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.setTitle("Error");
            alert.setContentText("Incorrect ID entered");
            alert.getButtonTypes().setAll(okBtn);
            alert.showAndWait();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isBridges() {
        return bridges;
    }

    public void setBridges(Boolean bridges) {
        this.bridges = bridges;
    }
}
