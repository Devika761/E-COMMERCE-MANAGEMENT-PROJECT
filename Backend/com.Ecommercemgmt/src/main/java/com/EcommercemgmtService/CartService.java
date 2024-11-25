package com.EcommercemgmtService;

import org.hibernate.SessionFactory;

public interface CartService {

void insertCart(SessionFactory sf);
	
	void updateCart(SessionFactory sf);
	
	void deleteCart(SessionFactory sf);
	
	void getAllCart(SessionFactory sf);

	void getCart(SessionFactory sf);
	
	void getCartInformation(SessionFactory sf);

}
