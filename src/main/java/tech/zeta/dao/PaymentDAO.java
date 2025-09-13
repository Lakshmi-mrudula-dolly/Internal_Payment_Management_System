package tech.zeta.dao;

import tech.zeta.model.Payment;
import java.util.List;

public interface PaymentDAO {
    void addPayment(Payment payment);
    void updatePaymentStatus(long paymentId,String status);
    List<Payment> generateMonthlyReport(int month, int year);
    List<Payment> generateQuarterlyReport(int quarter, int year);

}
