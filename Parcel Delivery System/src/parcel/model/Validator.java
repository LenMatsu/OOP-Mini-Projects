package parcel.model;

import parcel.exception.InvalidInputException;
import java.util.regex.Pattern;

public final class Validator {
    private static final Pattern TRACKING = Pattern.compile("^ABC\\d{4}$");

    public static final int MAX_DIMENSION = 300;

    private Validator() {
    }

    public static String trackingNumber(String value) throws InvalidInputException {
        String v = value == null ? "" : value.trim().toUpperCase();
        if (!TRACKING.matcher(v).matches()) {
            throw new InvalidInputException("Tracking number must be \"ABC\" followed by 4 digits (e.g. ABC0001).");
        }
        return v;
    }

    public static String nonEmpty(String value, String field) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(field + " must not be empty.");
        }
        if (value.trim().length() > 100) {
            throw new InvalidInputException(field + " must not exceed 100 characters.");
        }
        return value.trim();
    }

    public static double weight(double value, double max) throws InvalidInputException {
        if (Double.isNaN(value) || value <= 0) {
            throw new InvalidInputException("Weight must be greater than 0.");
        }
        if (value > max) {
            throw new InvalidInputException("Weight must not exceed " + max + " kg for this parcel type.");
        }
        return value;
    }

    public static int range(int value, int min, int max, String field) throws InvalidInputException {
        if (value < min || value > max) {
            throw new InvalidInputException(field + " must be between " + min + " and " + max + ".");
        }
        return value;
    }

    public static int dimension(int value, String field) throws InvalidInputException {
        if (value <= 0 || value > MAX_DIMENSION) {
            throw new InvalidInputException(field + " must be between 1 and " + MAX_DIMENSION + " cm.");
        }
        return value;
    }

    public static int positive(int value, String field) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(field + " must be greater than 0.");
        }
        return value;
    }
}