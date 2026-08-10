import java.util.ArrayList;
import java.util.List;

public class RentalSystem {

    private List<Vehicle> vehicles;

    public RentalSystem() {
        vehicles = new ArrayList<>();
    }

    public boolean isPlateNumberTaken(String plateNumber) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                return true;
            }
        }
        return false;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        System.out.println("Vehicle added successfully!");
    }

    public void displayAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles have been added yet.");
            return;
        }

        System.out.println();
        System.out.println("Vehicle List:");

        int count = 1;
        for (Vehicle vehicle : vehicles) {
            String status = vehicle.isAvailable() ? "Available" : "Rented";

            System.out.println("-----------------------------");
            System.out.println(count + ". Plate: " + vehicle.getPlateNumber());
            System.out.println("   Type: " + vehicle.getVehicleType());
            System.out.println("   Model: " + vehicle.getModel());
            System.out.printf("   Rate/Day: Php %.2f%n", vehicle.getBaseRatePerDay());
            System.out.println("   Details: " + vehicle.getAdditionalInfo());
            System.out.println("   Status: " + status);

            count++;
        }

        System.out.println("-----------------------------");
    }

    private Vehicle findVehicle(String plateNumber) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                return vehicle;
            }
        }
        return null;
    }

    public void rentVehicle(String plateNumber, int days) {
        Vehicle vehicle = findVehicle(plateNumber);

        if (vehicle == null) {
            System.out.println("Vehicle not found!");
            return;
        }

        if (!vehicle.isAvailable()) {
            System.out.println("Vehicle not available!");
            return;
        }

        double totalCost = vehicle.calculateRentalCost(days);
        vehicle.setAvailable(false);
        System.out.printf("Total rental cost: Php %.2f%n", totalCost);
    }

    public void returnVehicle(String plateNumber) {
        Vehicle vehicle = findVehicle(plateNumber);

        if (vehicle == null) {
            System.out.println("Vehicle not found!");
            return;
        }

        if (vehicle.isAvailable()) {
            System.out.println("Vehicle was not rented.");
            return;
        }

        vehicle.setAvailable(true);
        System.out.println("Vehicle returned successfully!");
    }
}