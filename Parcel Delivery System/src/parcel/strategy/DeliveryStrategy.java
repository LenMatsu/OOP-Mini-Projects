package parcel.strategy;

import parcel.model.Parcel;

import java.math.BigDecimal;

public interface DeliveryStrategy {
    String getName();

    boolean isAllowedFor(Parcel parcel);

    BigDecimal calculateFee(Parcel parcel);
}