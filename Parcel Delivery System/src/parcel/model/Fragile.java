package parcel.model;

import parcel.exception.InvalidInputException;

import java.math.BigDecimal;

public class Fragile extends Box {
    public static final double MAX_WEIGHT = 30.0;
    private static final BigDecimal HANDLING_FEE = BigDecimal.valueOf(100);

    private final String handlingNote;

    public Fragile(String trackingNumber, String receiverName, double weight,
                   int length, int width, int height, String handlingNote) throws InvalidInputException {
        super(trackingNumber, receiverName, weight, length, width, height, MAX_WEIGHT);
        this.handlingNote = Validator.nonEmpty(handlingNote, "Handling note");
    }

    @Override
    public String getType() {
        return "Fragile";
    }

    @Override
    public BigDecimal getBaseFee() {
        return super.getBaseFee().add(HANDLING_FEE);
    }

    public String getHandlingNote() {
        return handlingNote;
    }
}