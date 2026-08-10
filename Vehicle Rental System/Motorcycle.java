public class Motorcycle extends Vehicle {

    private int engineDisplacementCc;

    public Motorcycle(String plateNumber, String model, double baseRatePerDay, int engineDisplacementCc) {
        super(plateNumber, model, baseRatePerDay);
        setEngineDisplacementCc(engineDisplacementCc);
    }

    public int getEngineDisplacementCc() {
        return engineDisplacementCc;
    }

    private void setEngineDisplacementCc(int engineDisplacementCc) {
        if (engineDisplacementCc <= 0) {
            throw new IllegalArgumentException("Engine displacement must be greater than 0.");
        }
        this.engineDisplacementCc = engineDisplacementCc;
    }

    @Override
    public double calculateRentalCost(int days) {
        return getBaseRatePerDay() * days;
    }

    @Override
    public String getVehicleType() {
        return "Motorcycle";
    }

    @Override
    public String getAdditionalInfo() {
        return engineDisplacementCc + "cc";
    }
}