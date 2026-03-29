import java.util.*;

/**
 * CLASS - RoomInventory
 * Manages available room counts per room type.
 */
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void setInitialCount(String roomType, int count) {
        availability.put(roomType, count);
    }

    public void addRooms(String roomType, int count) {
        availability.put(roomType, availability.getOrDefault(roomType, 0) + count);
    }

    public int getAvailableCount(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

/**
 * CLASS - CancellationService
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Description:
 * This class is responsible for handling booking cancellations.
 * It ensures that:
 * - Cancelled room IDs are tracked
 * - Inventory is restored correctly
 * - Invalid cancellations are prevented
 * A stack is used to model rollback behavior.
 *
 * @version 10.0
 */
class CancellationService {

    /** Stack that stores recently released room IDs. */
    private Stack<String> releasedRoomIds;

    /** Maps reservation ID to room type. */
    private Map<String, String> reservationRoomTypeMap;

    /** Initializes cancellation tracking structures. */
    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    /**
     * Registers a confirmed booking.
     * This method simulates storing confirmation data
     * that will later be required for cancellation.
     *
     * @param reservationId confirmed reservation ID
     * @param roomType      allocated room type
     */
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    /**
     * Cancels a confirmed booking and restores inventory safely.
     *
     * @param reservationId reservation to cancel
     * @param inventory     centralized room inventory
     */
    public void cancelBooking(String reservationId, RoomInventory inventory) {
        // Validate reservation existence
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation " + reservationId + " does not exist or is already cancelled.");
            return;
        }

        // Retrieve room type and restore inventory
        String roomType = reservationRoomTypeMap.get(reservationId);
        inventory.addRooms(roomType, 1);      // Increment inventory count

        // Record released room ID in rollback stack
        releasedRoomIds.push(reservationId);

        // Remove from active reservations to prevent double cancellation
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    /**
     * Displays recently cancelled reservations.
     * This method helps visualize rollback order (most recent first).
     */
    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No cancellations recorded yet.");
            return;
        }

        // Display stack content from top (most recent) to bottom
        List<String> list = new ArrayList<>(releasedRoomIds);
        Collections.reverse(list);
        for (String id : list) {
            System.out.println("Released Reservation ID: " + id);
        }
    }
}

/**
 * MAIN CLASS - UseCase10BookingCancellation
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Description:
 * This class demonstrates how confirmed bookings can be cancelled safely.
 * Inventory is restored and rollback history is maintained.
 *
 * @version 10.0
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     *
     * @param args Command-Line arguments
     */
    public static void main(String[] args) {
        System.out.println("Booking Cancellation\n");

        // Setup inventory: initially 5 Single rooms available
        RoomInventory inventory = new RoomInventory();
        inventory.setInitialCount("Single", 5);

        // Create cancellation service
        CancellationService service = new CancellationService();

        // Simulate a confirmed booking
        String reservationId = "Single-1";
        String roomType = "Single";
        service.registerBooking(reservationId, roomType);

        // Cancel the booking
        service.cancelBooking(reservationId, inventory);

        // Show rollback history (LIFO order)
        service.showRollbackHistory();

        // Display updated inventory
        System.out.println("\nUpdated Single Room Availability: " + inventory.getAvailableCount("Single"));
    }
}