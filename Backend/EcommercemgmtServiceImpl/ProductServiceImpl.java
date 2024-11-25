package com.EcommercemgmtServiceImpl;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.Ecommercemgmt.Entity.Product;
import com.EcommercemgmtService.ProductService;

import jakarta.persistence.Query;

public class ProductServiceImpl implements ProductService {

	Scanner sc = new Scanner(System.in);
	Session session;

	
//============ SELECT * FROM PRODUCT ==========================

	public void getAllProduct(SessionFactory sf) {
		try {
			session = sf.openSession();

			Transaction tx = session.beginTransaction();

			Query query = session.createQuery("from Product");
			List<Product> resultList = query.getResultList();

			for (Product prod : resultList)
				System.out.println(prod);
			tx.commit();
		} finally {
			session.close();
		}

	}

//================ SELECTING A SPECIFIC RECORD ============================================
	public void getProduct(SessionFactory sf) {
		session = sf.openSession();
		System.out.println("Enter product Id:");
		int id = sc.nextInt();

		Product prod = session.get(Product.class, id);
		System.out.println(prod);
		session.close();

	}

//======================== COUNT ==========================

	public void getProductInformation(SessionFactory sf) {
		try {
			session = sf.openSession();
			Query query = session.createQuery("select count(product_Id) from Product");

			List<Integer> list = query.getResultList();

			System.out.println("Total number of products:" + list.get(0));
		} finally {
			session.close();
		}

	}

}



//public void insertProduct(SessionFactory sf) {
//
//		session = sf.openSession();
//		Transaction transaction = session.beginTransaction();
//
//		Product product = new Product();
//
//		System.out.println("Welcome to product");
//
//		System.out.println("Enter product Id:");
//		int id = sc.nextInt();
//		sc.nextLine();
//		product.setProduct_Id(id);
//
//		System.out.println("Enter product Name:");
//		String product_Name = sc.nextLine();
//		product.setProduct_Name(product_Name);
//
//		System.out.println("Enter price:");
//		int price = sc.nextInt();
//		product.setPrice(price);
//		;
//
//		System.out.println("Enter Quantity Available:");
//		int Qnt = sc.nextInt();
//		product.setQuantity_In_Stock(Qnt);
//
//		// Get the category (You need to get categoryId from user input)
//		System.out.println("Enter Category ID:");
//		Long categoryId = sc.nextLong();
//
//		// Load the category from the database
//		Category category = session.get(Category.class, categoryId);
//
//		if (category == null) {
//			System.out.println("Category not found!");
//			return; // Handle this as needed
//		}
//
//		product.setCategory(category); // This line associates the category with the product
//
//		session.persist(product);
//		transaction.commit();
//		session.close();
//
//	}
//
////================  UPDATE ===========================
//
//	public void updateProduct(SessionFactory sf) {
//		session = sf.openSession();
//		Transaction transaction = session.beginTransaction();
//
//		Product product;
//
//		try {
//			while (true) {
//				System.out.println("Choose an option\n1.Update product_name\n2.Update price"
//						+ "\n3.Update Quantity In Stock\n4.Update CategoryId\n5.Exit");
//
//				int option = sc.nextInt();
//				switch (option) {
//				case 1:
//
//					System.out.println("Enter Product Id:");
//					product = session.get(Product.class, sc.nextInt());
//					if (product == null) {
//						System.out.println("product not found!!");
//						break;
//					}
//					System.out.println("Update Product Name:");
//					product.setProduct_Name(sc.next());
//					session.saveOrUpdate(product);
//					transaction.commit();
//					break;
//
//				case 2:
//
//					System.out.println("Enter Product Id:");
//					product = session.get(Product.class, sc.nextInt());
//					if (product == null) {
//						System.out.println("product not found!!");
//						break;
//					}
//					System.out.println("Update Price:");
//					product.setPrice(sc.nextInt());
//					session.saveOrUpdate(product);
//					transaction.commit();
//					break;
//
//				case 3:
//
//					System.out.println("Enter Product Id:");
//					product = session.get(Product.class, sc.nextInt());
//					if (product == null) {
//						System.out.println("product not found!!");
//						break;
//					}
//					System.out.println("Update Quantity:");
//					product.setQuantity_In_Stock(sc.nextInt());
//					session.saveOrUpdate(product);
//					transaction.commit();
//					break;
//
//				case 4:
//					System.out.println("Enter Product Id:");
//					product = session.get(Product.class, sc.nextInt());
//					if (product == null) {
//						System.out.println("product not found!!");
//						break;
//					}
//					System.out.println("Update Category Id:");
//					int id = sc.nextInt();
//					Category category = session.get(Category.class, id);
//                    if (category == null) {
//                        System.out.println("Category not found!!");
//                        transaction.rollback(); // Rollback if category not found
//                        break;
//                    }
//                    product.setCategory(category);
//                    session.saveOrUpdate(product);
//                    transaction.commit(); // Commit after successful update
//                    break;
//
//				case 5:
//					System.out.println("Aplication exited !");
//					System.exit(0);
//					break;
//
//				default:
//					System.out.println("Choose the correct option");
//					break;
//
//				}
//			}
//		} finally {
//			session.close();
//		}
//
//	}
//
////==================  DELETE  ======================
//	public void deleteProduct(SessionFactory sf) {
//		session = sf.openSession();
//		Transaction transaction = session.beginTransaction();
//
//		System.out.println("Enter product Id:");
//		int id = sc.nextInt();
//
//		Product product = session.get(Product.class, id);
//
//		if (product != null)
//			session.delete(product);
//		else
//			System.out.println("Enter valid Product Id:");
//		transaction.commit();
//		session.close();
//	}
