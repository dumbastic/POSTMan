package pos.model.memento;

import java.util.ArrayList;

/**
 * Created by dumbastic on 01/03/14.
 * caretaker class for memento pattern
 */
public class ShoppingCartCaretaker {
    ArrayList<ShoppingCartMemento> cartStates = new ArrayList<ShoppingCartMemento>();

    /**
     * add new memento to list of cartStates
     * @param shoppingCartMemento
     */
    public void addMemento(ShoppingCartMemento shoppingCartMemento) {
        cartStates.add(shoppingCartMemento);
    }

    /**
     * get memento from cartStates
     * @param index
     * @return
     */
    public ShoppingCartMemento getMemento(int index) {
        return cartStates.get(index);
    }

    public int getSize() {
        return cartStates.size();
    }
}
