package tech.zeta.service;

import tech.zeta.dao.UserDAO;
import tech.zeta.model.User;
import java.util.List;
import tech.zeta.util.AuditLogUtil;

public class AdminService {
    private final UserDAO userDAO;

    public AdminService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    // Create a new user (Admin/Finance Manager/Viewer)
    public void createUser(User user, long adminId) {
        userDAO.createUser(user);
        AuditLogUtil.logAction(adminId, "Created new user: "
                + user.getName() + " (Email: " + user.getEmail() + ") with role: " + user.getRole());
    }

    // Get all users
    public List<User> getAllUsers(long adminId) {
        List<User> users = userDAO.getAllUsers();
        AuditLogUtil.logAction(adminId, "Viewed all users");
        return users;
    }

    // Delete a user by ID
    public void deleteUserById(long userId, long adminId) {
        userDAO.deleteUserById(userId);
        AuditLogUtil.logAction(adminId, "Deleted user with ID: " + userId);
    }

    // Update a user's role
    public void updateUserRoleById(long userId, String newRole, long adminId) {
        userDAO.updateUserRoleById(userId, newRole);
        AuditLogUtil.logAction(adminId, "Updated role of user ID " + userId + " to: " + newRole);
    }

    // Fetch userId from email
    public long getUserIdByEmail(String email, long adminId) {
        long userId = userDAO.getUserByEmail(email).getUserId();
        AuditLogUtil.logAction(adminId, "Fetched user ID for email: " + email);
        return userId;
    }
}
