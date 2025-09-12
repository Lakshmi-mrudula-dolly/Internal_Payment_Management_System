package tech.zeta.dao.impl;

import tech.zeta.dao.UserDAO;
import tech.zeta.model.User;
import tech.zeta.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public void createUser(User user) {
        String sql="Insert into users(name,email,password,role) values(?,?,?,?)";
        try(Connection connection= DBUtil.getConnection();){

        }catch(SQLException e){

        }
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public void deleteUserByEmail(String email) {

    }

    @Override
    public void updateUserRoleByEmail(String email, String newRole) {

    }
}
