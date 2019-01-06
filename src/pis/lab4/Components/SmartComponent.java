package pis.lab4.Components;
import pis.lab4.Action;
import java.util.ArrayList;

public abstract class SmartComponent {

    // Describes all available states for SmartComponent
    private enum State { ON, OFF };

    // Store current state for this SmartComponent
    private State CurrentState = State.OFF;

    // Name of component (any identifier)
    private String ComponentName;

    // Prefix usually is the first letters of each word in component type
    private String ComponentPrefix;

    // all available actions for given SmartComponent
    private ArrayList<Action> AvailableActions;

    SmartComponent(String prefix, String name)
    {
        this.ComponentPrefix = prefix;
        this.ComponentName = name;
        this.AvailableActions = new ArrayList<Action>();

        AddAction(new Action(() -> { Enable(); }, "Enable component " + GetComponentFullName()));
        AddAction(new Action(() -> { Disable(); }, "Disable component " + GetComponentFullName()));
    }

    // FullName = Prefix + ComponentName (w/o spaces)
    public String GetComponentFullName()
    {
        return this.ComponentPrefix + this.ComponentName;
    }

    public void Enable()
    {
        this.CurrentState = State.ON;
    }

    public void Disable()
    {
        this.CurrentState = State.OFF;
    }

    // add a new action to list of available actions for this component
    public void AddAction(Action actionToAdd)
    {
        AvailableActions.add(actionToAdd);
    }

    // print all information about object and its current state to stdout
    public void PrintInfo()
    {
        System.out.println("Component " + GetComponentFullName() + " is currently in state " + CurrentState.name());
    }

    public void PrintActions()
    {
        System.out.println("List of available actions:");
        var idx = 0;
        for(Action action : AvailableActions)
        {
            System.out.println((idx++) + " " + action.Describe());
        }
    }

    public Action GetActionForScheduling(int actionId)
    {
        return AvailableActions.get(actionId);
    }
}
