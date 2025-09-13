package tech.zeta.dao;

import tech.zeta.dto.ReportDTO;
import tech.zeta.model.Payment;
import java.util.List;

public interface PaymentDAO {
    void addPayment(Payment payment);
    void updatePaymentStatus(long paymentId,String status);
    List<ReportDTO> generateMonthlyReport(int month, int year);
    List<ReportDTO> generateQuarterlyReport(int quarter, int year);

}
