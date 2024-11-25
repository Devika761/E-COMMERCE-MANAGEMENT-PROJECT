package com.Ecommercemgmt.Entity;

import jakarta.persistence.*;

@Entity
public class Cart {

    @Id
    @Column(name = "cart_Id")
    private int cartId;

    @Column(name = "Quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;  // Association with Product

    @ManyToOne
    @JoinColumn(name = "order_id")  // Foreign key to Order
    private Order order;  // Many-to-one relationship with Order

    // Constructor
    public Cart() {
        super();
    }

    // Getters and setters for fields
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Get price from the associated product, no need to store it explicitly.
    public int getPrice() {
        return product != null ? product.getPrice() * quantity : 0;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Cart [cartId=" + cartId + ", quantity=" + quantity + ", price=" + getPrice() + ", product=" + product + "]";
    }
}
