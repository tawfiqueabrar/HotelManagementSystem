package HotelManagementSystem.Javacore.service;

import HotelManagementSystem.Javacore.model.Guest;
import HotelManagementSystem.Javacore.model.Room;

import java.util.List;

public interface HotelService {
    boolean login(String u, String p);

    // Rooms
    List<String> availableRooms();
    List<Room> rooms();
    int roomPrice(String roomNo);
    int updateRoom(String roomNo, String status);

    // Guests
    void checkIn(Guest g);
    void checkOutByMobile(String mobile);
    List<String> guestNids();
    List<Guest> allGuests();

    // Backup
    void backupGuestsCsv(String path);
}