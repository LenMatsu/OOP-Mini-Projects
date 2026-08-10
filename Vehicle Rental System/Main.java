import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static RentalSystem rentalSystem = new RentalSystem();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("Menu");
            System.out.println("1 - Add Vehicle");
            System.out.println("2 - View All Vehicles");
            System.out.println("3 - Rent a Vehicle");
            System.out.println("4 - Return a Vehicle");
            System.out.println("5 - Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addVehicle();
                    break;
                case "2":
                    rentalSystem.displayAllVehicles();
                    break;
                case "3":
                    rentVehicle();
                    break;
                case "4":
                    returnVehicle();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-5.");
            }
        }

        scanner.close();
    }

    private static void addVehicle() {
        System.out.println("Select vehicle type:");
        System.out.println("1 - Car");
        System.out.println("2 - Motorcycle");
        System.out.println("3 - Van");
        System.out.print("Enter choice: ");
        String typeChoice = scanner.nextLine().trim();

        if (!typeChoice.equals("1") && !typeChoice.equals("2") && !typeChoice.equals("3")) {
            System.out.println("Invalid vehicle type.");
            return;
        }

        String plateNumber = readPlateNumber();
        if (plateNumber == null) {
            return;
        }

        String model = readNonEmptyString("Enter model: ");
        double baseRate = readPositiveDouble("Enter base rate per day: ");

        try {
            Vehicle vehicle;

            switch (typeChoice) {
                case "1":
                    int seats = readIntInRange("Enter number of seats (1-5): ", 1, 5);
                    vehicle = new Car(plateNumber, model, baseRate, seats);
                    break;
                case "2":
                    int cc = readPositiveInt("Enter engine displacement (cc): ");
                    vehicle = new Motorcycle(plateNumber, model, baseRate, cc);
                    break;
                default:
                    int cargo = readPositiveInt("Enter cargo capacity (kg): ");
                    int vanSeats = readIntInRange("Enter number of seats (1-15): ", 1, 15);
                    vehicle = new Van(plateNumber, model, baseRate, cargo, vanSeats);
                    break;
            }

            rentalSystem.addVehicle(vehicle);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add vehicle: " + e.getMessage());
        }
    }

    private static void rentVehicle() {
        System.out.print("Enter Plate Number: ");
        String plateNumber = scanner.nextLine().trim();
        int days = readPositiveInt("Enter number of rental days: ");
        rentalSystem.rentVehicle(plateNumber, days);
    }

    private static void returnVehicle() {
        System.out.print("Enter Plate Number: ");
        String plateNumber = scanner.nextLine().trim();
        rentalSystem.returnVehicle(plateNumber);
    }

    private static String readPlateNumber() {
        while (true) {
            System.out.print("Enter Plate Number: ");
            String plateNumber = scanner.nextLine().trim();

            if (!plateNumber.matches("[a-zA-Z0-9]+")) {
                System.out.println("Plate number must be alphanumeric and non-empty. Try again.");
                continue;
            }

            if (plateNumber.length() > 7) {
                System.out.println("Plate number must be at most 7 characters. Try again.");
                continue;
            }

            if (rentalSystem.isPlateNumberTaken(plateNumber)) {
                System.out.println("This plate number already exists. Try again.");
                continue;
            }

            return plateNumber;
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("This field cannot be empty. Try again.");
                continue;
            }

            return input;
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Value must be greater than 0. Try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number. Try again.");
            }
        }
    }

    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max + ". Try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number. Try again.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Value must be greater than 0. Try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number. Try again.");
            }
        }
    }
}