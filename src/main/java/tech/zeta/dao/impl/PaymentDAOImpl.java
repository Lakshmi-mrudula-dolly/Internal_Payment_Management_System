package tech.zeta.dao.impl;

import tech.zeta.dao.PaymentDAO;
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

    @Override
    public List<Payment> generateMonthlyReport(int month, int year) {
        String sql = "select * from payments where extract(month from payment_date) = ? and extract(year from payment_date) = ?";
        List<Payment> payments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, month);
            preparedStatement.setInt(2, year);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                payments.add(mapResultSetToPayment(resultSet));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return payments;
    }

    @Override
    public List<Payment> generateQuarterlyReport(int quarter, int year) {
        int startMonth = (quarter - 1) * 4 + 1;
        int endMonth = startMonth + 3;
        String sql = "select * from payments where extract(month from payment_date) between ? and ? and extract(year from payment_date) = ?";
        List<Payment> payments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, startMonth);
            preparedStatement.setInt(2, endMonth);
            preparedStatement.setInt(3, year);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                payments.add(mapResultSetToPayment(resultSet));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return payments;
    }
    private Payment mapResultSetToPayment(ResultSet rs) throws SQLException {
        return new Payment(
                rs.getLong("payment_id"),
                rs.getDouble("amount"),
                rs.getString("payment_type"),
                rs.getString("status"),
                rs.getDate("payment_date").toLocalDate(),
                rs.getInt("category_id"),
                rs.getLong("user_id")
        );
    }
}
