package pos.model.sales;

/**
 * Created by dumbastic on 22/02/14.
 * this class is used to store discount inside shopping cart
 */
public class Discount implements IShoppingCartElement {

    private int discount;
    private ShoppingCart shoppingCart;

    public Discount(int discount) {
        super();
        this.discount = discount;
    }

    @Override
    public void accept(IShoppingCartVisitor shoppingCartVisitor) {
        shoppingCartVisitor.visitDiscount(this);
    }

    public int getDiscount() {
        return discount;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

}
