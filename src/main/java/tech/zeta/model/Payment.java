package tech.zeta.model;

import java.time.LocalDate;

public class Payment {
    private long paymentId;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
    private LocalDate date;
    private int categoryId;
    private long userId;

    public Payment() {
    }

    public Payment(long paymentId, double amount, PaymentType type, PaymentStatus status, LocalDate date, int categoryId, long userId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.date = date;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
