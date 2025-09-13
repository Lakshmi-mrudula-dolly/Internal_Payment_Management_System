package tech.zeta.service;

import tech.zeta.dao.UserDAO;
import tech.zeta.dao.impl.UserDAOImpl;
import tech.zeta.exception.InvalidPasswordException;
import tech.zeta.exception.InvalidUserException;
import tech.zeta.model.User;

public class UserService {

    public static UserDAO userDAO=new UserDAOImpl();

    public User login(String email,String password) throws InvalidUserException, InvalidPasswordException{

        if(doesUserExists(email)==-1) throw new InvalidUserException("User does not exist");
        else {
            User user=userDAO.getUserByEmail(email);
            if (!user.getPassword().equals(password)) throw new InvalidPasswordException("Invalid Password");
            return user;
        }
    }
    public static long doesUserExists(String email){

        User user=userDAO.getUserByEmail(email);
        if(user==null || !user.getIsActive()) return -1;
        else return user.getUserId();
    }
}
