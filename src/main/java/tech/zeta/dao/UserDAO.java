package tech.zeta.dao;

import tech.zeta.model.User;

import java.util.List;

public interface UserDAO {
    void createUser(User user);
    List<User> getAllUsers();
    User getUserByEmail(String email);
    void deleteUserById(long userId);
    void updateUserRoleById(long userId,String newRole);
}
