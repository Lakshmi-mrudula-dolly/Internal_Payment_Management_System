package tech.zeta;

import tech.zeta.dao.impl.PaymentDAOImpl;
import tech.zeta.dao.impl.UserDAOImpl;
import tech.zeta.model.Payment;
import tech.zeta.model.User;
import tech.zeta.util.DBUtil;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection= DBUtil.getConnection();
        if(connection!=null) System.out.println("Sucessful");
        else System.out.println("Failed");
        UserDAOImpl a=new UserDAOImpl();
        //a.createUser(new User("John","john@gmail.com","finance manager"));
        //a.createUser(new User("Daniel","daniel@gmail.com","viewer"));
       // a.updateUserRoleById(13,"finance manager");
      /*  List<User> users=a.getAllUsers();
        for(User user:users){
            System.out.println(user.getUserId()+" "+user.getName()+" "+user.getRole()+" "+user.getIsActive());
        }*/

        PaymentDAOImpl p=new PaymentDAOImpl();
       // p.addPayment(new Payment(25000,"Outgoing","Completed", LocalDate.parse("2024-04-25"),3,1));
        List<Payment> payments=p.generateQuarterlyReport(1,2024);
        for(Payment payment:payments){
            System.out.println(payment.getAmount()+" "+payment.getDate()+" "+payment.getStatus());
        }

    }
}
