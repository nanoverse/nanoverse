package nanoverse;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lizzybradley on 1/23/16.
 */
public class UserInterfaceRunnable implements Runnable {
    private final AtomicBoolean isRunningFlag;

    @Override
    public void run() {
        UserInterface ui = new UserInterface();
        ui.setIsRunningFlag(this.isRunningFlag);

        ui.show();
    }

    public UserInterfaceRunnable(AtomicBoolean isRunningFlag) {
        this.isRunningFlag = isRunningFlag;
    }
}
