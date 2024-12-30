
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Abstract base class (Abstraction)
abstract class Vehicle {
    private String vehicleId;
    private String model;
    private double baseRentalRate;
    private boolean isAvailable;

    public Vehicle(String vehicleId, String model, double baseRentalRate) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.baseRentalRate = baseRentalRate;
        this.isAvailable = true;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getModel() {
        return model;
    }

    public double getBaseRentalRate() {
        return baseRentalRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public abstract double calculateRentalCost(int days);

    public abstract boolean isAvailableForRental();

    @Override
    public String toString() {
        return "Vehicle ID: " + vehicleId + ", Model: " + model +
               ", Base Rate: $" + baseRentalRate + ", Available: " + isAvailable;
    }
}

// Car class (Inheritance & Polymorphism)
class Car extends Vehicle {
    public Car(String vehicleId, String model, double baseRentalRate) {
        super(vehicleId, model, baseRentalRate);
    }

    @Override
    public double calculateRentalCost(int days) {
        return getBaseRentalRate() * days * 1.2; // Additional charge for cars
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }
}

// Motorcycle class (Inheritance & Polymorphism)
class Motorcycle extends Vehicle {
    public Motorcycle(String vehicleId, String model, double baseRentalRate) {
        super(vehicleId, model, baseRentalRate);
    }

    @Override
    public double calculateRentalCost(int days) {
        return getBaseRentalRate() * days; // Base rate applies
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }
}

// Truck class (Inheritance & Polymorphism)
class Truck extends Vehicle {
    public Truck(String vehicleId, String model, double baseRentalRate) {
        super(vehicleId, model, baseRentalRate);
    }

    @Override
    public double calculateRentalCost(int days) {
        return getBaseRentalRate() * days * 1.5; // Higher cost for trucks
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }
}

// Customer class
class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer Name: " + name;
    }
}

// RentalAgency class (Composition)
class RentalAgency {
    private List<Vehicle> vehicleFleet;

    public RentalAgency() {
        vehicleFleet = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleFleet.add(vehicle);
    }

    public void displayAvailableVehicles() {
        System.out.println("Available Vehicles:");
        for (Vehicle vehicle : vehicleFleet) {
            if (vehicle.isAvailable()) {
                System.out.println(vehicle);
            }
        }
    }

    public Vehicle getVehicleById(String vehicleId) {
        for (Vehicle vehicle : vehicleFleet) {
            if (vehicle.getVehicleId().equals(vehicleId)) {
                return vehicle;
            }
        }
        return null;
    }

    public void processRental(String vehicleId, Customer customer, int days) {
        Vehicle vehicle = getVehicleById(vehicleId);
        if (vehicle != null && vehicle.isAvailableForRental()) {
            double cost = vehicle.calculateRentalCost(days);
            System.out.println("Rental processed for " + customer.getName() +
                               ". Total cost: $" + cost);
            vehicle.setAvailable(false);
        } else {
            System.out.println("Vehicle is not available for rental.");
        }
    }

    public void returnVehicle(String vehicleId) {
        Vehicle vehicle = getVehicleById(vehicleId);
        if (vehicle != null) {
            vehicle.setAvailable(true);
            System.out.println("Vehicle returned successfully.");
        } else {
            System.out.println("Invalid vehicle ID.");
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        RentalAgency agency = new RentalAgency();

        // Prepopulate fleet
        agency.addVehicle(new Car("C1", "Toyota Corolla", 50.0));
        agency.addVehicle(new Motorcycle("M1", "Yamaha R1", 30.0));
        agency.addVehicle(new Truck("T1", "Volvo FH16", 100.0));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Vehicle Rental System ---");
            System.out.println("1. Display Available Vehicles");
            System.out.println("2. Rent a Vehicle");
            System.out.println("3. Return a Vehicle");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    agency.displayAvailableVehicles();
                    break;
                case 2:
                    System.out.print("Enter vehicle ID: ");
                    String vehicleId = scanner.next();
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.next();
                    System.out.print("Enter rental days: ");
                    int days = scanner.nextInt();
                    agency.processRental(vehicleId, new Customer(customerName), days);
                    break;
                case 3:
                    System.out.print("Enter vehicle ID to return: ");
                    String returnId = scanner.next();
                    agency.returnVehicle(returnId);
                    break;
                case 4:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
