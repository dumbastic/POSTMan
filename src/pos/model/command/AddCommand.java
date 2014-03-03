package pos.model.command;

import pos.model.sales.CartItem;
import pos.model.sales.ShoppingCart;

/**
 * Created by dumbastic on 24/02/14.
 * concrete class for command pattern
 */
public class AddCommand implements ICommand {

    String productID;
    ShoppingCart shoppingCart;

    public AddCommand(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * add new cartItem into shoppingCart object
     * @throws Exception
     */
    public void execute() throws Exception {
        if (productID == null) {
            throw new Exception("Please Select An Item First!");
        }
        else {
            shoppingCart.addCartItem(new CartItem(productID, 1));
        }
    }
}