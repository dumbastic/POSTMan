package pos.model.payment;

import java.math.BigDecimal;

/**
 * Created by dumbastic on 22/02/14.
 * concrete class for strategy pattern
 */
public class CashPayment extends PaymentStrategy {

    public CashPayment(BigDecimal totalAmount) {
        super(totalAmount);
    }

    public BigDecimal getChangeAmount(BigDecimal cashPaid) {
        return cashPaid.subtract(super.amount);
    }

    @Override
    public String pay() {
        return "Cash Payment " + super.getAmount();
    }
}
