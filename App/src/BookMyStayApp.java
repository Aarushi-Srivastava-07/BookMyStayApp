import java.util.*;
/// **
//
//LASS
//
//        RoomInventory
//
//*
//
//        * Use Case 3: Centralized Room Inventory Management
//
//* Description:
//        * This class acts as the single source of truth
//* for room availability in the hotel.
//*
//
//        * Room pricing and characteristics are obtained
//* from Room objects, not duplicated here.
//
//* This avoids multiple sources of truth and
//* keeps responsibilities clearly separated.
//
//        * @version 3.1
//        */

class RoomInventory {

    /// **
//        * Stores available room count for each room type.
//*
//        * Key
//* Value -> Available room count
//*/
    private Map<String, Integer> roomAvailability;

    /// **
//        * Constructor initializes the inventory
//* with default availability values.
//            */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /// **
//        * Initializes room availability data.
//
//            * This method centralizes inventory setup
//* instead of using scattered variables.
    private void initializeInventory() {
        roomAvailability.put("Single", 10);
        roomAvailability.put("Double", 5);
        roomAvailability.put("Suite", 2);
    }

    /// **
//        * Returns the current availability map.
//
//* @return map of room type to available count
//*/
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    /// **
//        * Updates availability for a specific room type.
//
//            * @param roomType the room type to update
//* @param count new availability count
//*/
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

public class BookMyStayApp {
/// **
//
//        * MAIN CLASS - UseCase3InventorySetup
//
//* Use Case 3: Centralized Room Inventory Management
//
//* Description:
//            * This class demonstrates how room availability
//* is managed using a centralized inventory.
//            *
//
//            * Room objects are used to retrieve pricing
//* and room characteristics.
//*
//
//        * No booking or search logic is introduced here.
//
//    @version 3.1
//            *
//
//            */
/// **
//        * Application entry point.
//
//* @param args Command-line arguments
//*/
    private static void printRoomDetails(String type, int beds, int size, double price, int available) {
        System.out.println(type + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available Rooms: " + available + "\n");
    }
    public static void main(String[] args) {
        System.out.println("Hotel Room Inventory Status");

        RoomInventory inventory = new RoomInventory();

        inventory.updateAvailability("Single", 5);
        inventory.updateAvailability("Double", 3);
        inventory.updateAvailability("Suite", 2);

        printRoomDetails("Single", 1, 250, 1500.0, inventory.getRoomAvailability().get("Single"));
        printRoomDetails("Double", 2, 400, 2500.0, inventory.getRoomAvailability().get("Double"));
        printRoomDetails("Suite", 3, 750, 5000.0, inventory.getRoomAvailability().get("Suite"));
    }
}
