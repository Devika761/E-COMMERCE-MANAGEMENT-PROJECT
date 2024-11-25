package com.EcommercemgmtServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.Ecommercemgmt.Entity.Category;
import com.Ecommercemgmt.Entity.Customer;
import com.Ecommercemgmt.Entity.Inventory;
import com.Ecommercemgmt.Entity.Order;
import com.Ecommercemgmt.Entity.Payment;
import com.Ecommercemgmt.Entity.Product;
import com.Ecommercemgmt.utility.InventoryUnavailableException;
import com.EcommercemgmtService.AdminService;

public  class AdminServiceImpl implements AdminService{
	
	Scanner sc=new Scanner(System.in);
	Session session;
	

//======= CUSTOMER DETAILS=========================	
	
	@Override
	public void getAllCustomer(SessionFactory sf) {
		session=sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Query query=session.createQuery("from Customer");
		List<Customer> resultList=query.getResultList();
		
		for(Customer cust:resultList)
			System.out.println(cust);
		tx.commit();
		session.close();
		
	}


	@Override
	public void getCustomer(SessionFactory sf) {
		session=sf.openSession();
		System.out.println("Please Enter Your Id .To get all details:");
		int id=sc.nextInt();
		 
		Customer cust=session.get(Customer.class, id);
		System.out.println(cust);
		session.close();
	}


	@Override
	public void getCustomerInformation(SessionFactory sf) {
		session = sf.openSession();
	
		Query query = session.createQuery("select count(customerId) from Customer");
		List<Integer> list = query.getResultList();
	
		System.out.println("Total number of customers:" + list.get(0));
		session.close();
	
		
	}	
	
	
	
//======= PAYMENT DETAILS=========================	

	
	@Override
	public void getAllPayment(SessionFactory sf) {
	  Session session = null;
	  try {
	      session = sf.openSession();
	      Transaction tx = session.beginTransaction();
	
	      Query<Payment> query = session.createQuery("from Payment", Payment.class);
	      List<Payment> resultList = query.getResultList();
	
			for (Payment payment : resultList) {
	          System.out.println(payment);
	          if (payment.getOrder_Id() != null) {
	              System.out.println("Associated Order: " + payment.getOrder_Id());
	          }
	      }
	
	      tx.commit();
	  } catch (Exception e) {
	      e.printStackTrace();
	  }
	}
	
	
	 @Override
	    public void getPayment(SessionFactory sf) {
	        session = sf.openSession();

	        System.out.println("Enter Payment Id:");
	        int paymentId = sc.nextInt();

	        Payment payment = session.get(Payment.class, paymentId);
	        if (payment != null) {
	            System.out.println(payment);
	            if (payment.getOrder_Id() != null) {
	                System.out.println("Associated Order: " + payment.getOrder_Id());
	            }
	        } else {
	            System.out.println("Payment not found.");
	        }

	        session.close();
	    }

	@Override
	public void getPaymentInformation(SessionFactory sf) {
	  Session session = null;
	  try {
	      session = sf.openSession();
	      Transaction tx = session.beginTransaction();
	
	      Query<Payment> query = session.createQuery("from Payment", Payment.class);
	      List<Payment> resultList = query.getResultList();
	
	      for (Payment payment : resultList) {
	          System.out.println(payment);
	          if (payment.getOrder_Id() != null) {
	              System.out.println("Associated Order: " + payment.getOrder_Id());
	          }
	      }
	
	      tx.commit();
	  } catch (Exception e) {
	      e.printStackTrace();
	  }
	}

	
//======= ORDER DETAILS=========================	
	
	 @Override
	  public void getAllOrder(SessionFactory sf) { // Renamed method to match the interface
	      try {
	          session = sf.openSession();
	          Transaction tx = session.beginTransaction();
	  
	          Query<Order> query = session.createQuery("from Order", Order.class);
	          List<Order> resultList = query.getResultList();
	  
	          for (Order o : resultList) {
	              System.out.println(o);
	          }
	          tx.commit();
	      } finally {
	          if (session != null && session.isOpen()) {
	              session.close(); // Ensuring the session is closed to prevent connection leaks
	          }
	      }
	  }

	 @Override
	    public void getOrder(SessionFactory sf) {
	        session = sf.openSession();
	        
	        System.out.println("Enter Order Id:");
	        int id = sc.nextInt();

	        Order order = session.get(Order.class, id);
	        System.out.println(order);

	        session.close();
	    }

	    
	@Override
	public void getOrderInformation(SessionFactory sf) { // Renamed method to match the interface
	    try {
	        session = sf.openSession();
	        Transaction tx = session.beginTransaction();
	
	        Query<Order> query = session.createQuery("from Order", Order.class);
	        List<Order> resultList = query.getResultList();
	
	        for (Order o : resultList) {
	            System.out.println(o);
	        }
	        tx.commit();
	    } finally {
	        if (session != null && session.isOpen()) {
	            session.close(); // Ensuring the session is closed to prevent connection leaks
	        }
	    }
	    }

	
//======= INVENTORY DETAILS=========================	
	
