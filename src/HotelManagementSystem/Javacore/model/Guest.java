package HotelManagementSystem.Javacore.model;

public class Guest extends Person {

    private final String nid;
    private final String mobile;
    private final String room;
    private final int paid;

    public Guest(String name, String nid, String mobile, String address, String room, int paid) {
        super(name, address);
        this.nid = nid;
        this.mobile = mobile;
        this.room = room;
        this.paid = paid;
    }

    public String getNid() {

        return nid;
    }

    public String getMobile() {

        return mobile;
    }

    public String getRoom() {

        return room;
    }

    public int getPaid() {

        return paid;
    }
}