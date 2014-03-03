package pos.model.memento;

import pos.model.sales.ShoppingCart;

/**
 * Created by dumbastic on 01/03/14.
 * memento class for memento pattern
 */
class ShoppingCartMemento {
    private ShoppingCart cartState;

    /**
     * set the memento state
     * @param cartState
     */
    public ShoppingCartMemento(ShoppingCart cartState) {
        this.cartState = ShoppingCart.copy(cartState);
    }

    public ShoppingCart getSavedCart() {
        return this.cartState;
    }
}
