package com.Ecommercemgmt.Entity;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "Payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_Id")
    private int paymentId;

    @Column(name = "payment_Date")
    private Date paymentDate;

    @Column(name = "payment_Mode")
    private String paymentMode;

    @Column(name = "Amount")
    private float amountPaid;

    @OneToOne
    @JoinColumn(name = "order_id")  // Foreign key to Order
    private Order order_Id;  // Association with Order

    public Payment() {
    }

    // Automatically set the amountPaid based on the Order's total amount
    public void setOrder_Id(Order order_Id) {
        this.order_Id = order_Id;

        // Automatically set the amountPaid from the associated Order
        if (order_Id != null) {
            this.amountPaid = order_Id.getTotalPrice(); // Assuming Order has a method getTotalPrice() for the total order price
        }
    }

    // Getter and Setter methods
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Order getOrder_Id() {
        return order_Id;
    }

    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId + ", paymentDate=" + paymentDate + ", paymentMode=" + paymentMode
                + ", amountPaid=" + amountPaid + "]";
    }
}
