package tech.zeta.menu;

import tech.zeta.dao.PaymentDAO;
import tech.zeta.dao.impl.PaymentDAOImpl;
import tech.zeta.model.Payment;
import tech.zeta.service.ViewerService;
import java.util.List;
import java.util.Scanner;

public class ViewerMenu {
    private final ViewerService viewerService;
    private final Scanner scanner;

    public ViewerMenu() {
        PaymentDAO paymentDAO=new PaymentDAOImpl();
        this.viewerService = new ViewerService(paymentDAO);
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        while (true) {
            System.out.println("\n===== Viewer Menu =====");
            System.out.println("1. View Monthly Report");
            System.out.println("2. View Quarterly Report");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    handleMonthlyReport();
                    break;
                case 2:
                    handleQuarterlyReport();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return; // exit menu loop
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void handleMonthlyReport() {
        System.out.print("Enter month (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Enter year (e.g., 2025): ");
        int year = scanner.nextInt();

        List<Payment> report = viewerService.viewMonthlyReport(month, year);
        if (report.isEmpty()) {
            System.out.println("No payments found for " + month + "/" + year);
        } else {
            System.out.println("===== Monthly Report for " + month + "/" + year + " =====");

            report.forEach(payment-> System.out.println("Amount : "+payment.getAmount()+", Type : "
                    +payment.getType()+", Payment Status : "+payment.getStatus()
                    +", Payment Date : "+payment.getDate()));
        }
    }

    private void handleQuarterlyReport() {
        System.out.print("Enter quarter (1-4): ");
        int quarter = scanner.nextInt();
        System.out.print("Enter year (e.g., 2025): ");
        int year = scanner.nextInt();

        List<Payment> report = viewerService.viewQuarterlyReport(quarter, year);
        if (report.isEmpty()) {
            System.out.println("No payments found for Q" + quarter + "/" + year);
        } else {
            System.out.println("===== Quarterly Report for Q" + quarter + "/" + year + " =====");

            report.forEach(payment-> System.out.println("Amount : "+payment.getAmount()+", Type : "
                    +payment.getType()+", Payment Status : "+payment.getStatus()
                    +", Payment Date : "+payment.getDate()));
        }
    }
}

