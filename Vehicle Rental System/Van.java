public class Van extends Vehicle {

    private static final double DRIVER_FEE = 500.0;

    private int cargoCapacityKg;
    private int numberOfSeats;

    public Van(String plateNumber, String model, double baseRatePerDay, int cargoCapacityKg, int numberOfSeats) {
        super(plateNumber, model, baseRatePerDay);
        setCargoCapacityKg(cargoCapacityKg);
        setNumberOfSeats(numberOfSeats);
    }

    public int getCargoCapacityKg() {
        return cargoCapacityKg;
    }

    private void setCargoCapacityKg(int cargoCapacityKg) {
        if (cargoCapacityKg <= 0) {
            throw new IllegalArgumentException("Cargo capacity must be greater than 0.");
        }
        this.cargoCapacityKg = cargoCapacityKg;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    private void setNumberOfSeats(int numberOfSeats) {
        if (numberOfSeats <= 0 || numberOfSeats > 15) {
            throw new IllegalArgumentException("Number of seats for a van must be between 1 and 15.");
        }
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public double calculateRentalCost(int days) {
        return (getBaseRatePerDay() * days) + DRIVER_FEE;
    }

    @Override
    public String getVehicleType() {
        return "Van";
    }

    @Override
    public String getAdditionalInfo() {
        return cargoCapacityKg + "kg cargo, " + numberOfSeats + " seats";
    }
}