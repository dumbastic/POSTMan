package pos.model.sales;

import java.math.BigDecimal;
import java.util.Iterator;

/**
 * Created by dumbastic on 22/02/14.
 */
public class SalesOrderVisitor implements IShoppingCartVisitor {

    ShoppingCart shoppingCart;

    @Override
    public void visitCartItem(CartItem cartItem) {
        shoppingCart.setTotalBill(ShoppingCart.BillState.TOTAL_AMOUNT,
                shoppingCart.getTotalBill(ShoppingCart.BillState.TOTAL_AMOUNT).add(cartItem.getSubtotal())
        );
    }

    @Override
    public void visitDiscount(Discount discount) {
        shoppingCart.setTotalBill(ShoppingCart.BillState.AFTER_DISCOUNT,
                shoppingCart.getTotalBill(ShoppingCart.BillState.TOTAL_AMOUNT).multiply(
                        new BigDecimal(100 - discount.getDiscount())
                ).divide(new BigDecimal(100))
        );
    }

    @Override
    public void visitVAT(VAT vat) {
        shoppingCart.setTotalBill(ShoppingCart.BillState.AFTER_VAT,
                shoppingCart.getTotalBill(ShoppingCart.BillState.AFTER_DISCOUNT).multiply(
                        new BigDecimal(100 + vat.getVat())
                ).divide(new BigDecimal(100))
        );
    }

    @Override
    public void visitShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;

        Iterator iterator = this.shoppingCart.getCartItems().values().iterator();
        while(iterator.hasNext()) {
            IShoppingCartElement shoppingCartElement = (IShoppingCartElement)iterator.next();
            shoppingCartElement.accept(this);
        }
    }

}
