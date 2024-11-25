package com.EcommercemgmtService;

import org.hibernate.SessionFactory;


import com.Ecommercemgmt.utility.InventoryUnavailableException;

public interface AdminService {

	
	// Customer Details
	
	void getAllCustomer(SessionFactory sf);

	void getCustomer(SessionFactory sf);
	
	void getCustomerInformation(SessionFactory sf);
	
//====================================================
	
	//payments
	
	void getAllPayment(SessionFactory sf);

	void getPayment(SessionFactory sf);
	
	void getPaymentInformation(SessionFactory sf);
	
	
//======================================================	
	
	//Orders 
	
	void getAllOrder(SessionFactory sf);

	void getOrder(SessionFactory sf);
	
	void getOrderInformation(SessionFactory sf);

//========================================================
	//Inventory
	
    void insertInventory(SessionFactory sf) ;
	
	void updateInventory(SessionFactory sf) throws InventoryUnavailableException ;
	
	void deleteInventory(SessionFactory sf);
	
	void getAllInventory(SessionFactory sf);

	void getInventory(SessionFactory sf);
	
	void getInventoryInformation(SessionFactory sf);

	
	//==========================================================
	//Category
	
	void insertCategory(SessionFactory sf);
	
	void updateCategory(SessionFactory sf);
	
	void deleteCategory(SessionFactory sf);
	
	void getAllCategory(SessionFactory sf);

	void getCategory(SessionFactory sf);
	
	void getCategoryInformation(SessionFactory sf);
	//==============================================================
	//Product
	
	void insertProduct(SessionFactory sf);
	
	void updateProduct(SessionFactory sf);
	
	void deleteProduct(SessionFactory sf);

	void getAllProduct(SessionFactory sf);

	void getProduct(SessionFactory sf);
	
	void getProductInformation(SessionFactory sf);


	

	
}
