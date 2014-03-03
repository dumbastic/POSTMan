package pos.model.command;

import pos.model.sales.ShoppingCart;

/**
 * Created by dumbastic on 24/02/14.
 * concrete class for command pattern
 */
public class RemoveCommand implements ICommand {

    String itemID;
    ShoppingCart shoppingCart;

    public RemoveCommand( ShoppingCart shoppingCart ){
        this.shoppingCart = shoppingCart;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    /**
     * remove cartItem from shoppingCart object based on itemID
     * @throws Exception
     */
    public void execute() throws Exception {
        if (itemID == null) {
            throw new Exception("Please Select An Item First!");
        }
        else {
            shoppingCart.removeCartItem(itemID.toString());
        }
    }
}