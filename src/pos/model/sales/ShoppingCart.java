package pos.model.sales;

/**
 * Created by dumbastic on 22/02/14.
 */
import pos.model.payment.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * ShoppingCart class
 * contains list of IShoppingCartElement which can be CartItem, Discount, or VAT
 */
public class ShoppingCart implements IShoppingCartElement {

    private String transactionID;
    private Date transactionDate;
    private Map<String, IShoppingCartElement> cartItems = new LinkedHashMap<String, IShoppingCartElement>();
    private BigDecimal totalBill = BigDecimal.ZERO;
    private BigDecimal afterDiscount = BigDecimal.ZERO;
    private BigDecimal afterVAT = BigDecimal.ZERO;

    private PaymentStrategy paymentStrategy;

    public ShoppingCart(){
        this.transactionID = UUID.randomUUID().toString();
        this.transactionDate = new Date();
    }

    /**
     * add a new cart item to the cartItems object
     * if the cart item already exist (duplicate ID), then add the item quantity
     * if not, then add the item into the cartItems
     * @param shoppingCartElement
     */
    public void addCartItem(CartItem shoppingCartElement) {
        shoppingCartElement.setShoppingCart(this);
        String ID = shoppingCartElement.getProduct().getID();
        if (cartItems.containsKey(ID))
            ((CartItem)cartItems.get(ID)).addQuantity();
        else
            cartItems.put(shoppingCartElement.getProduct().getID(), shoppingCartElement);
    }

    /**
     * remove a cart item from cartItems based on itemID
     * if the item's quantity is more than 1, then subtract the quantity
     * if not, then remove the item from cartItems
     * @param itemID
     */
    public void removeCartItem(String itemID) {
        CartItem cartItem = (CartItem)cartItems.get(itemID);
        if (cartItem.getQuantity() > 1)
            cartItem.subtractQuantity();
        else
            cartItems.remove(itemID);
    }

    /**
     * add VAT element to the cartItems
     */
    public void addVAT() {
        VAT vat = new VAT(20);
        vat.setShoppingCart(this);
        cartItems.put("vat", vat);
    }

    /**
     * add discount element to the cartItems
     */
    public void giveDiscount() {
        Discount discount = new Discount(10);
        discount.setShoppingCart(this);
        cartItems.put("discount", discount);
    }

    @Override
    public void accept(IShoppingCartVisitor shoppingCartVisitor) {
        shoppingCartVisitor.visitShoppingCart(this);
    }

    public String getTransactionID() {
        return transactionID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Map<String, IShoppingCartElement> getCartItems() {
        return this.cartItems;
    }

    public void clearCartItem() {
        this.cartItems.clear();
    }

    public BigDecimal getTotalBill(BillState billState) {
        switch (billState) {
            case TOTAL_AMOUNT:
                return this.totalBill;
            case AFTER_DISCOUNT:
                return this.afterDiscount;
            case AFTER_VAT:
                return this.afterVAT;
            default:
                return null;
        }
    }

    public void setTotalBill(BillState billState, BigDecimal amount) {
        switch (billState) {
            case TOTAL_AMOUNT:
                this.totalBill = amount;
                break;
            case AFTER_DISCOUNT:
                this.afterDiscount = amount;
                break;
            case AFTER_VAT:
                this.afterVAT = amount;
                break;
        }
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }

    /**
     * part of strategy pattern
     * this method will invoke pay() method from PaymentStrategy concrete object
     */
    public void makePayment() {
        this.paymentStrategy.pay();
    }

    /**
     * copy shoppingCart from another shoppingCart
     * this method is used inside ShoppingCartMemento
     * to avoid the same object reference for all cartState
     * @param anotherCart
     * @return
     */
    public static ShoppingCart copy(ShoppingCart anotherCart) {
        ShoppingCart newCart = new ShoppingCart();
        Iterator iterator = anotherCart.getCartItems().values().iterator();
        while(iterator.hasNext()) {
            newCart.addCartItem(CartItem.copy((CartItem)iterator.next()));
        }
        return newCart;
    }

    /**
     * contain 3 states to distinguish the original total price,
     * total price after discount, and total price after VAT
     */
    public enum BillState{
        TOTAL_AMOUNT,
        AFTER_DISCOUNT,
        AFTER_VAT
    }
}
