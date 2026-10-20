package parcel.service;

import parcel.exception.DuplicateTrackingNumberException;
import parcel.exception.ParcelAlreadyDeliveredException;
import parcel.exception.ParcelNotFoundException;
import parcel.exception.SameDayNotAllowedException;
import parcel.model.Parcel;
import parcel.strategy.DeliveryService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ParcelManager {
    private final Map<String, Parcel> parcels = new LinkedHashMap<>();

    private ParcelManager() {
    }

    private static class Holder {
        private static final ParcelManager INSTANCE = new ParcelManager();
    }

    public static ParcelManager getInstance() {
        return Holder.INSTANCE;
    }

    public boolean exists(String trackingNumber) {
        return parcels.containsKey(trackingNumber);
    }

    public void addParcel(Parcel parcel) throws DuplicateTrackingNumberException {
        if (exists(parcel.getTrackingNumber())) {
            throw new DuplicateTrackingNumberException(parcel.getTrackingNumber());
        }
        parcels.put(parcel.getTrackingNumber(), parcel);
    }

    public List<Parcel> getAllParcels() {
        return new ArrayList<>(parcels.values());
    }

    public Parcel findParcel(String trackingNumber) throws ParcelNotFoundException {
        Parcel parcel = parcels.get(trackingNumber == null ? "" : trackingNumber.trim().toUpperCase());
        if (parcel == null) {
            throw new ParcelNotFoundException();
        }
        return parcel;
    }

    public BigDecimal changeDeliveryService(String trackingNumber, DeliveryService service)
            throws ParcelNotFoundException, ParcelAlreadyDeliveredException, SameDayNotAllowedException {
        Parcel parcel = findParcel(trackingNumber);
        if (parcel.isDelivered()) {
            throw new ParcelAlreadyDeliveredException("Parcel already delivered!");
        }
        parcel.setDeliveryStrategy(service.getStrategy());
        return parcel.getDeliveryFee();
    }

    public void markAsDelivered(String trackingNumber)
            throws ParcelNotFoundException, ParcelAlreadyDeliveredException {
        Parcel parcel = findParcel(trackingNumber);
        if (parcel.isDelivered()) {
            throw new ParcelAlreadyDeliveredException("Parcel was already delivered.");
        }
        parcel.markDelivered();
    }
}