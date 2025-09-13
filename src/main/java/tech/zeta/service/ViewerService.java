package tech.zeta.service;

import tech.zeta.dao.PaymentDAO;
import tech.zeta.dto.ReportDTO;
import tech.zeta.model.Payment;
import java.util.List;

public class ViewerService {
        private final PaymentDAO paymentDAO;

        public ViewerService(PaymentDAO paymentDAO) {
            this.paymentDAO = paymentDAO;
        }

        public List<ReportDTO> viewMonthlyReport(int month, int year) {
            return paymentDAO.generateMonthlyReport(month, year);
        }

        public List<ReportDTO> viewQuarterlyReport(int quarter, int year) {
            return paymentDAO.generateQuarterlyReport(quarter, year);
        }
}

