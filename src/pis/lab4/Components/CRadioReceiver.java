package pis.lab4.Components;
import pis.lab4.Action;

public class CRadioReceiver extends SmartComponent {
    private enum Channels {
        TEL_AVIV, BBC_5, KISS, TALK_SPORT, MANX, SMOOTH, HEART, ABSOLUTE;

        private static Channels[] values = values();
        public Channels next()
        {
            return values[(this.ordinal() + 1) % values.length];
        }
        public Channels prev()
        {
            return values[(this.ordinal() - 1) % values.length];
        }
    };

    private Channels CurrentChannel = Channels.TEL_AVIV;

    public CRadioReceiver(String name)
    {
        super("RR", name);
        AddAction(new Action(() -> { NextChannel(); }, "Selected next channel for radio " + GetComponentFullName()));
        AddAction(new Action(() -> { PrevChannel(); }, "Selected previous channel for radio " + GetComponentFullName()));
    }

    public void NextChannel()
    {
        CurrentChannel = CurrentChannel.next();
    }

    public void PrevChannel()
    {
        CurrentChannel = CurrentChannel.prev();
    }
}
