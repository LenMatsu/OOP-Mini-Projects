package parcel.exception;

public class SameDayNotAllowedException extends Exception {
    public SameDayNotAllowedException() {
        super("Same Day delivery is only for parcels up to 10 kg!");
    }
}
