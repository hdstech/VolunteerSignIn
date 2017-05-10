package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static Stage window;
    private static Parent parent;
    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        window.initStyle(StageStyle.UNIFIED);
        Parent root = FXMLLoader.load(getClass().getResource("initialScreen.fxml"));
        window.setTitle("Volunteer Sign In");
        window.setScene(new Scene(root));
        window.show();
    }
    void pageSwitch(String fxml) throws Exception{
        window.close();
        //window.initStyle(UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        //root.getStylesheets().addAll("tableview.css", "rootWelcomePage.css");
        window.setScene(new Scene(root));
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
