package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

import model.Activities;
import model.Student;

/**
 * Created by hswea on 12/28/2016.
 */
public class AppData {
    private static AppData appData;
    private ResultSet result;
    private PreparedStatement prepStatement;
    private Statement stmt = null;
    private static Connection conn;
    private Student student = new Student();

    private ArrayList<Student> tableData = new ArrayList<Student>();

    private AppData(){
    }
    public static AppData getAppData(){
        if(appData == null){
            appData = new AppData();
        }
        return appData;
    }

    public void addSingleStudent(Student student){
        this.student = student;
    }

    public Student getStudent(){
        return student;
    }

    public Student dbStudent(){
        //Student myStudent = new Student();
        System.out.println("in dbStudent class: " + student.getvID());
        String query = "SELECT * FROM studentinfo_table WHERE vID = '" + student.getvID() + "'";
        try{
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
            while(result.next()) {
                student.setName(result.getString("name"));
                student.setvID(result.getString("vID"));
                student.setEmail(result.getString("email"));
                student.setBridges(result.getBoolean("bridges"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return student;
    }

    //Add initial student data to database
    public void addInitData(Student student) throws SQLException {
        /*
        this.student.add(student);
        studentVid = student.getvID();
        */
        String query = "INSERT INTO studentinfo_table (name, vID, email, bridges) VALUES (?,?,?,?)";
        try {
            conn = DatabaseConnection.getConnection();

            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, student.getName());
            prepStatement.setString(2, student.getvID());
            prepStatement.setString(3, student.getEmail());
            prepStatement.setBoolean(4, student.isBridges());
            prepStatement.executeUpdate();
            System.out.println("updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
            System.out.println("connection closed");
        }
    }

    //Check if students already exists
    public Boolean isExist(String vID) throws Exception {
        Boolean check = true;
        String query = "SELECT * FROM studentinfo_table WHERE vID = '" + vID + "'";

        conn = DatabaseConnection.getConnection();
        stmt = conn.createStatement();
        result = stmt.executeQuery(query);
        if(result.wasNull()) check = false;
        return check;
    }

    //Populates table
    public ObservableList<Activities> getStudentActivities() {
        //System.out.println("Observable list student: " + student.getvID());
        String query = "SELECT date, event, timeIn, timeOut FROM events_table WHERE studentID = '" + student.getvID() + "'";
        //System.out.println(student.getvID());

        ObservableList<Activities> activity = FXCollections.observableArrayList();
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);

            if(!result.next()){
                activity.add(new Activities(
                        result.getString("date"),
                        result.getString("event"),
                        result.getString("timeIn"),
                        result.getString("timeOut")
                ));
            } else{
            while (result.next()) {
                Activities activities = new Activities(
                        result.getString("date"),
                        result.getString("event"),
                        result.getString("timeIn"),
                        result.getString("timeOut")
                        );
                activity.add(activities);
                };
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return activity;
    }

    //Add student volunteer activity
    public void addActivity(Activities activity) {
        //System.out.println("adding activity of student: " + activity.getTimeIn());
        String query = "INSERT INTO events_table (event, timeIn, timeOut, date, studentID)"
                + "VALUES( ?, ?, ?, ?, ?)";

        try {
            conn = DatabaseConnection.getConnection();
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, activity.getEvent());
            prepStatement.setString(2, activity.getTimeIn());
            prepStatement.setString(3, activity.getTimeOut());
            prepStatement.setString(4, activity.getDate());
            prepStatement.setString(5, student.getvID());
            prepStatement.executeUpdate();
            System.out.println("updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
