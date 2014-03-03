package pos.view.gui;

import pos.model.sales.CartItem;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dumbastic on 02/03/14.
 * this class is used to render the cart list
 */
public class CartListRenderer extends DefaultListCellRenderer {
    public CartListRenderer() { }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        CartItem cartItem = (CartItem)value;
        //set custom display for the list
        setText(
            cartItem.getQuantity() + "x " +
            cartItem.getProduct().getName() + " @ £" +
            cartItem.getProduct().getPrice() + " = £" +
            cartItem.getSubtotal()
        );
        return this;
    }
}
