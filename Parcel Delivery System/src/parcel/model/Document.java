package parcel.model;

import parcel.exception.InvalidInputException;

import java.math.BigDecimal;

public class Document extends Parcel {
    public static final double MAX_WEIGHT = 0.5;
    private static final BigDecimal BASE_FEE = BigDecimal.valueOf(50);

    private final int pages;

    public Document(String trackingNumber, String receiverName, double weight, int pages)
            throws InvalidInputException {
        super(trackingNumber, receiverName, weight, MAX_WEIGHT);
        this.pages = Validator.range(pages, 1, 100, "Number of pages");
    }

    @Override
    public String getType() {
        return "Document";
    }

    @Override
    public double getChargeableWeight() {
        return getWeight();
    }

    @Override
    public BigDecimal getBaseFee() {
        return BASE_FEE;
    }

    public int getPages() {
        return pages;
    }
}