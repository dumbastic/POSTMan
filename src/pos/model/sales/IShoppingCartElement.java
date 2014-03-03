package pos.model.sales;

import java.math.BigDecimal;

/**
 * Created by dumbastic on 22/02/14.
 */
public interface IShoppingCartElement {
    public void accept(IShoppingCartVisitor shoppingCartVisitor);
}
