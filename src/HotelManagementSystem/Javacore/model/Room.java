package HotelManagementSystem.Javacore.model;

public class Room {
    private String roomNumber, roomType, availability;
    private int price;

    public Room(String roomNumber, String roomType, String availability, int price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.availability = availability;
        this.price = price;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {

        return roomType;
    }

    public String getAvailability() {

        return availability;
    }

    public int getPrice() {
        return price;
    }
}
