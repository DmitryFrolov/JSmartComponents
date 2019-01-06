package pis.lab4.Components;
import java.util.ArrayList;

public class Builder {
    // objects available to build
    private ArrayList <String> Menu = new ArrayList<String>();

    public Builder()
    {
        this.Menu.add("Heater");
        this.Menu.add("Lamp");
        this.Menu.add("RadioReceiver");
        this.Menu.add("СoffeeMachine");
        this.Menu.add("Refrigerator");
    }

    public SmartComponent Build(int componentIdx, String componentName)
    {
        SmartComponent builtComponent;
        switch(componentIdx)
        {
            case 0:
                builtComponent = new CHeater(componentName);
                break;
            case 1:
                builtComponent = new CLamp(componentName);
                break;
            case 2:
                builtComponent = new CRadioReceiver(componentName);
                break;
            case 3:
                builtComponent = new CCoffeeMachine(componentName);
                break;
            case 4:
                builtComponent = new CRefrigerator(componentName);
                break;
            default:
                throw new IllegalArgumentException(componentIdx + " is not available component index");
        }
        return builtComponent;
    }

    public void PrintAvailableComponents()
    {
        System.out.println("Available components:");
        var index = 0;
        for(String componentName : this.Menu)
        {
            System.out.println((index++) + ") " + componentName);
        }
    }
}
