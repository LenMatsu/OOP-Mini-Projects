package parcel.strategy;

import parcel.model.Parcel;

import java.math.BigDecimal;

public class StandardDelivery implements DeliveryStrategy {
    @Override
    public String getName() {
        return "Standard";
    }

    @Override
    public boolean isAllowedFor(Parcel parcel) {
        return true;
    }

    @Override
    public BigDecimal calculateFee(Parcel parcel) {
        return parcel.getBaseFee();
    }
}