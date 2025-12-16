package HotelManagementSystem.Repository;

import HotelManagementSystem.Database.DatabaseConnection;
import HotelManagementSystem.Javacore.model.Guest;
import HotelManagementSystem.Javacore.model.Room;

import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelRepository {

    public boolean login(String u, String p) {
        String sql = "SELECT username FROM login WHERE username=? AND password=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u);
            ps.setString(2, p);
            return ps.executeQuery().next();
        } catch (Exception e) {
            throw new RuntimeException("Login error: " + e.getMessage(), e);
        }
    }

    public List<String> availableRooms() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT roomnumber FROM room WHERE availability='Available' ORDER BY roomnumber";
        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getString(1));
        } catch (Exception e) {
            throw new RuntimeException("Available room error: " + e.getMessage(), e);
        }
        return list;
    }

    public int roomPrice(String roomNo) {
        String sql = "SELECT price FROM room WHERE roomnumber=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, roomNo);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;
        } catch (Exception e) {
            throw new RuntimeException("Room price error: " + e.getMessage(), e);
        }
    }

    public List<Room> rooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT roomnumber, roomtype, availability, price FROM room ORDER BY roomnumber";
        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Room(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Rooms load error: " + e.getMessage(), e);
        }
        return list;
    }

    public int updateRoom(String roomNo, String status) {
        String sql = "UPDATE room SET availability=? WHERE roomnumber=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, roomNo);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Update room error: " + e.getMessage(), e);
        }
    }

    public void addGuest(Guest g) {
        String insert = "INSERT INTO guest(name,nid,mobile,address,room,paid) VALUES(?,?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection()) {

            try (PreparedStatement ps = c.prepareStatement(insert)) {
                ps.setString(1, g.getName());
                ps.setString(2, g.getNid());
                ps.setString(3, g.getMobile());
                ps.setString(4, g.getAddress());
                ps.setString(5, g.getRoom());
                ps.setInt(6, g.getPaid());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(
                    "UPDATE room SET availability='Occupied' WHERE roomnumber=?")) {
                ps.setString(1, g.getRoom());
                ps.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException("Add guest error: " + e.getMessage(), e);
        }
    }
    public List<String> guestNames() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT name FROM guest ORDER BY checkin_time DESC";
        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getString(1));
        } catch (Exception e) {
            throw new RuntimeException("Guest names load error: " + e.getMessage(), e);
        }
        return list;
    }

    public List<String> guestNids() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT nid FROM guest ORDER BY checkin_time DESC";
        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getString(1));
        } catch (Exception e) {
            throw new RuntimeException("Guest list error: " + e.getMessage(), e);
        }
        return list;
    }

    public boolean guestExistsByMobile(String mobile) {
        String sql = "SELECT 1 FROM guest WHERE mobile=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, mobile);
            return ps.executeQuery().next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void checkoutByMobile(String mobile) {
        try (Connection c = DatabaseConnection.getConnection()) {

            String room = null;

            // get room
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT room FROM guest WHERE mobile=?")) {
                ps.setString(1, mobile);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) room = rs.getString(1);
            }

            // delete guest
            try (PreparedStatement ps = c.prepareStatement(
                    "DELETE FROM guest WHERE mobile=?")) {
                ps.setString(1, mobile);
                ps.executeUpdate();
            }

            // free room
            if (room != null) {
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE room SET availability='Available' WHERE roomnumber=?")) {
                    ps.setString(1, room);
                    ps.executeUpdate();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Checkout failed: " + e.getMessage(), e);
        }
    }

    public List<Guest> allGuests() {
        List<Guest> list = new ArrayList<>();
        String sql = "SELECT name,nid,mobile,address,room,paid FROM guest ORDER BY checkin_time DESC";
        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Guest(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6)
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("All guests error: " + e.getMessage(), e);
        }
        return list;
    }

    public void backupGuestsCsv(String path) {
        try {
            File f = new File(path);
            File parent = f.getParentFile();
            if (parent != null) parent.mkdirs();

            try (FileWriter fw = new FileWriter(f)) {
                fw.write("name,nid,mobile,address,room,paid\n");
                for (Guest g : allGuests()) {
                    fw.write(g.getName() + "," + g.getNid() + "," + g.getMobile() + ","
                            + g.getAddress() + "," + g.getRoom() + "," + g.getPaid() + "\n");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Backup error: " + e.getMessage(), e);
        }
    }
}