package pis.lab4;
import pis.lab4.Components.SmartComponent;
import pis.lab4.Components.Builder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

// Manages all components accordingly to stdin requests
public class Core {
    private ArrayList <SmartComponent> AllComponents = new ArrayList<>();

    private ArrayList <Action> StartMenuActions = new ArrayList<>();

    private Builder BuilderInst = new Builder();

    private boolean Quit = false;

    Scanner reader = new Scanner(System.in);

    public Core()
    {
        StartMenuActions.add(new Action(() -> { ActionShowHelpMessage(); }, "Show this msg", false));
        StartMenuActions.add(new Action(() -> { ActionAddComponent(); }, "Add component", false));
        StartMenuActions.add(new Action(() -> { ActionRemoveComponent(); }, "Remove component", false));
        StartMenuActions.add(new Action(() -> { ActionListAllComponents(); }, "List all components", false));
        StartMenuActions.add(new Action(() -> { ActionExecuteTaskNow(); }, "Execute task now", false));
        StartMenuActions.add(new Action(() -> { ActionScheduleTask(); }, "Schedule task", false));
        StartMenuActions.add(new Action(() -> { ActionQuit(); }, "Quit", false));
    }

    public void run()
    {
        ActionShowHelpMessage();
        while(!Quit)
        {
            try {
                System.out.println("Select operation:");
                var operation = Integer.parseInt(reader.nextLine());
                StartMenuActions.get(operation).Trigger();
            } catch (Exception e) {
                System.out.println("Invalid operation ID");
            }
        }
    }

    // add new component
    private void ActionAddComponent()
    {
        RepeatTillSuccess(() -> {
            System.out.println("Select type(index in list) of the component.");
            BuilderInst.PrintAvailableComponents();

            var type = Integer.parseInt(reader.nextLine());

            System.out.println("Set name for this component");
            var name = reader.nextLine();
            if(name.isEmpty())
                throw new RuntimeException("Name is empty");
            AllComponents.add(BuilderInst.Build(type, name));
        });
    }

    // Removes component from list available
    private void ActionRemoveComponent()
    {
        if(AllComponents.isEmpty()) {
            System.out.println("No components exists");
            return;
        }

        RepeatTillSuccess(() -> {
            System.out.println("Input index of the component to remove.");
            var index = Integer.parseInt(reader.nextLine());
            AllComponents.remove(index);
        });
    }

    // print all existing components with indices
    private void ActionListAllComponents()
    {
        var idx = 0;
        for(SmartComponent component : AllComponents)
        {
            System.out.println((idx++) + ") " + component.GetComponentFullName());
        }
    }

    // Schedules action to be executed RN
    private void ActionExecuteTaskNow()
    {
        RepeatTillSuccess(() -> {
            ScheduleTaskForComponent(SelectComponent(), new Date());
        });
    }

    // Schedules action to be executed later
    private void ActionScheduleTask()
    {
        RepeatTillSuccess(() -> {
            ScheduleTaskForComponent(SelectComponent(), ReadDateFromInput());
        });
    }

    // Simply print all available actions
    private void ActionShowHelpMessage()
    {
        var idx = 0;
        for(Action action : StartMenuActions)
        {
            System.out.println((idx++) + ") " + action.Describe());
        }
    }

    // Quit the program
    private void ActionQuit()
    {
        Quit = true;
    }

    // Continues to call Runnable object till no exception is thrown
    // Used for input handling
    private void RepeatTillSuccess(Runnable methodToRepeat)
    {
        // to avoid infinite error loop
        int repeatsLeft = 5;
        boolean errorOccurred;
        do {
            errorOccurred = false;
            try {
                methodToRepeat.run();
            } catch (Exception e) {
                errorOccurred = true;
                repeatsLeft--;
                System.out.println(e.getMessage());
                System.out.println("Invalid input! Try again... repeats = " + repeatsLeft);
            }
        } while (errorOccurred && repeatsLeft > 0);
    }

    // Select component from list available by idx and return it
    private SmartComponent SelectComponent()
    {
        do {
            try {
                System.out.println("Select component id:");
                var idx = Integer.parseInt(reader.nextLine());
                return AllComponents.get(idx);
            } catch (Exception e) {
                System.out.println("Invalid input! Try again...");
            }
        } while (true);
    }

    private void ScheduleTaskForComponent(SmartComponent targetComponent, Date taskTriggerTime)
    {
        targetComponent.PrintActions();
        var idx = Integer.parseInt(reader.nextLine());
        Scheduler.Schedule(targetComponent.GetActionForScheduling(idx), taskTriggerTime);
    }

    // Read date from input and return built object
    private Date ReadDateFromInput()
    {
        System.out.println("Input date in format: {" + "dd MM yyyy HH:mm:ss" + "}");
        System.out.println("Example: {" + "12 12 2013 22:12:30" + "}");
        System.out.println(new Date());

        String dateString = reader.nextLine();
        DateFormat formatter = new SimpleDateFormat("dd MM yyyy HH:mm:ss");

        Date actualDate;
        try {
            actualDate = formatter.parse(dateString);
        } catch (Exception e) {
            throw new RuntimeException("Wrong date format");
        }
        return actualDate;
    }
}
