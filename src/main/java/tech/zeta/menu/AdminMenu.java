package tech.zeta.menu;

import tech.zeta.dao.UserDAO;
import tech.zeta.dao.impl.UserDAOImpl;
import tech.zeta.model.User;
import tech.zeta.service.AdminService;
import tech.zeta.util.AuditLogUtil;

import java.util.List;

import java.util.Scanner;

public class AdminMenu {

    Scanner scanner=new Scanner(System.in);
    UserDAO userDAO=new UserDAOImpl();
    AdminService adminService=new AdminService(userDAO);

    public void show(User admin) {
        int choice;
        do {
            System.out.println("\n<<<<<< Admin Menu >>>>>");
            System.out.println("1. Add User");
            System.out.println("2. Update User Role");
            System.out.println("3. Delete User");
            System.out.println("4. View AuditLog");
            System.out.println("5. View All Users");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:

                    System.out.print("Enter new user name : ");
                    String name = scanner.nextLine();

                    System.out.print("Enter email of the user : ");
                    String email = scanner.nextLine();

                    System.out.print("Enter role of the new User : ");
                    String role = scanner.nextLine();

                    User newUser = new User(name,email,role);
                    adminService.createUser(newUser,admin.getUserId());
                    break;

                case 2:

                    System.out.println("Enter id of the user ");
                    long userId = scanner.nextLong();
                    scanner.nextLine();

                    System.out.println("Enter updated role : ");
                    String newRole = scanner.nextLine();

                    adminService.updateUserRoleById(userId,newRole, admin.getUserId());
                    break;

                case 3:

                    System.out.print("Enter id of the user you want to delete : ");
                    long id = scanner.nextLong();
                    scanner.nextLine();

                    adminService.deleteUserById(id, admin.getUserId());
                    break;

                case 4:

                    AuditLogUtil.readLogs(admin);
                    break;

                case 5:

                    List<User> allUsers=adminService.getAllUsers(admin.getUserId());
                    for(User user : allUsers){
                        if(user.getIsActive()){
                            System.out.println("Name : "+user.getName()+", Email : "+user.getEmail()
                                        +", Role: "+user.getRole());
                        }
                    }
                    break;

                case 6:

                    System.out.println("Logging out...");
                    choice=0;
                    break;

                default:

                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
}
