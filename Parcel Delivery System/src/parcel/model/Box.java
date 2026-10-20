package parcel.model;

import parcel.exception.InvalidInputException;

import java.math.BigDecimal;

public class Box extends Parcel {
    public static final double MAX_WEIGHT = 50.0;
    private static final BigDecimal RATE_PER_KG = BigDecimal.valueOf(40);
    private static final int VOLUMETRIC_DIVISOR = 3500;

    private final int length;
    private final int width;
    private final int height;

    public Box(String trackingNumber, String receiverName, double weight,
               int length, int width, int height) throws InvalidInputException {
        this(trackingNumber, receiverName, weight, length, width, height, MAX_WEIGHT);
    }

    protected Box(String trackingNumber, String receiverName, double weight,
                  int length, int width, int height, double maxWeight) throws InvalidInputException {
        super(trackingNumber, receiverName, weight, maxWeight);
        this.length = Validator.dimension(length, "Length");
        this.width = Validator.dimension(width, "Width");
        this.height = Validator.dimension(height, "Height");
    }

    public double getVolumetricWeight() {
        return (double) (length * width * height) / VOLUMETRIC_DIVISOR;
    }

    @Override
    public String getType() {
        return "Box";
    }

    @Override
    public double getChargeableWeight() {
        long volume = (long) length * width * height;
        long volumetricCeil = (volume + VOLUMETRIC_DIVISOR - 1) / VOLUMETRIC_DIVISOR;
        long actualCeil = (long) Math.ceil(getWeight());
        return Math.max(volumetricCeil, actualCeil);
    }

    @Override
    public BigDecimal getBaseFee() {
        return RATE_PER_KG.multiply(BigDecimal.valueOf(getChargeableWeight()));
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}