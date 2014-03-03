package pos.model.sales;

/**
 * Created by dumbastic on 22/02/14.
 * this class is used to store vat inside shopping cart
 */
public class VAT implements IShoppingCartElement {

    private int vat;
    private ShoppingCart shoppingCart;

    public VAT(int vat) {
        super();
        this.vat = vat;
    }

    @Override
    public void accept(IShoppingCartVisitor shoppingCartVisitor) {
        shoppingCartVisitor.visitVAT(this);
    }

    public int getVat() {
        return vat;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
