package pos.model.sales;

import pos.model.product.ProductCatalog;
import pos.model.product.ProductSpecification;

import java.math.BigDecimal;

/**
 * Created by dumbastic on 22/02/14.
 * this class is used to store cart item inside shopping cart
 */
public class CartItem implements IShoppingCartElement {

    private ProductSpecification product;
    private int quantity;
    private ShoppingCart shoppingCart;

    public CartItem( String id, int quantity ) {
        super();
        this.product = ProductCatalog.getInstance().getProductSpecification(id);
        this.quantity = quantity;
    }

    @Override
    public void accept(IShoppingCartVisitor shoppingCartVisitor) {
        shoppingCartVisitor.visitCartItem(this);
    }

    public ProductSpecification getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity() {
        this.quantity++;
    }

    public void subtractQuantity() {
        this.quantity--;
    }

    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public static CartItem copy(CartItem anotherCartItem){
        return new CartItem(anotherCartItem.getProduct().getID(), anotherCartItem.getQuantity());
    }
}