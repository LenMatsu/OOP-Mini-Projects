public class Car extends Vehicle {

    private int numberOfSeats;

    public Car(String plateNumber, String model, double baseRatePerDay, int numberOfSeats) {
        super(plateNumber, model, baseRatePerDay);
        setNumberOfSeats(numberOfSeats);
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    private void setNumberOfSeats(int numberOfSeats) {
        if (numberOfSeats <= 0 || numberOfSeats > 5) {
            throw new IllegalArgumentException("Number of seats for a car must be between 1 and 5.");
        }
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public double calculateRentalCost(int days) {
        return getBaseRatePerDay() * days;
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }

    @Override
    public String getAdditionalInfo() {
        return numberOfSeats + " seats";
    }
}