	    @Override
		public void insertInventory(SessionFactory sf) {
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
//======= CATEGORY DETAILS=========================	

		@Override
		public void insertCategory(SessionFactory sf) {
		session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Category category = new Category();
		
		System.out.println("Welcome to Category");
		
		System.out.println("Enter category Id: ");
		int id = sc.nextInt();
		category.setCategory_Id(id);
		
		System.out.println("Enter Category name:");
		String category_name = sc.next();
		category.setCategory_Name(category_name);
		
		session.persist(category);
		tx.commit();
		session.close();
		}
		
		@Override
		public void updateCategory(SessionFactory sf) {
		session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Category category;
		try {
			while (true) {
				System.out.println("Choose an Option for Update " + "\n1.Update Category_Name\n2.Exit");
		
				int option = sc.nextInt();
				switch (option) {
				case 1:
					System.out.println("Enter Category Id:");
					category = session.get(Category.class, sc.nextInt());
		
					if (category != null) {
						System.out.println("Update category name:");
						category.setCategory_Name(sc.next());
		
						session.saveOrUpdate(category);
						tx.commit();
						System.out.println("Category name updated successfully");
					} else {
						System.out.println("Category not found for the given Id");
					}
					break;
		
				case 2:
					System.out.println("Exit");
					System.exit(0);
					break;
		
				default:
					System.out.println("Choose correct option!!");
				}
			}
		} finally {
			session.close();
		}
		
		}
		
		@Override
		public void deleteCategory(SessionFactory sf) {
		
		session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		System.out.println("Enter Category Id:");
		int id = sc.nextInt();
		
		Category category = session.get(Category.class, id);
		try {
			if (category != null) {
				session.delete(category);
				tx.commit();
		
			} else {
				System.out.println("Please Enter valid Category Id");
		
			}
		} finally {
			session.close();
		
		}
		
		}
		

//======= PRODUCT DETAILS=========================	

		public void insertProduct(SessionFactory sf) {
		
				session = sf.openSession();
				Transaction transaction = session.beginTransaction();
		
				Product product = new Product();
		
				System.out.println("Welcome to product");
		
				System.out.println("Enter product Id:");
				int id = sc.nextInt();
				sc.nextLine();
				product.setProduct_Id(id);
		
				System.out.println("Enter product Name:");
				String product_Name = sc.nextLine();
				product.setProduct_Name(product_Name);
		
				System.out.println("Enter price:");
				int price = sc.nextInt();
				product.setPrice(price);
				;
		
				System.out.println("Enter Quantity Available:");
				int Qnt = sc.nextInt();
				product.setQuantity_In_Stock(Qnt);
		
				// Get the category (You need to get categoryId from user input)
				System.out.println("Enter Category ID:");
				Long categoryId = sc.nextLong();
		
				// Load the category from the database
				Category category = session.get(Category.class, categoryId);
		
				if (category == null) {
					System.out.println("Category not found!");
					return; // Handle this as needed
				}
		
				product.setCategory(category); // This line associates the category with the product
		
				session.persist(product);
				transaction.commit();
				session.close();
		
			}
		
		//================  UPDATE ===========================
		
			public void updateProduct(SessionFactory sf) {
				session = sf.openSession();
				Transaction transaction = session.beginTransaction();
		
				Product product;
		
				try {
					while (true) {
						System.out.println("Choose an option\n1.Update product_name\n2.Update price"
								+ "\n3.Update Quantity In Stock\n4.Update CategoryId\n5.Exit");
		
						int option = sc.nextInt();
						switch (option) {
						case 1:
		
							System.out.println("Enter Product Id:");
							product = session.get(Product.class, sc.nextInt());
							if (product == null) {
								System.out.println("product not found!!");
								break;
							}
							System.out.println("Update Product Name:");
							product.setProduct_Name(sc.next());
							session.saveOrUpdate(product);
							transaction.commit();
							break;
		
						case 2:
		
							System.out.println("Enter Product Id:");
							product = session.get(Product.class, sc.nextInt());
							if (product == null) {
								System.out.println("product not found!!");
								break;
							}
							System.out.println("Update Price:");
							product.setPrice(sc.nextInt());
							session.saveOrUpdate(product);
							transaction.commit();
							break;
		
						case 3:
		
							System.out.println("Enter Product Id:");
							product = session.get(Product.class, sc.nextInt());
							if (product == null) {
								System.out.println("product not found!!");
								break;
							}
							System.out.println("Update Quantity:");
							product.setQuantity_In_Stock(sc.nextInt());
							session.saveOrUpdate(product);
							transaction.commit();
							break;
		
						case 4:
							System.out.println("Enter Product Id:");
							product = session.get(Product.class, sc.nextInt());
							if (product == null) {
								System.out.println("product not found!!");
								break;
							}
							System.out.println("Update Category Id:");
							int id = sc.nextInt();
							Category category = session.get(Category.class, id);
		                    if (category == null) {
		                        System.out.println("Category not found!!");
		                        transaction.rollback(); // Rollback if category not found
		                        break;
		                    }
		                    product.setCategory(category);
		                    session.saveOrUpdate(product);
		                    transaction.commit(); // Commit after successful update
		                    break;
		
						case 5:
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
		
		//==================  DELETE  ======================
			public void deleteProduct(SessionFactory sf) {
				session = sf.openSession();
				Transaction transaction = session.beginTransaction();
		
				System.out.println("Enter product Id:");
				int id = sc.nextInt();
		
				Product product = session.get(Product.class, id);
		
				if (product != null)
					session.delete(product);
				else
					System.out.println("Enter valid Product Id:");
				transaction.commit();
				session.close();
			}

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
