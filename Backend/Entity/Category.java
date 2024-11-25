package com.Ecommercemgmt.Entity; 
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


	
	@Entity
	@Table(name="categories")
	public class Category {

		
		@Id
		@Column(name = "category_id")
		private int categoryId;

		@Column(name = "categoryName")
		private String categoryName;

		@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
		private List<Product> products;

		public Category() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Category(int category_Id, String category_Name, List<Product> products) {
			super();
			this.categoryId = category_Id;
			this.categoryName = category_Name;
			this.products = products;
		}

		public int getCategory_Id() {
			return categoryId;
		}

		public void setCategory_Id(int category_Id) {
			this.categoryId = category_Id;
		}

		public String getCategory_Name() {
			return categoryName;
		}

		public void setCategory_Name(String category_Name) {
			this.categoryName = category_Name;
		}

		public List<Product> getProducts() {
			return products;
		}

		public void setProducts(List<Product> products) {
			this.products = products;
		}

		@Override
		public String toString() {
		    return "Category [category_id=" + categoryId + ", category_name=" + categoryName + "]";
		}


		
		

}
