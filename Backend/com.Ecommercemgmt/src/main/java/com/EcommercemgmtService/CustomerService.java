package com.EcommercemgmtService;

import org.hibernate.SessionFactory;

public interface CustomerService {

void insertCustomer(SessionFactory sf);
	
	void updateCustomer(SessionFactory sf);
	
	void deleteCustomer(SessionFactory sf);
	

	void getCustomer(SessionFactory sf);
	
	//void getCustomerInformation(SessionFactory sf);
	//void getAllCustomer(SessionFactory sf);

}
