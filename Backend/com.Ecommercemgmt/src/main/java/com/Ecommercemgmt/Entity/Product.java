package com.Ecommercemgmt.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


	

	@Entity
	@Table(name="Products")
	public class Product {

		@Id
		@Column(name="prod_id")
		private int product_Id;
		
		@Column(name="prod_Name")
		private String product_Name;
		
		@Column(name="price")
		private int price;
		
		@Column(name="QuantityInStock")
		private int quantity_In_Stock;
		
		
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="category_id")
		private Category category;



		public Product() {
			super();
			// TODO Auto-generated constructor stub
		}



		public int getProduct_Id() {
			return product_Id;
		}



		public void setProduct_Id(int product_Id) {
			this.product_Id = product_Id;
		}



		public String getProduct_Name() {
			return product_Name;
		}



		public void setProduct_Name(String product_Name) {
			this.product_Name = product_Name;
		}



		public int getPrice() {
			return price;
		}



		public void setPrice(int price) {
			this.price = price;
		}



		public int getQuantity_In_Stock() {
			return quantity_In_Stock;
		}



		public void setQuantity_In_Stock(int quantity_In_Stock) {
			this.quantity_In_Stock = quantity_In_Stock;
		}



		public Category getCategory() {
			return category;
		}



		public void setCategory(Category category) {
			this.category = category;
		}



		@Override
		public String toString() {
			return "Product [product_Id=" + product_Id + ", product_Name=" + product_Name + ", price=" + price
					+ ", quantity_In_Stock=" + quantity_In_Stock + ", category=" + category + "]";
		}



		
		
		
		


}
