package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by hswea on 12/28/2016.
 */
public class Activities extends RecursiveTreeObject<Activities>{
    private SimpleStringProperty event;
    private SimpleStringProperty timeIn;
    private SimpleStringProperty timeOut; //= new SimpleStringProperty("1200");
    private SimpleStringProperty date;

    public Activities(){
        this.event = new SimpleStringProperty("event");
        this.timeIn = new SimpleStringProperty("1200");
        this.timeOut = new SimpleStringProperty("1200");
        this.date = new SimpleStringProperty("9999-99-99");
    }

    public Activities(String date, String event, String timeIn) {
        this.event = new SimpleStringProperty(event);
        this.timeIn = new SimpleStringProperty(timeIn);
        //this.timeOut = timeOut;
        this.date = new SimpleStringProperty(date);
    }

    public Activities(String date, String event, String timeIn, String timeOut) {
        this.event = new SimpleStringProperty(event);
        this.timeIn = new SimpleStringProperty(timeIn);
        this.timeOut = new SimpleStringProperty(tempTimeOut());
        this.date = new SimpleStringProperty(date);
    }
    public Activities(String timeOut) {
        this.timeOut = new SimpleStringProperty(timeOut);
    }

    public String getEvent() {
        return event.get();
    }

    public void setEvent(String event) {
        this.event.set(event);
    }

    public String getTimeIn(){
        String textTime = timeIn.getValue();
        StringTokenizer token = new StringTokenizer(textTime, " :");
        StringBuilder number = new StringBuilder();
        while (token.hasMoreTokens()){
            number.append(token.nextToken());
        }
        int mod = Integer.parseInt(number.toString().substring(0,2));
        textTime = String.valueOf(mod) + textTime.substring(2);

        if(mod > 12){
            mod = mod % 12;
            if(mod <= 10) {
                textTime = "0" + String.valueOf(mod).concat(":").concat(textTime.substring(2)) + " pm";
                return textTime;
            } else { // afternoon values greater than or equal to 10pm
                textTime = String.valueOf(mod).concat(":").concat(textTime.substring(2)) + " pm";
            }
        } else if (mod == 12) { //prevents 12 noon from being equal to 0
            textTime = String.valueOf(12).concat(":") + "00 pm";
            return textTime;
        } else if (mod < 10){
            textTime = "0" + String.valueOf(mod).concat(":") + "00 am";
        } else {
            textTime = String.valueOf(mod).concat(":") + "00 am";
        }
        return textTime;
        //return this.timeIn.get();
    }

    public String getFormattedTimeIn( String timeIn) {
        String format = timeIn.replaceAll("[^0-9]", "");
        String usTime = SimpleDateFormat
                .getTimeInstance(SimpleDateFormat.SHORT, Locale.US)
                .format(Long.parseLong(format));
        return usTime;
    }

    public int getFormattedTimeOut(){
        //int time = 0;
        String format = timeIn.getValue().replaceAll("[^A-Za-z:]","");
        int time = Integer.parseInt(format);
        return time;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn.set(timeIn);
    }

    public String tempTimeOut(){
        String time;
        int newtime = Integer.parseInt(timeIn.getValue());
        if (newtime < 1000)
            newtime += 1000;
        else
            newtime += 100;
        time  = String.valueOf(newtime);
        return time;
    }

    public String getTimeOut() {
        String textTime = timeOut.getValue();
        StringTokenizer token = new StringTokenizer(textTime, " :");
        StringBuilder number = new StringBuilder();
        while (token.hasMoreTokens()){
            number.append(token.nextToken());
        }
        int mod = Integer.parseInt(number.toString().substring(0,2));
        textTime = String.valueOf(mod) + textTime.substring(2);

        if(mod > 12){
            mod = mod % 12;
            if(mod < 10) {
                textTime = "0" + String.valueOf(mod).concat(":").concat(textTime.substring(2)) + " pm";
                return textTime;
            } else { // afternoon values greater than or equal to 10pm
                textTime = String.valueOf(mod).concat(":").concat(textTime.substring(2)) + " pm";
            }
        } else if (mod == 12) { //prevents 12 noon from being equal to 0
            textTime = String.valueOf(12).concat(":") + "00 pm";
            return textTime;
        } else if (mod < 10){
            textTime = "0" + String.valueOf(mod).concat(":") + "00 am";
            } else {
                textTime = String.valueOf(mod).concat(":") + "00 am";
            }
        return textTime;
       //return timeOut.get();
    }

    public void setTimeOut(String timeOut) {
        this.timeOut.set(timeOut);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public int hoursWorked(String endTime, String startTime){
        StringTokenizer t1 = new StringTokenizer(endTime, " :amp");
        StringTokenizer t2 = new StringTokenizer(startTime, " :amp");
        StringBuilder num1 = new StringBuilder();
        StringBuilder num2 = new StringBuilder();
        String number1;
        String number2;
        while (t1.hasMoreTokens()){
            num1.append(t1.nextToken());
        }
        while (t2.hasMoreTokens()){
            num2.append(t2.nextToken());
        }
        if(num1.charAt(0) == '0'){
            number1 = num1.substring(1);
        } else if(num2.charAt(0) == '0'){
            number2 = num2.substring(1);
        }
            number1 = num1.toString();
            number2 = num2.toString();

        int calcnum1 = Integer.parseInt(number1);
        int calcnum2 = Integer.parseInt(number2);
        if((calcnum1 < 1200) && endTime.substring(3).contains("pm")) {
            calcnum1 += 1200;
        }
        if((calcnum2 < 1200) && startTime.substring(3).contains("pm")) {
            calcnum2 += 1200;
        }
        return (calcnum1 - calcnum2) / 100;
    }

    public int hoursToInt(String hours){
        StringTokenizer t2 = new StringTokenizer(hours, " :amp");
        StringBuilder num2 = new StringBuilder();
        String number2;
        while (t2.hasMoreTokens()){
            num2.append(t2.nextToken());
        }
        if(num2.charAt(0) == '0'){
            number2 = num2.substring(1);
        } else{
            number2 = num2.toString();
        }
        int calcnum2 = Integer.parseInt(number2);
        if(calcnum2 < 1200){
            calcnum2 += 1200;
        }
        return calcnum2;
    }
}
