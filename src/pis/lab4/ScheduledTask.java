package pis.lab4;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
    Action ActionToRun;

    public ScheduledTask(Action action)
    {
        ActionToRun = action;
    }

    @Override
    public void run() {
        ActionToRun.Trigger();
        System.out.println(ActionToRun.Describe());
    }
}
