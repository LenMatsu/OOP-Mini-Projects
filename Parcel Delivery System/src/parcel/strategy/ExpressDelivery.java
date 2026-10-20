package parcel.strategy;

import parcel.model.Parcel;

import java.math.BigDecimal;

public class ExpressDelivery implements DeliveryStrategy {
    @Override
    public String getName() {
        return "Express";
    }

    @Override
    public boolean isAllowedFor(Parcel parcel) {
        return true;
    }

    @Override
    public BigDecimal calculateFee(Parcel parcel) {
        return parcel.getBaseFee().multiply(new BigDecimal("1.5"));
    }
}