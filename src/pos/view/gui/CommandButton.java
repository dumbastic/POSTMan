package pos.view.gui;

import pos.model.command.ICommand;

import javax.swing.*;

/**
 * Created by dumbastic on 24/02/14.
 */
public class CommandButton extends JButton implements ICommandHolder {
    protected ICommand command;

    public CommandButton(String name){
        super(name);
    }

    public void setCommand(ICommand command){
        this.command = command;
    }

    public ICommand getCommand(){
        return command;
    }
}
