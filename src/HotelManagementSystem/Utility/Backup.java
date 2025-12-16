package HotelManagementSystem.Utility;

import HotelManagementSystem.Javacore.model.Guest;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Backup {

    public static void backupGuestsCSV(String filePath, List<Guest> guests) {
        try {
            File f = new File(filePath);
            File parent = f.getParentFile();
            if (parent != null) parent.mkdirs();

            try (FileWriter fw = new FileWriter(f)) {
                // ✅ new header
                fw.write("name,nid,mobile,address,room,paid\n");

                for (Guest g : guests) {
                    fw.write(csv(g.getName()) + "," +
                            csv(g.getNid()) + "," +
                            csv(g.getMobile()) + "," +
                            csv(g.getAddress()) + "," +
                            csv(g.getRoom()) + "," +
                            g.getPaid() + "\n");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Backup error: " + e.getMessage(), e);
        }
    }

    private static String csv(String s) {
        if (s == null) return "";
        String t = s.replace("\"", "\"\"");
        if (t.contains(",") || t.contains("\"") || t.contains("\n")) return "\"" + t + "\"";
        return t;
    }
}