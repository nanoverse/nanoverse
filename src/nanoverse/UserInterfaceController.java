package nanoverse;

/**
 * Created by lizzybradley on 1/22/16.
 */
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicBoolean;

public class UserInterfaceController {
    public Label status;
    public Button toggle;
    private AtomicBoolean isRunningFlag;

    public void toggleExecution(ActionEvent actionEvent) {
        boolean isRunning = isRunningFlag.get();

        if (isRunning) {
            isRunningFlag.set(false);
            status.setText("Program Paused");
            toggle.setText("Resume Execution");
        }
        else {
            isRunningFlag.set(true);
            status.setText("Program Running");
            toggle.setText("Pause Execution");
        }
    }

    public void setIsRunningFlag(AtomicBoolean isRunningFlag) {
        this.isRunningFlag = isRunningFlag;
    }
}
