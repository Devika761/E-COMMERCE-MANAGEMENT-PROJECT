package com.EcommercemgmtService;

import org.hibernate.SessionFactory;

public interface ProductService {

//void insertProduct(SessionFactory sf);
//	
//	void updateProduct(SessionFactory sf);
//	
//	void deleteProduct(SessionFactory sf);
	
	void getAllProduct(SessionFactory sf);

	void getProduct(SessionFactory sf);
	
	void getProductInformation(SessionFactory sf);

}
