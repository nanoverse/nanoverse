package nanoverse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lizzybradley on 1/23/16.
 */
public class UserInterface extends Application {
    private static AtomicBoolean isRunningFlag;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userInterface.fxml"));
        primaryStage.setScene(new Scene((Pane) loader.load(), 300, 275));
        primaryStage.setTitle("Nanoverse UI");

        UserInterfaceController controller = loader.<UserInterfaceController>getController();
        controller.setIsRunningFlag(isRunningFlag);

        primaryStage.show();
    }

    public void show() {
        launch();
    }

    public void setIsRunningFlag(AtomicBoolean isRunningFlag) {
        UserInterface.isRunningFlag = isRunningFlag;
    }

    public UserInterface() {
        // Must be an empty constructor
    }
}


