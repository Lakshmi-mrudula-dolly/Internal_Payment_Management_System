package tech.zeta.util;

import tech.zeta.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AuditLogUtil {
    private static final String FILE_NAME = "src/main/java/tech/zeta/logs/audit.log";

    static {
        try {
            Path logDirectory = Paths.get("logs");
            if (!Files.exists(logDirectory)) {
                Files.createDirectories(logDirectory); // Ensure "logs" folder exists
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs an action performed by a user.
     *
     * @param userId user performing the action
     * @param action description of the action
     */
    public static void logAction(long userId, String action) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(LocalDateTime.now() + " | UserID: " + userId + " | Action: " + action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readLogs(User user) {
        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            System.out.println("Access denied! Only Admin can view audit logs.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            if (lines.isEmpty()) {
                System.out.println("No audit logs available.");
            } else {
                System.out.println("===== Audit Log =====");
                lines.forEach(System.out::println);
                System.out.println("=====================");
            }
        } catch (IOException e) {
            System.out.println("Error reading audit log file.");
            e.printStackTrace();
        }
    }
}
