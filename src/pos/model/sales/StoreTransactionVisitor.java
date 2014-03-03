package pos.model.sales;

import pos.model.sales.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by dumbastic on 23/02/14.
 */
public class StoreTransactionVisitor implements IShoppingCartVisitor {

    FileWriter writer;
    BufferedWriter bufferedWriter;

    @Override
    public void visitCartItem(CartItem cartItem) {

        try {

            bufferedWriter.write(cartItem.getShoppingCart().getTransactionID());
            bufferedWriter.write(",");
            bufferedWriter.write(cartItem.getShoppingCart().getTransactionDate().toString());
            bufferedWriter.write(",");
            bufferedWriter.write(cartItem.getProduct().getID());
            bufferedWriter.write(",");
            bufferedWriter.write(cartItem.getQuantity());
            bufferedWriter.write(",");
            bufferedWriter.write(cartItem.getSubtotal().toString());
            bufferedWriter.write("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitDiscount(Discount discount) {

    }

    @Override
    public void visitVAT(VAT vat) {

    }

    @Override
    public void visitShoppingCart(ShoppingCart shoppingCart) {
        try {
            writer = new FileWriter("TransactionHistory.csv", true);
            bufferedWriter = new BufferedWriter(writer);

            Iterator iterator = shoppingCart.getCartItems().values().iterator();
            while(iterator.hasNext()) {
                IShoppingCartElement shoppingCartElement = (IShoppingCartElement)iterator.next();
                shoppingCartElement.accept(this);
            }

            bufferedWriter.flush();
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
