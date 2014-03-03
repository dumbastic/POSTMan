package pos.model.payment;

import java.math.BigDecimal;

/**
 * Created by dumbastic on 22/02/14.
 * concrete class for strategy pattern
 */
public class CouponPayment extends PaymentStrategy {

    public CouponPayment(BigDecimal totalAmount) {
        super(totalAmount);
    }

    /**
     * get coupon value based on coupon type entered
     * @param couponType
     * @return
     */
    private BigDecimal getCouponValue(CouponType couponType) {
        switch (couponType) {
            case CP10:
                return new BigDecimal(10);
            case CP25:
                return new BigDecimal(25);
            case CP50:
                return new BigDecimal(50);
            default:
                return BigDecimal.ZERO;
        }
    }

    /**
     * get cash amount from total amount subtracted by coupon value
     * @param couponType
     * @return
     */
    public BigDecimal getCashAmount(CouponType couponType) {
        BigDecimal cashAmount = super.amount.subtract(getCouponValue(couponType));
        if (cashAmount.compareTo(BigDecimal.ZERO) < 0)
            cashAmount = BigDecimal.ZERO;
        return cashAmount;
    }

    @Override
    public String pay() {
        return "Coupon Payment " + super.getAmount();
    }

    public enum CouponType {
        CP10,
        CP25,
        CP50
    }
}
