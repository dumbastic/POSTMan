package pos.view.gui;

import pos.model.command.ICommand;

/**
 * Created by dumbastic on 24/02/14.
 */
public interface ICommandHolder {
    public void setCommand(ICommand command);
    public ICommand getCommand();
}
