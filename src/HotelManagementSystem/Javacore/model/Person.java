package HotelManagementSystem.Javacore.model;

public abstract class Person {
    private final String name;
    private final String address;

    protected Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}