package tech.zeta.service;

import tech.zeta.dao.PaymentDAO;
import tech.zeta.dto.ReportDTO;
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

    public List<ReportDTO> generateMonthlyReport(int month, int year) {
        List<ReportDTO> report = paymentDAO.generateMonthlyReport(month, year);
        AuditLogUtil.logAction(financeManagerId,
                "Generated monthly financial report for " + month + "/" + year);
        return report;
    }

    public List<ReportDTO> generateQuarterlyReport(int quarter, int year) {
        List<ReportDTO> report = paymentDAO.generateQuarterlyReport(quarter, year);
        AuditLogUtil.logAction(financeManagerId,
                "Generated quarterly financial report for Q" + quarter + "/" + year);
        return report;
    }
}
