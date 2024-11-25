package com.Ecommercemgmt.Entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @Column(name = "order_Id")
    private int orderId;

    @Column(name = "order_Date", nullable = false)
    private Date orderDate;  // Automatically set order date

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false) // Foreign key to Customer
    private Customer customer;  // Association with Customer

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cart> cartItems;  // Assuming you have a list of Cart items associated with the Order

    @OneToOne(mappedBy = "order_Id", fetch = FetchType.LAZY)  // Corrected to match the field name in Payment
    private Payment payment;

    // Constructor
    public Order() {
        this.orderDate = new Date(System.currentTimeMillis());  // Automatically set to current system date
    }

    // Add the method to calculate the total price
    public float getTotalPrice() {
        float total = 0;
        for (Cart cartItem : cartItems) {
            total += cartItem.getPrice();  // Assuming getPrice() in Cart calculates (quantity * product price)
        }
        return total;
    }

    // Getters and setters for fields
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Cart> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Cart> cartItems) {
        this.cartItems = cartItems;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", customer=" + customer + ", cartItems=" + cartItems + ", payment=" + payment + "]";
    }
}
