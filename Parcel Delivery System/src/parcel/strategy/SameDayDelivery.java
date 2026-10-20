package parcel.strategy;

import parcel.model.Parcel;

import java.math.BigDecimal;

public class SameDayDelivery implements DeliveryStrategy {
    public static final double MAX_CHARGEABLE_WEIGHT = 10.0;

    @Override
    public String getName() {
        return "Same Day";
    }

    @Override
    public boolean isAllowedFor(Parcel parcel) {
        return parcel.getChargeableWeight() <= MAX_CHARGEABLE_WEIGHT;
    }

    @Override
    public BigDecimal calculateFee(Parcel parcel) {
        return parcel.getBaseFee().multiply(BigDecimal.valueOf(2)).add(BigDecimal.valueOf(100));
    }
}