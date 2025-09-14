package tech.zeta.dao.impl;

import tech.zeta.dao.PaymentDAO;
import tech.zeta.dto.ReportDTO;
import tech.zeta.model.Payment;
import tech.zeta.util.DBUtil;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class PaymentDAOImpl implements PaymentDAO {
    private static Connection connection;

    public PaymentDAOImpl() {
        connection= DBUtil.getConnection();
    }
    @Override
    public void addPayment(Payment payment) {
        String sql = "INSERT INTO payments(amount, payment_type, status, payment_date, category_id, user_id) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, payment.getAmount());
            preparedStatement.setString(2, payment.getType());
            preparedStatement.setString(3, payment.getStatus());
            preparedStatement.setDate(4, Date.valueOf(payment.getDate()));
            preparedStatement.setInt(5, payment.getCategoryId());
            preparedStatement.setLong(6, payment.getUserId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                payment.setPaymentId(generatedKeys.getLong(1));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void updatePaymentStatus(long paymentId, String status) {
        String sql = "update payments set status = ? where payment_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, paymentId);

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public List<ReportDTO> generateMonthlyReport(int month, int year) {
        String sql = "SELECT c.category_name, SUM(p.amount) AS total_amount " +
                "FROM payments p " +
                "JOIN categories c ON p.category_id = c.category_id " +
                "WHERE EXTRACT(MONTH FROM p.payment_date) = ? " +
                "AND EXTRACT(YEAR FROM p.payment_date) = ? " +
                "AND status='Completed'"+
                "GROUP BY c.category_name";

        List<ReportDTO> reports = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reports.add(new ReportDTO(rs.getString("category_name"), rs.getDouble("total_amount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    @Override
    public List<ReportDTO> generateQuarterlyReport(int quarter, int year) {
        int startMonth = (quarter - 1) * 3 + 1;
        int endMonth = startMonth + 2;

        String sql = "SELECT c.category_name, SUM(p.amount) AS total_amount " +
                "FROM payments p " +
                "JOIN categories c ON p.category_id = c.category_id " +
                "WHERE EXTRACT(MONTH FROM p.payment_date) BETWEEN ? AND ? " +
                "AND EXTRACT(YEAR FROM p.payment_date) = ? " +
                "AND status = 'Completed'"+
                "GROUP BY c.category_name";

        List<ReportDTO> reports = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, startMonth);
            ps.setInt(2, endMonth);
            ps.setInt(3, year);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reports.add(new ReportDTO(
                        rs.getString("category_name"),
                        rs.getDouble("total_amount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }
}
