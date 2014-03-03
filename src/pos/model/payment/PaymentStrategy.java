package pos.model.payment;

import java.math.BigDecimal;

/**
 * Created by dumbastic on 22/02/14.
 * abstract class for payment strategy
 */
public abstract class PaymentStrategy {
    protected BigDecimal amount;

    public PaymentStrategy( BigDecimal totalAmount ) {
        amount = totalAmount;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    /**
     * this method will be implemented inside concrete class for strategy pattern
     * @return
     */
    public abstract String pay();
}
