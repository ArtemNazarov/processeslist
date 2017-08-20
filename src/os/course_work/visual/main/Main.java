package os.course_work.visual.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import os.course_work.threads.ProgramThreads;

import java.io.File;
import java.util.Timer;

public class Main extends Application {

    private static Stage primary;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primary = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("mainFrame.fxml"));
        File mainIconFile = new File("resources/icons/list.png");
        primaryStage.getIcons().add(new Image(mainIconFile.toURI().toURL().toString()));
        primaryStage.setOnCloseRequest(event -> {
            stopThreads();
        });
        primaryStage.setTitle("ProcessMonitor");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    //метод остановки всех запущенных потоков, кроме главного
    void stopThreads(){
        ProgramThreads.tasks.forEach(Timer::cancel);
        ReadTable.getTableStage().close();
    }

    public static Stage getPrimary() {
        return primary;
    }

    public static void closeApp(){
        primary.close();
    }


}
