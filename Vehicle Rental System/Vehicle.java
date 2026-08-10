public abstract class Vehicle {

    private String plateNumber;
    private String model;
    private double baseRatePerDay;
    private boolean available;

    public Vehicle(String plateNumber, String model, double baseRatePerDay) {
        setPlateNumber(plateNumber);
        setModel(model);
        setBaseRatePerDay(baseRatePerDay);
        this.available = true;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    private void setPlateNumber(String plateNumber) {
        if (plateNumber == null || !plateNumber.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Plate number must be alphanumeric and non-empty.");
        }
        if (plateNumber.length() > 7) {
            throw new IllegalArgumentException("Plate number must be at most 7 characters.");
        }
        this.plateNumber = plateNumber;
    }

    public String getModel() {
        return model;
    }

    private void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model must not be empty.");
        }
        this.model = model;
    }

    public double getBaseRatePerDay() {
        return baseRatePerDay;
    }

    private void setBaseRatePerDay(double baseRatePerDay) {
        if (baseRatePerDay <= 0) {
            throw new IllegalArgumentException("Base rate per day must be greater than 0.");
        }
        this.baseRatePerDay = baseRatePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateRentalCost(int days);

    public abstract String getVehicleType();

    public abstract String getAdditionalInfo();
}