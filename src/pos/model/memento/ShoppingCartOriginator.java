package pos.model.memento;

import pos.model.sales.ShoppingCart;

/**
 * Created by dumbastic on 01/03/14.
 * originator class for memento pattern
 */
public class ShoppingCartOriginator {
    private ShoppingCart cartState;

    public void set(ShoppingCart cartState) {
        this.cartState = cartState;
    }

    /**
     * save the current cartState to memento class
     * @return
     */
    public ShoppingCartMemento saveToMemento() {
        return new ShoppingCartMemento(this.cartState);
    }

    /**
     * restore the last saved memento
     * @param memento
     * @return
     */
    public ShoppingCart restoreFromMemento(ShoppingCartMemento memento) {
        return memento.getSavedCart();
    }
}