package com.EcommercemgmtServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.Ecommercemgmt.Entity.Inventory;
import com.Ecommercemgmt.Entity.Product;
import com.Ecommercemgmt.utility.InventoryUnavailableException;
import com.EcommercemgmtService.InventoryService;

public class InventoryServiceImpl implements InventoryService {

	Scanner sc = new Scanner(System.in);
	Session session;

//============== INSERT ===============================	
	@Override
	public void insertInventory(SessionFactory sf) throws InventoryUnavailableException {
	    session = sf.openSession();
	    Transaction tx = null;
	    try {
	        tx = session.beginTransaction();
	        Inventory inventory = new Inventory();

	        System.out.println("Welcome to Inventory");
	        System.out.println("Enter Available Quantity :");
	        int qnt = sc.nextInt();

	        
	        int qnt_available = testcheckInventoryUnavailableException(qnt);
	        if (qnt_available == 0) { 
	            throw new InventoryUnavailableException("We are sorry for the delay. We will update you about the product as soon as possible.");
	        }
	        
	        // Set quantity only if valid
	        inventory.setQuantity_available(qnt);

	        System.out.println("Enter Date (YYYY-MM-DD):");
	        String dateInput = sc.next();
	        LocalDate dt;

	        try {
	            dt = LocalDate.parse(dateInput);
	            inventory.setRestock_date(dt);
	        } catch (Exception e) {
	            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format");
	            return; // Exit if the date is invalid
	        }

	        System.out.println("Enter Product Id:");
	        int product_id = sc.nextInt();
	        Product product = session.get(Product.class, product_id);

	        if (product == null) {
	            System.out.println("Product not found");
	            return; // Exit if product is not found
	        }
	        inventory.setProduct(product);

	        session.persist(inventory); // Only persist after all checks are done
	        tx.commit(); // Commit transaction after all operations are successful
	        System.out.println("Inventory item added successfully.");

	    } catch (InventoryUnavailableException e) {
	        if (tx != null) tx.rollback(); // Rollback transaction on custom exception
	        System.out.println(e.getMessage()); // Handle custom exception message
	    } catch (Exception e) {
	        if (tx != null) tx.rollback(); // Rollback transaction on general error
	        e.printStackTrace(); // Handle any other exceptions
	    } finally {
	        session.close(); // Always close the session
	    }
	}

	public int testcheckInventoryUnavailableException(int qt) {
	    if (qt <0) {  
	        return 0;   
	    } else {
	        return 1;  
	    }
	}


//============== UPDATE ===============================		
	@Override
	public void updateInventory(SessionFactory sf) throws InventoryUnavailableException {

		session = sf.openSession();
		Transaction transaction = session.beginTransaction();

		Inventory inventory;

		try {
			while (true) {
				System.out.println("Choose an option\n1.Update product_Id\n2.Update Available Quantity"
						+ "\n3.Update Restock date\n4.Exit");

				int option = sc.nextInt();
				switch (option) {

				case 1:
					// foreign key values accessing process
					System.out.println("Enter Inventory Id:");
					inventory = session.get(Inventory.class, sc.nextInt());

					if (inventory == null) {
						System.out.println("Inventory not found");
						break;
					}

					System.out.println("Update Product Id:");
					int id = sc.nextInt();
					Product product = session.get(Product.class, id);

					if (product == null) {
						System.out.println("product not found");
						break;
					}
					inventory.setProduct(product);
					session.saveOrUpdate(product);
					transaction.commit();
					break;

				case 2:
					System.out.println("Enter Inventory Id:");
					inventory = session.get(Inventory.class, sc.nextInt());

					if (inventory == null) {
						System.out.println("Inventory not found.");
						break;
					}

					System.out.println("Update Quantity:");
					int newQuantity = sc.nextInt();

					if (newQuantity == 0) {
						throw new InventoryUnavailableException("We are sorry for the delay.And we update about product as soon as possible");

					}
					inventory.setQuantity_available(newQuantity);
					session.beginTransaction();
					session.saveOrUpdate(inventory);
					session.getTransaction().commit();
					System.out.println("Quantity updated successfully.");
					break;

				case 3:
					System.out.println("Enter Inventory Id:");
					inventory = session.get(Inventory.class, sc.nextInt());

					if (inventory == null) {
						System.out.println("Inventory not found.");
						break;
					}

					System.out.println("Enter Date (YYYY-MM-DD):");
					String dateInput = sc.next();
					LocalDate restockDate;

					try {
						restockDate = LocalDate.parse(dateInput);
						inventory.setRestock_date(restockDate);
						session.beginTransaction();
						session.saveOrUpdate(inventory);
						session.getTransaction().commit();
						System.out.println("Restock date updated successfully.");
					} catch (Exception e) {
						System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
					}
					break;

				case 4:
					System.out.println("Aplication exited !");
					System.exit(0);
					break;

				default:
					System.out.println("Choose the correct option");
					break;

				}
			}
		} finally {
			session.close();
		}

	}

//============== DELETE ===============================	
	@Override
	public void deleteInventory(SessionFactory sf) {
		session = sf.openSession();
		Transaction transaction = session.beginTransaction();

		System.out.println("Enter Inventory Id:");
		int id = sc.nextInt();

		Inventory inventory = session.get(Inventory.class, id);

		if (inventory != null) {
			session.delete(inventory);
			System.out.println("Inventory item deleted successfully.");

		}
		System.out.println("Enter valid Inventory Id:");
		transaction.commit();
		session.close();

	}

	// ============ SELECT * FROM PRODUCT ==========================
	@Override
	public void getAllInventory(SessionFactory sf) {
		try {
			session = sf.openSession();

			Transaction tx = session.beginTransaction();

			Query query = session.createQuery("from Inventory");
			List<Inventory> resultList = query.getResultList();

			for (Inventory i : resultList)
				System.out.println(i);
			tx.commit();
		} finally {
			session.close();
		}

	}

//============== SELECT A SPECIFIC RECORD ===============================		
	@Override
	public void getInventory(SessionFactory sf) {
		session = sf.openSession();
		System.out.println("Enter Inventory Id:");
		int id = sc.nextInt();

		Inventory inventory = session.get(Inventory.class, id);
		System.out.println(inventory);

	}

//============== COUNT ===============================	

	@Override
	public void getInventoryInformation(SessionFactory sf) {
		try {
			session = sf.openSession();
			Query query = session.createQuery("select count(inventoryId) from Inventory");

			List<Integer> list = query.getResultList();

			System.out.println("Total number of products:" + list.get(0));
		} finally {
			session.close();
		}

	}

}
