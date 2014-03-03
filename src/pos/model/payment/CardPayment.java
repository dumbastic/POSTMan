package pos.model.payment;

import java.math.BigDecimal;

/**
 * Created by dumbastic on 22/02/14.
 * concrete class for strategy pattern
 */
public class CardPayment extends PaymentStrategy {

    private String pin;

    public CardPayment(BigDecimal totalAmount) {
        super(totalAmount);
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String pay() {
        return "Card Payment " + super.getAmount();
    }
}
