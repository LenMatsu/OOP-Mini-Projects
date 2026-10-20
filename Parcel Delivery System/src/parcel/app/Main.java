package parcel.app;

import parcel.exception.DuplicateTrackingNumberException;
import parcel.exception.InvalidInputException;
import parcel.exception.ParcelAlreadyDeliveredException;
import parcel.exception.ParcelNotFoundException;
import parcel.exception.SameDayNotAllowedException;
import parcel.model.Box;
import parcel.model.Document;
import parcel.model.Fragile;
import parcel.model.Parcel;
import parcel.model.Validator;
import parcel.service.ParcelManager;
import parcel.strategy.DeliveryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner IN = new Scanner(System.in);
    private static final ParcelManager MANAGER = ParcelManager.getInstance();

    @FunctionalInterface
    private interface Reader<T> {
        T read(String input) throws InvalidInputException;
    }

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readMenuChoice();
            System.out.println();
            switch (choice) {
                case 1 -> addParcel();
                case 2 -> viewAllParcels();
                case 3 -> changeDeliveryService();
                case 4 -> markAsDelivered();
                case 5 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please enter 1-5.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("==================================");
        System.out.println("      PARCEL DELIVERY SYSTEM      ");
        System.out.println("==================================");
        System.out.println("\n1. Add Parcel");
        System.out.println("2. View All Parcels");
        System.out.println("3. Change Delivery Service");
        System.out.println("4. Mark as Delivered");
        System.out.println("5. Exit");
        System.out.print("\nChoose an option: ");
    }

    private static int readMenuChoice() {
        if (!IN.hasNextLine()) {
            return 5;
        }
        try {
            return Integer.parseInt(IN.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addParcel() {
        System.out.println("Parcel types: 1. Document  2. Box  3. Fragile");
        int type = prompt("Select parcel type (1-3): ", s -> {
            int v = parseInt(s);
            return Validator.range(v, 1, 3, "Parcel type");
        });
        String tracking = prompt("Tracking number (e.g. ABC0001): ", s -> {
            String t = Validator.trackingNumber(s);
            if (MANAGER.exists(t)) {
                throw new InvalidInputException("Tracking number " + t + " already exists!");
            }
            return t;
        });
        String receiver = prompt("Receiver name: ", s -> Validator.nonEmpty(s, "Receiver name"));

        double maxWeight = switch (type) {
            case 1 -> Document.MAX_WEIGHT;
            case 2 -> Box.MAX_WEIGHT;
            default -> Fragile.MAX_WEIGHT;
        };
        double weight = prompt("Weight in kg (max " + maxWeight + "): ",
                s -> Validator.weight(parseDouble(s), maxWeight));

        Parcel parcel;
        try {
            if (type == 1) {
                int pages = prompt("Number of pages (1-100): ", s -> Validator.range(parseInt(s), 1, 100, "Number of pages"));
                parcel = new Document(tracking, receiver, weight, pages);
            } else {
                int l = prompt("Length (cm, max 300): ", s -> Validator.dimension(parseInt(s), "Length"));
                int w = prompt("Width (cm, max 300): ", s -> Validator.dimension(parseInt(s), "Width"));
                int h = prompt("Height (cm, max 300): ", s -> Validator.dimension(parseInt(s), "Height"));
                if (type == 2) {
                    parcel = new Box(tracking, receiver, weight, l, w, h);
                } else {
                    String note = prompt("Handling note: ", s -> Validator.nonEmpty(s, "Handling note"));
                    parcel = new Fragile(tracking, receiver, weight, l, w, h, note);
                }
            }
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        while (true) {
            DeliveryService service = promptService();
            try {
                parcel.setDeliveryStrategy(service.getStrategy());
                break;
            } catch (SameDayNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            MANAGER.addParcel(parcel);
            System.out.printf("Parcel added successfully! Delivery fee: Php %.2f%n", parcel.getDeliveryFee());
        } catch (DuplicateTrackingNumberException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewAllParcels() {
        List<Parcel> parcels = MANAGER.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("No parcels yet.");
            return;
        }
        String row = "%-10s %-9s %-20s %-12s %-10s %-14s %-10s%n";
        System.out.printf(row, "Tracking", "Type", "Receiver", "Chargeable", "Service", "Delivery Fee", "Status");
        System.out.println("-".repeat(92));
        for (Parcel p : parcels) {
            System.out.printf(row,
                    p.getTrackingNumber(),
                    p.getType(),
                    truncate(p.getReceiverName(), 20),
                    String.format("%.2f kg", p.getChargeableWeight()),
                    p.getServiceName(),
                    String.format("Php %.2f", p.getDeliveryFee()),
                    p.getStatus());
        }
    }

    private static void changeDeliveryService() {
        System.out.print("Enter tracking number: ");
        String tracking = IN.nextLine().trim();
        try {
            Parcel parcel = MANAGER.findParcel(tracking);
            if (parcel.isDelivered()) {
                throw new ParcelAlreadyDeliveredException("Parcel already delivered!");
            }
            DeliveryService service = promptService();
            BigDecimal fee = MANAGER.changeDeliveryService(tracking, service);
            System.out.printf("Delivery service changed! New fee: Php %.2f%n", fee);
        } catch (ParcelNotFoundException | ParcelAlreadyDeliveredException | SameDayNotAllowedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void markAsDelivered() {
        System.out.print("Enter tracking number: ");
        String tracking = IN.nextLine().trim();
        try {
            MANAGER.markAsDelivered(tracking);
            System.out.println("Parcel delivered successfully!");
        } catch (ParcelNotFoundException | ParcelAlreadyDeliveredException e) {
            System.out.println(e.getMessage());
        }
    }

    private static DeliveryService promptService() {
        System.out.println("Delivery services: 1. Standard  2. Express  3. Same Day");
        int choice = prompt("Select delivery service (1-3): ", s -> Validator.range(parseInt(s), 1, 3, "Delivery service"));
        return DeliveryService.fromChoice(choice);
    }

    private static <T> T prompt(String message, Reader<T> reader) {
        while (true) {
            System.out.print(message);
            String line = IN.hasNextLine() ? IN.nextLine() : "";
            try {
                return reader.read(line);
            } catch (InvalidInputException e) {
                System.out.println("Invalid input: " + e.getMessage());
                if (!IN.hasNextLine()) {
                    throw new IllegalStateException("Input ended unexpectedly.");
                }
            }
        }
    }

    private static int parseInt(String s) throws InvalidInputException {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please enter a whole number.");
        }
    }

    private static double parseDouble(String s) throws InvalidInputException {
        try {
            return Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please enter a valid number.");
        }
    }

    private static String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 3) + "...";
    }
}