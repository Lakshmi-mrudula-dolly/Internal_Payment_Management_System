package tech.zeta.menu;

import tech.zeta.dao.UserDAO;
import tech.zeta.dao.impl.UserDAOImpl;
import tech.zeta.model.User;
import tech.zeta.service.AdminService;
import tech.zeta.util.AuditLogUtil;

import java.util.List;

import java.util.Scanner;

import static tech.zeta.service.UserService.doesUserExists;

public class AdminMenu {

    Scanner scanner=new Scanner(System.in);
    UserDAO userDAO=new UserDAOImpl();
    AdminService adminService=new AdminService(userDAO);

    public void show(User admin) {
        int choice;
        String userEmail;
        long id;
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

                    System.out.print("Enter role of the new User (admin/finance manager/viewer) : ");
                    String role = scanner.nextLine();

                    User newUser = new User(name,email,role);
                    if(isValidUser(newUser)) adminService.createUser(newUser,admin.getUserId());
                    else System.out.println("Invalid details ");
                    break;

                case 2:

                    System.out.print("Enter email of the user you want to update : ");
                    userEmail = scanner.nextLine();
                    if(!isValidEmail(userEmail)) {
                        System.out.println("InValid email");
                        break;
                    }

                    System.out.println("Enter updated role : ");
                    String newRole = scanner.nextLine();
                    if(!isValidRole(newRole)) {
                        System.out.println("InValid role");
                        break;
                    }

                    id=doesUserExists(userEmail);
                    if(id!=-1){ adminService.updateUserRoleById(id,newRole, admin.getUserId()); }
                    else System.out.println("User does not exist");
                    break;

                case 3:

                    System.out.print("Enter email of the user you want to delete : ");
                    userEmail = scanner.nextLine();
                    if(!isValidEmail(userEmail)) {
                        System.out.println("InValid email");
                        break;
                    }

                    id=doesUserExists(userEmail);
                    if(id!=-1){ adminService.deleteUserById(id, admin.getUserId()); }
                    else System.out.println("User does not exist");
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

    public boolean isValidUser(User user){          //User name,email,role must be valid in order to create new user
        if(user.getName().isEmpty()) return false;
        if(!isValidEmail(user.getEmail())) return false;
        if(!isValidRole(user.getRole())) return false; // The user role should be one of the three
        return true;
    }

    public boolean isValidEmail(String email){
        return email.contains("@") && email.contains(".");
    }

    public boolean isValidRole(String role){
        return (role.equals("admin")||role.equals("finance manager")||role.equals("viewer"));
    }
}
