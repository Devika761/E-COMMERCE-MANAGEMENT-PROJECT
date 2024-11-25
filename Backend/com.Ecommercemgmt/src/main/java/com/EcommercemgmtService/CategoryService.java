package com.EcommercemgmtService;



import org.hibernate.SessionFactory;

public interface CategoryService {

//	void insertCategory(SessionFactory sf);
//	
//	void updateCategory(SessionFactory sf);
//	
//	void deleteCategory(SessionFactory sf);
//	
	void getAllCategory(SessionFactory sf);

	void getCategory(SessionFactory sf);
	
	void getCategoryInformation(SessionFactory sf);

}
