package com.Ecommercemgmt.Entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Admin {

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	  
	@OneToOne
	private Customer customer;
	
	@OneToOne
	private Payment  payment;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Order> order;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Inventory> inventory;

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public List<Inventory> getInventory() {
		return inventory;
	}

	public void setInventory(List<Inventory> inventory) {
		this.inventory = inventory;
	}

	@Override
	public String toString() {
		return "Admin [Id=" + Id + ", customer=" + customer + ", payment=" + payment + ", order=" + order
				+ ", inventory=" + inventory + "]";
	}

	
	
}
