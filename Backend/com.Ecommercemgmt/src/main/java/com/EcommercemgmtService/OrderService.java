package com.EcommercemgmtService;

import org.hibernate.SessionFactory;

public interface OrderService {

void insertOrder(SessionFactory sf);
	
	void updateOrder(SessionFactory sf);
	
	void deleteOrder(SessionFactory sf);
	
	void getAllOrder(SessionFactory sf);

	void getOrder(SessionFactory sf);
	
	//void getOrderInformation(SessionFactory sf);

	
}
