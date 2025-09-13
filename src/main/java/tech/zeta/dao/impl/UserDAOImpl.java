package tech.zeta.dao.impl;

import tech.zeta.dao.UserDAO;
import tech.zeta.model.User;
import tech.zeta.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static Connection connection;

    public UserDAOImpl() {
        connection=DBUtil.getConnection();
    }

    @Override
    public void createUser(User user) {
        String email = user.getEmail().trim();
        if (getUserByEmail(email)!=null) {
            makeExistingUserActive(user);
        } else {
            createNewUser(user);
            user.setUserId(getUserByEmail(email).getUserId());
        }
    }

    @Override
    public List<User> getAllUsers() {

        String sql="Select user_id,name,email,role,is_active from users";
        List<User> users=new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet= statement.executeQuery(sql);
            while(resultSet.next()){
                users.add(new User(resultSet.getLong(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getBoolean(5)));
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        return users;
    }

    @Override
    public void deleteUserById(long userId) {
        String sql="Update users set is_active=false where user_id=?";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(sql);

            preparedStatement.setLong(1,userId);
            preparedStatement.executeUpdate();

        }catch (SQLException | NullPointerException exception){
            exception.printStackTrace();
        }

    }

    @Override
    public void updateUserRoleById(long userId, String newRole) {
        String sql="Update users set role=? where user_id=?";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(sql);

            preparedStatement.setString(1,newRole);
            preparedStatement.setLong(2,userId);
            preparedStatement.executeUpdate();

        }catch (SQLException | NullPointerException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql="select user_id,name,email,password,role,is_active from users where email = ?";
        User user=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email.trim());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user=new User(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getBoolean(6));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return user;
    }

    public void makeExistingUserActive(User user) {
        String sql="update users set is_active = true,role=? where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getRole());
            preparedStatement.setString(2, user.getEmail().trim());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void createNewUser(User user) {
        String sql = "insert into users(name, email, password, role, is_active) values (?, ?, ?, ?, true)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail().trim());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.executeUpdate();


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
