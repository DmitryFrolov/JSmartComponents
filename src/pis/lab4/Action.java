package pis.lab4;

public class Action {
    // function to be called when action is triggered
    private Runnable ActionItem;

    // description of this action
    private String ActionDescription;

    // do we need to print a message when action completed
    private boolean NotifyActionDone = true;

    public Action(Runnable actionItem, String actionDescription)
    {
        this.ActionItem = actionItem;
        this.ActionDescription = actionDescription;
    }

    public Action(Runnable actionItem, String actionDescription, boolean notifyActionDone)
    {
        this.ActionItem = actionItem;
        this.ActionDescription = actionDescription;
        this.NotifyActionDone = notifyActionDone;
    }

    public String Describe()
    {
        return ActionDescription;
    }

    public void Trigger()
    {
        this.ActionItem.run();
        if(NotifyActionDone)
            System.out.println("Action \"" + Describe() + "\" done");
    }
}
