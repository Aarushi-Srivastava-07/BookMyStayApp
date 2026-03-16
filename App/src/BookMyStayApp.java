import java.util.*;
// **
//
//        * CLASS - RoomAllocationService
//
//* Use Case 6: Reservation Confirmation & Room Allocation
//
//* Description:
//        * This class is responsible for confirming
//* booking requests and assigning rooms.
//*
//
//        * It ensures:
//        * - Each room ID is unique
//- Inventory is updated immediately
//- No room is double-booked
//
//* @version 6.0
//        */

class RoomAllocationService {

    /// **
//        * Stores all allocated room IDs to
//* prevent duplicate assignments.
//*/
    private Set<String> allocatedRoomIds;

    /// **
//        * Stores assigned room IDs by room type.
//*
//        * Key -> Room type
//* Value -> Set of assigned room IDs
//*/
    private Map<String, Set<String>> assignedRoomsByType;

    /// **
//        * Initializes allocation tracking structures.
//            */
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /// **
//        * Confirms a booking request by assigning
//* a unique room ID and updating inventory.
//
//* @param reservation booking request
//* @param inventory centralized room inventory
//*/
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();

        // Check availability
        if (!inventory.isAvailable(roomType)) {
            System.out.println("No rooms available for Guest: " + reservation.getGuestName() + ", Room Type: " + roomType);
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Record allocation
        allocatedRoomIds.add(roomId);
        assignedRoomsByType.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);

        // Update inventory
        inventory.decrement(roomType);

        // Confirm reservation
        System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + ", Room ID: " + roomId);
    }

    /// **
//        * Generates a unique room ID
//* for the given room type.
//            *
//            * @param roomType type of room
//* @return unique room ID
//*/
    private String generateRoomId(String roomType) {
        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + count;
    }
}

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// RoomInventory class
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

/// **
//
//        * MAIN CLASS - UseCase6RoomAllocation
//
//Use Case 6: Reservation Confirmation & Room Allocation
//
//* Description:
//        * This class demonstrates how booking
//* requests are confirmed and rooms
//* are allocated safely.
//*
//
//        * It consumes booking requests in FIFO
//* order and updates inventory immediately.
//
//* @version 6.0
//        */
public class BookMyStayApp {
//    / **
//            * Application entry point.
//*
//
//        * @param args Command-Line arguments
//*/
    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Suite", 1);

        RoomAllocationService service = new RoomAllocationService();

        // Sample reservations
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Single");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Process reservations
        service.allocateRoom(r1, inventory);
        service.allocateRoom(r2, inventory);
        service.allocateRoom(r3, inventory);
    }
}
