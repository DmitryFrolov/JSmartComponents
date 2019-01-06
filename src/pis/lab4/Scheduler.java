package pis.lab4;
import java.util.Date;
import java.util.Timer;

public class Scheduler {
    static public void Schedule(Action action, Date runDate)
    {
        ScheduledTask STask = new ScheduledTask(action);
        Timer time = new Timer();
        time.schedule(STask, runDate);
    }
}
