package parcel.strategy;

public enum DeliveryService {
    STANDARD(new StandardDelivery()),
    EXPRESS(new ExpressDelivery()),
    SAME_DAY(new SameDayDelivery());

    private final DeliveryStrategy strategy;

    DeliveryService(DeliveryStrategy strategy) {
        this.strategy = strategy;
    }

    public DeliveryStrategy getStrategy() {
        return strategy;
    }

    
    public static DeliveryService fromChoice(int choice) {
        DeliveryService[] all = values();
        if (choice < 1 || choice > all.length) {
            throw new IllegalArgumentException("Invalid delivery service choice.");
        }
        return all[choice - 1];
    }
}
