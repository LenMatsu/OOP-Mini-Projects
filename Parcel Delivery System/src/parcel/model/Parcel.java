package parcel.model;

import parcel.exception.InvalidInputException;
import parcel.exception.SameDayNotAllowedException;
import parcel.strategy.DeliveryStrategy;
import parcel.strategy.StandardDelivery;

import java.math.BigDecimal;

public abstract class Parcel {
    private final String trackingNumber;
    private final String receiverName;
    private final double weight;
    private DeliveryStrategy deliveryStrategy = new StandardDelivery();
    private ParcelStatus status = ParcelStatus.PENDING;

    protected Parcel(String trackingNumber, String receiverName, double weight, double maxWeight)
            throws InvalidInputException {
        this.trackingNumber = Validator.trackingNumber(trackingNumber);
        this.receiverName = Validator.nonEmpty(receiverName, "Receiver name");
        this.weight = Validator.weight(weight, maxWeight);
    }

    public abstract String getType();

    public abstract double getChargeableWeight();

    public abstract BigDecimal getBaseFee();

    public void setDeliveryStrategy(DeliveryStrategy strategy) throws SameDayNotAllowedException {
        if (!strategy.isAllowedFor(this)) {
            throw new SameDayNotAllowedException();
        }
        this.deliveryStrategy = strategy;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryStrategy.calculateFee(this);
    }

    public void markDelivered() {
        this.status = ParcelStatus.DELIVERED;
    }

    public boolean isDelivered() {
        return status == ParcelStatus.DELIVERED;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public double getWeight() {
        return weight;
    }

    public String getServiceName() {
        return deliveryStrategy.getName();
    }

    public ParcelStatus getStatus() {
        return status;
    }
}