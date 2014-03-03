package pos.model.payment;

import java.math.BigDecimal;

/**
 * Created by dumbastic on 22/02/14.
 * factory method for payment
 * this class will create payment objects based on payment method passed by the caller
 * this class also implement singleton pattern
 */
public class PaymentFactory {
    private static PaymentFactory instance = new PaymentFactory();

    public static PaymentFactory getInstance() {
        if (instance == null)
            instance = new PaymentFactory();
        return instance;
    }

    private PaymentFactory() {

    }

    /**
     * create new payment object based on payment method and total amount to pay
     * @param paymentMethod
     * @param totalAmount
     * @return
     */
    public PaymentStrategy createPayment(PaymentMethod paymentMethod, BigDecimal totalAmount) {
        switch (paymentMethod) {
            case CASH:
                return new CashPayment(totalAmount);
            case CARD:
                return new CardPayment(totalAmount);
            case COUPON:
                return new CouponPayment(totalAmount);
            default:
                return null;
        }
    }
}
