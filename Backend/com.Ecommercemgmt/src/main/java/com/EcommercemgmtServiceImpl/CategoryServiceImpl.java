package com.EcommercemgmtServiceImpl;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Ecommercemgmt.Entity.Category;
import com.EcommercemgmtService.CategoryService;

import jakarta.persistence.Query;

public class CategoryServiceImpl implements CategoryService {

	Scanner sc = new Scanner(System.in);
	Session session;

	// Select * from Categroy
	public void getAllCategory(SessionFactory sf) {
		session = sf.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery("from Category");
		List<Category> resultList = query.getResultList();

		for (Category c : resultList)
			System.out.println(c);
		tx.commit();

		session.close();

	}

	// Select a specific category "record"
	public void getCategory(SessionFactory sf) {

		session = sf.openSession();
		System.out.println("Enter Category Id:");
		int id = sc.nextInt();
		Category c = session.get(Category.class, id);
		System.out.println(c);
		session.close();
	}

	// Overall "count" of category records
	public void getCategoryInformation(SessionFactory sf) {

		session = sf.openSession();

		Query query = session.createQuery("select count(categoryId) from Category");

		List<Integer> list = query.getResultList();

		System.out.println("Total number of categories:" + list.get(0));
		session.close();

	}
}


//public void insertCategory(SessionFactory sf) {
//session = sf.openSession();
//Transaction tx = session.beginTransaction();
//
//Category category = new Category();
//
//System.out.println("Welcome to Category");
//
//System.out.println("Enter category Id: ");
//int id = sc.nextInt();
//category.setCategory_Id(id);
//
//System.out.println("Enter Category name:");
//String category_name = sc.next();
//category.setCategory_Name(category_name);
//
//session.persist(category);
//tx.commit();
//session.close();
//}
//
//public void updateCategory(SessionFactory sf) {
//session = sf.openSession();
//Transaction tx = session.beginTransaction();
//
//Category category;
//try {
//	while (true) {
//		System.out.println("Choose an Option for Update " + "\n1.Update Category_Name\n2.Exit");
//
//		int option = sc.nextInt();
//		switch (option) {
//		case 1:
//			System.out.println("Enter Category Id:");
//			category = session.get(Category.class, sc.nextInt());
//
//			if (category != null) {
//				System.out.println("Update category name:");
//				category.setCategory_Name(sc.next());
//
//				session.saveOrUpdate(category);
//				tx.commit();
//				System.out.println("Category name updated successfully");
//			} else {
//				System.out.println("Category not found for the given Id");
//			}
//			break;
//
//		case 2:
//			System.out.println("Exit");
//			System.exit(0);
//			break;
//
//		default:
//			System.out.println("Choose correct option!!");
//		}
//	}
//} finally {
//	session.close();
//}
//
//}
//
//public void deleteCategory(SessionFactory sf) {
//
//session = sf.openSession();
//Transaction tx = session.beginTransaction();
//
//System.out.println("Enter Category Id:");
//int id = sc.nextInt();
//
//Category category = session.get(Category.class, id);
//try {
//	if (category != null) {
//		session.delete(category);
//		tx.commit();
//
//	} else {
//		System.out.println("Please Enter valid Category Id");
//
//	}
//} finally {
//	session.close();
//
//}
//
//}
//
