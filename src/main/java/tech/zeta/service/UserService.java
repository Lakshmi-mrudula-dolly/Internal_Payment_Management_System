package tech.zeta.service;

import tech.zeta.dao.UserDAO;
import tech.zeta.dao.impl.UserDAOImpl;
import tech.zeta.exception.InvalidPasswordException;
import tech.zeta.exception.InvalidUserException;
import tech.zeta.model.User;

public class UserService {

    public User login(String email,String password) throws InvalidUserException, InvalidPasswordException{

        UserDAO userDAO=new UserDAOImpl();
        User user=userDAO.getUserByEmail(email);
        if(user==null || !user.getIsActive()) throw new InvalidUserException("User does not exist");
        else if(!user.getPassword().equals(password)) throw new InvalidPasswordException("Invalid Password");
        return user;
    }
}
