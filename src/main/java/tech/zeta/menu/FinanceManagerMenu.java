package tech.zeta.menu;

import tech.zeta.dao.PaymentDAO;
import tech.zeta.dao.impl.PaymentDAOImpl;
import tech.zeta.dto.ReportDTO;
import tech.zeta.model.Payment;
import tech.zeta.model.User;
import tech.zeta.service.FinanceManagerService;
import tech.zeta.util.AuditLogUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FinanceManagerMenu {
    Scanner scanner = new Scanner(System.in);
    FinanceManagerService financeManagerService;

    public void show(User financeManager){

        PaymentDAO paymentDAO=new PaymentDAOImpl();
        long managerId=financeManager.getUserId();
        financeManagerService=new FinanceManagerService(paymentDAO, financeManager.getUserId());

        int choice;

        do {
            System.out.println("\n====== Finance Manager Menu ======");
            System.out.println("1. Add Payment");
            System.out.println("2. Update Payment Status");
            System.out.println("3. Generate Monthly Report");
            System.out.println("4. Generate Quarterly Report");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addPayment(managerId);
                    break;
                case 2:
                    updatePaymentStatus();
                    break;
                case 3:
                    generateMonthlyReport();
                    break;
                case 4:
                    generateQuarterlyReport();
                    break;
                case 5:
                    System.out.println("Exiting Finance Manager menu...");
                    choice=5;
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);

    }

    private void addPayment(long managerId) {
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        System.out.print("Enter type (Incoming/Outgoing): ");
        String type = scanner.nextLine();

        System.out.print("Enter status (e.g., Pending, Completed): ");
        String status = scanner.nextLine();

        System.out.print("Enter categoryId (1.Salary 2.Vendor Payment 3.ClientInvoice) : ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter payment date(YYYY-MM-DD) : ");
        String paymentDate= scanner.nextLine();

        Payment payment = new Payment(amount, type, status,  LocalDate.parse(paymentDate),managerId,categoryId);
        financeManagerService.addPayment(payment); // pass manager id for audit

        System.out.println("Payment added successfully!");
    }

    private void updatePaymentStatus() {
        System.out.print("Enter paymentId to update: ");
        long paymentId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        System.out.print("Enter new status: ");
        String status = scanner.nextLine();

        financeManagerService.updatePaymentStatus(paymentId, status);

        System.out.println("Payment status updated!");
    }

    private void generateMonthlyReport() {
        System.out.print("Enter month (1-12): ");
        int month = scanner.nextInt();

        System.out.print("Enter year: ");
        int year = scanner.nextInt();

        List<ReportDTO> report = financeManagerService.generateMonthlyReport(month, year);

        System.out.println("\n===== Monthly Financial Report (" + month + "/" + year + ") =====");
        if (report.isEmpty()) {
            System.out.println("No records found.");
        } else {
            report.forEach(r -> System.out.println(r.getCategoryName() + " : " + r.getTotalAmount()));
        }
        System.out.println("==================================");
    }

    private void generateQuarterlyReport() {
        System.out.print("Enter quarter (1-4): ");
        int quarter = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();

        List<ReportDTO> report = financeManagerService.generateQuarterlyReport(quarter, year);

        System.out.println("\n===== Quarterly Financial Report (Q" + quarter + "/" + year + ") =====");
        if (report.isEmpty()) {
            System.out.println("No records found.");
        } else {
            report.forEach(r -> System.out.println(r.getCategoryName() + " : " + r.getTotalAmount()));
        }
        System.out.println("==================================");
    }
}
