import java.util.*;
// / **

// CLASS - Service

// * Use Case 7: Add-On Service Selection

// * Description:
// * This class represents an optional service
// * that can be added to a confirmed reservation.

// * Examples:
// * - Breakfast
// - Spa
// * - Airport Pickup

// * @version 7.0
// */

// CLASS - AddOnService
// Represents an optional service that can be added to a reservation
class AddOnService {
    private String serviceName;
    private double cost;

    // Constructor
    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// CLASS - AddOnServiceManager
// Manages mapping of reservation IDs to selected services
class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    // Attach a service to a reservation
    public void addService(String reservationId, AddOnService service) {
        servicesByReservation
            .computeIfAbsent(reservationId, k -> new ArrayList<>())
            .add(service);
    }

    // Calculate total cost of services for a reservation
    public double calculateTotalServiceCost(String reservationId) {
        List<AddOnService> services = servicesByReservation.get(reservationId);
        if (services == null) return 0.0;

        double total = 0.0;
        for (AddOnService s : services) {
            total += s.getCost();
        }
        return total;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Add-On Service Selection");

        String reservationId = "Single-1";
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        manager.addService(reservationId, new AddOnService("Breakfast", 500.0));
        manager.addService(reservationId, new AddOnService("Spa", 1000.0));

        // Output
        double totalCost = manager.calculateTotalServiceCost(reservationId);
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);

    }
}
