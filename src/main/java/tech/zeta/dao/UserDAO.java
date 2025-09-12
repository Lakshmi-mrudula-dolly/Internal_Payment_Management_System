package tech.zeta.dao;

import tech.zeta.model.User;

public interface UserDAO {
    void createUser(User user);
    User getUserByEmail(String email);
    void deleteUserByEmail(String email);
    void updateUserRoleByEmail(String email,String newRole);
}
