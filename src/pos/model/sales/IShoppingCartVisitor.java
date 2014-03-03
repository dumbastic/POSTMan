package pos.model.sales;

import java.math.BigDecimal;

/**
 * Created by dumbastic on 22/02/14.
 */
public interface IShoppingCartVisitor {
    public void visitShoppingCart(ShoppingCart shoppingCart);
    public void visitCartItem(CartItem cartItem);
    public void visitDiscount(Discount discount);
    public void visitVAT(VAT vat);
}
