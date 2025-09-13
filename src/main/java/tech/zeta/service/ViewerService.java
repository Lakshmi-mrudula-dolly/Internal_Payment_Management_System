package tech.zeta.service;

import tech.zeta.dao.PaymentDAO;
import tech.zeta.model.Payment;
import java.util.List;

public class ViewerService {
        private final PaymentDAO paymentDAO;

        public ViewerService(PaymentDAO paymentDAO) {
            this.paymentDAO = paymentDAO;
        }

        public List<Payment> viewMonthlyReport(int month, int year) {
            return paymentDAO.generateMonthlyReport(month, year);
        }

        public List<Payment> viewQuarterlyReport(int quarter, int year) {
            return paymentDAO.generateQuarterlyReport(quarter, year);
        }
}

