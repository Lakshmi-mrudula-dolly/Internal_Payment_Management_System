package tech.zeta.service;

import tech.zeta.dao.PaymentDAO;
import tech.zeta.model.Payment;
import java.util.List;
import tech.zeta.util.AuditLogUtil;

public class FinanceManagerService {

    private final PaymentDAO paymentDAO;
    private final long financeManagerId; // store logged-in manager id

    public FinanceManagerService(PaymentDAO paymentDAO, long financeManagerId) {
        this.paymentDAO = paymentDAO;
        this.financeManagerId = financeManagerId;
    }

    // Add new payment
    public void addPayment(Payment payment) {
        paymentDAO.addPayment(payment);
        AuditLogUtil.logAction(financeManagerId,
                "Added payment of amount " + payment.getAmount() + " for userId: " + payment.getUserId());
    }

    // Update payment status
    public void updatePaymentStatus(long paymentId, String status) {
        paymentDAO.updatePaymentStatus(paymentId, status);
        AuditLogUtil.logAction(financeManagerId,
                "Updated paymentId " + paymentId + " status to " + status);
    }

    // Generate Monthly Report
    public List<Payment> generateMonthlyReport(int month, int year) {
        List<Payment> report = paymentDAO.generateMonthlyReport(month, year);
        AuditLogUtil.logAction(financeManagerId,
                "Generated monthly report for " + month + "/" + year);
        return report;
    }

    // Generate Quarterly Report
    public List<Payment> generateQuarterlyReport(int quarter, int year) {
        List<Payment> report = paymentDAO.generateQuarterlyReport(quarter, year);
        AuditLogUtil.logAction(financeManagerId,
                "Generated quarterly report for Q" + quarter + "/" + year);
        return report;
    }
}
