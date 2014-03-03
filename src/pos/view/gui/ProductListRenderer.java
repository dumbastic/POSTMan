package pos.view.gui;

import pos.model.product.ProductCatalog;
import pos.model.product.ProductSpecification;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dumbastic on 02/03/14.
 * this class is used to render the product list
 */
public class ProductListRenderer extends DefaultListCellRenderer {
    public ProductListRenderer() { }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        ProductSpecification product = ProductCatalog.getInstance().getProductSpecification(value.toString());
        //set custom display for the list
        setText("[" + product.getID() + "] " + product.getName() + " -  £" + product.getPrice());
        return this;
    }
}