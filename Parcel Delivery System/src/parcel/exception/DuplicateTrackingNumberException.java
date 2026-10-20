package parcel.exception;

public class DuplicateTrackingNumberException extends Exception {
    public DuplicateTrackingNumberException(String trackingNumber) {
        super("Tracking number " + trackingNumber + " already exists!");
    }
}
