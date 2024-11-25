package com.Ecommercemgmt.Entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name="Inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for auto-increment
    @Column(name="inventory_id")
    private int inventoryId;

    @Column(name="quantity_available")
    private int quantity_available;

    @ManyToOne
    @JoinColumn(name="product_id") 
    private Product product;

    @Column(name="restock_date")
    private LocalDate restock_date;

    
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(int quantity_available) {
        this.quantity_available = quantity_available;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getRestock_date() {
        return restock_date;
    }

    public void setRestock_date(LocalDate restock_date) {
        this.restock_date = restock_date;
    }

    @Override
    public String toString() {
        return "Inventory [inventoryId=" + inventoryId + ", quantity_available=" + quantity_available + 
               ", product=" + product + ", restock_date=" + restock_date + "]";
    }
}
