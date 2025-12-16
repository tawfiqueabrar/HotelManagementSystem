package HotelManagementSystem.Javacore.service;

import HotelManagementSystem.Javacore.model.Guest;
import HotelManagementSystem.Javacore.model.Room;
import HotelManagementSystem.Repository.HotelRepository;

import java.util.List;

public class HotelServiceImpliment implements HotelService {

    private final HotelRepository repo;

    public HotelServiceImpliment(HotelRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean login(String u, String p) {
        return repo.login(u, p);
    }

    @Override
    public List<String> availableRooms() {
        return repo.availableRooms();
    }

    @Override
    public List<Room> rooms() {
        return repo.rooms();
    }

    @Override
    public int roomPrice(String roomNo) {
        return repo.roomPrice(roomNo);
    }

    @Override
    public int updateRoom(String roomNo, String status) {
        return repo.updateRoom(roomNo, status);
    }

    @Override
    public void checkIn(Guest g) {

        if (g.getName() == null || g.getName().isBlank())
            throw new IllegalArgumentException("Name required");

        if (g.getNid() == null || !g.getNid().matches("\\d{17}"))
            throw new IllegalArgumentException("NID number must be exactly 17 digits");

        if (g.getMobile() == null || !g.getMobile().matches("\\d{11}"))
            throw new IllegalArgumentException("Mobile number must be exactly 11 digits");

        if (g.getAddress() == null || g.getAddress().isBlank())
            throw new IllegalArgumentException("Address required");

        if (g.getRoom() == null || g.getRoom().isBlank())
            throw new IllegalArgumentException("Select a room");

        int price = repo.roomPrice(g.getRoom());
        if (price == -1) throw new IllegalArgumentException("Room not found");

        if (g.getPaid() != price)
            throw new IllegalArgumentException("Full payment required. Pay exactly: " + price);

        repo.addGuest(g);
    }

    @Override
    public void checkOutByMobile(String mobile) {

        if (mobile == null || !mobile.matches("\\d{11}"))
            throw new IllegalArgumentException("Mobile number must be exactly 11 digits");

        if (!repo.guestExistsByMobile(mobile))
            throw new IllegalArgumentException("No guest found with this mobile number");

        repo.checkoutByMobile(mobile);
    }

    @Override
    public List<String> guestNids() {

        return repo.guestNids();
    }

    @Override
    public List<Guest> allGuests() {

        return repo.allGuests();
    }

    @Override
    public void backupGuestsCsv(String path) {

        repo.backupGuestsCsv(path);
    }
}