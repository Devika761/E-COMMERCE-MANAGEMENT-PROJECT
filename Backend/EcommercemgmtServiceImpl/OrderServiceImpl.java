package com.EcommercemgmtServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.Ecommercemgmt.Entity.Cart;
import com.Ecommercemgmt.Entity.Customer;
import com.Ecommercemgmt.Entity.Order;
import com.EcommercemgmtService.OrderService;

public class OrderServiceImpl implements OrderService {

    Scanner sc = new Scanner(System.in);
    Session session;

    //======== INSERT =====================================
    @Override
    public void insertOrder(SessionFactory sf) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Order order = new Order();

        System.out.println("Welcome to Order Management");

        System.out.println("Enter Order Id:");
        int orderId = sc.nextInt();
        order.setOrderId(orderId);

        System.out.println("Enter Customer Id:");
        int customerId = sc.nextInt();
        Customer customer = session.get(Customer.class, customerId);

        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        order.setCustomer(customer);  // Set the customer to the order

        System.out.println("Enter Order Date (YYYY-MM-DD):");
        String dateInput = sc.next();
        LocalDate orderDate;

        try {
            orderDate = LocalDate.parse(dateInput);
            order.setOrderDate(Date.valueOf(orderDate));  // Store as SQL Date
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format");
            return;
        }

        // Automatically calculate total amount based on cart items
        float totalAmount = 0;

        // Assuming we have a Cart object that is associated with the Order
        System.out.println("Enter number of cart items:");
        int cartItemCount = sc.nextInt();

        for (int i = 0; i < cartItemCount; i++) {
            System.out.println("Enter Cart Item ID:");
            int cartItemId = sc.nextInt();
            Cart cartItem = session.get(Cart.class, cartItemId);

            if (cartItem == null) {
                System.out.println("Cart item not found. Skipping.");
                continue;
            }

            // Assuming Cart has quantity and price, the total price for that cart item is quantity * price
            totalAmount += cartItem.getPrice() * cartItem.getQuantity();  // Calculate price for each cart item
        }

        order.setTotalAmount(totalAmount);

        // Persist the order in the database
        session.persist(order);
        tx.commit();
        session.close();
    }

    //======== UPDATE =====================================
    @Override
    public void updateOrder(SessionFactory sf) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Order order;

        try {
            while (true) {
                System.out.println("Choose an option\n1. Update Customer Id\n2. Update Order Date"
                        + "\n3. Update Total Amount\n4. Exit");

                int option = sc.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("Enter Order Id:");
                        order = session.get(Order.class, sc.nextInt());

                        if (order == null) {
                            System.out.println("Order not found");
                            break;
                        }

                        System.out.println("Enter new Customer Id:");
                        int customerId = sc.nextInt();
                        Customer customer = session.get(Customer.class, customerId);

                        if (customer == null) {
                            System.out.println("Customer not found");
                            break;
                        }
                        order.setCustomer(customer);
                        session.saveOrUpdate(order);
                        tx.commit();
                        System.out.println("Customer updated successfully.");
                        break;

                    case 2:
                        System.out.println("Enter Order Id:");
                        order = session.get(Order.class, sc.nextInt());

                        if (order == null) {
                            System.out.println("Order not found");
                            break;
                        }

                        System.out.println("Enter new Order Date (YYYY-MM-DD):");
                        String dateInput = sc.next();
                        LocalDate newOrderDate;

                        try {
                            newOrderDate = LocalDate.parse(dateInput);  // Ensure the format is correct
                            order.setOrderDate(Date.valueOf(newOrderDate));
                            session.saveOrUpdate(order);
                            tx.commit();
                            System.out.println("Order date updated successfully.");
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                        } catch (Exception e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.println("Enter Order Id:");
                        order = session.get(Order.class, sc.nextInt());

                        if (order == null) {
                            System.out.println("Order not found");
                            break;
                        }

                        // Recalculate the total amount based on the updated cart items
                        float totalAmount = 0;

                        System.out.println("Enter number of cart items:");
                        int cartItemCount = sc.nextInt();

                        for (int i = 0; i < cartItemCount; i++) {
                            System.out.println("Enter Cart Item ID:");
                            int cartItemId = sc.nextInt();
                            Cart cartItem = session.get(Cart.class, cartItemId);

                            if (cartItem == null) {
                                System.out.println("Cart item not found. Skipping.");
                                continue;
                            }

                            totalAmount += cartItem.getPrice() * cartItem.getQuantity();  // Calculate price for each cart item
                        }

                        order.setTotalAmount(totalAmount);
                        session.saveOrUpdate(order);
                        tx.commit();
                        System.out.println("Total amount updated successfully.");
                        break;

                    case 4:
                        System.out.println("Application exited!");
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

    //======== DELETE =====================================
    @Override
    public void deleteOrder(SessionFactory sf) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();

        System.out.println("Enter Order Id:");
        int orderId = sc.nextInt(); // Capture user input for orderId

        try {
            // First delete associated cart items
            Query deleteCartQuery = session.createQuery("delete from Cart where order.id = :orderId");
            deleteCartQuery.setParameter("orderId", orderId);
            deleteCartQuery.executeUpdate();

            // Then delete the order
            Order order = session.get(Order.class, orderId);
            if (order != null) {
                session.delete(order);
                System.out.println("Order deleted successfully.");
            } else {
                System.out.println("Order not found.");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close(); // Close the session to prevent connection leaks
        }
    }

    //======== SELECT * FROM ORDER =====================================
   
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
  //   ============== SELECT A SPECIFIC ORDER ===============================
    @Override
    public void getOrder(SessionFactory sf) {
        session = sf.openSession();
        
        System.out.println("Enter Order Id:");
        int id = sc.nextInt();

        Order order = session.get(Order.class, id);
        System.out.println(order);

        session.close();
    }

	public void getOrderInformation(SessionFactory factory) {
		// TODO Auto-generated method stub
		
	}

    }




















//// ============== COUNT ===============================
//@Override
//public void getOrderInformation(SessionFactory sf) {
//    try {
//        session = sf.openSession();
//        Query<Long> query = session.createQuery("select count(orderId) from Order", Long.class);
//
//        Long count = query.getSingleResult();
//        System.out.println("Total number of orders: " + count);
//    } finally {
//        session.close();
//    }
//
//
//
//@Override
//public void getAllOrder(SessionFactory sf) { // Renamed method to match the interface
//    try {
//        session = sf.openSession();
//        Transaction tx = session.beginTransaction();
//
//        Query<Order> query = session.createQuery("from Order", Order.class);
//        List<Order> resultList = query.getResultList();
//
//        for (Order o : resultList) {
//            System.out.println(o);
//        }
//        tx.commit();
//    } finally {
//        if (session != null && session.isOpen()) {
//            session.close(); // Ensuring the session is closed to prevent connection leaks
//        }
//    }
//}
//
















































//package com.EcommercemgmtServiceImpl;
//
//import java.sql.Date;
//import java.time.LocalDate;
//import java.time.format.DateTimeParseException;
//import java.util.List;
//import java.util.Scanner;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//
//import com.Ecommercemgmt.Entity.Customer;
//import com.Ecommercemgmt.Entity.Order;
//import com.Ecommercemgmt.Entity.Payment;
//import com.EcommercemgmtService.OrderService;
//
//public class OrderServiceImpl implements OrderService{
//
//	
//	Scanner sc = new Scanner(System.in);
//    Session session;
//
////======== INSERT =====================================	
//	@Override
//	public void insertOrder(SessionFactory sf) {
//		
//		session = sf.openSession();
//        Transaction tx = session.beginTransaction();
//
//        Order order = new Order();
//
//        System.out.println("Welcome to Order Management");
//
//        System.out.println("Enter Order Id:");
//        int orderId = sc.nextInt();
//        order.setOrderId(orderId);
//
//        System.out.println("Enter Customer Id:");
//        int customerId = sc.nextInt();
//        Customer customer = session.get(Customer.class, customerId);
//
//        if (customer == null) {
//            System.out.println("Customer not found");
//            return;
//        }
//        order.setCustomer(customer);
//
//        System.out.println("Enter Order Date (YYYY-MM-DD):");
//        String dateInput = sc.next();
//        LocalDate orderDate;
//
//        try {
//            orderDate = LocalDate.parse(dateInput);
//            order.setOrderDate(Date.valueOf(orderDate));
//        } catch (Exception e) {
//            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format");
//            return;
//        }
//
//        System.out.println("Enter Total Amount:");
//        float totalAmount = sc.nextFloat();
//        order.setTotalAmount(totalAmount);
////
////        System.out.println("Enter Payment Id:");
////        int paymentId = sc.nextInt();
////        Payment payment = session.get(Payment.class, paymentId);
////
////        if (payment == null) {
////            System.out.println("Payment not found");
////            return;
////        }
//       // order.setPayment(payment);
//       // payment.setOrder_Id(order);
//
//        session.persist(order);
//        tx.commit();
//        session .close();
//	}
//
//	
//	//======== UPDATE =====================================	
//	@Override
//	public void updateOrder(SessionFactory sf) {
//		session = sf.openSession();
//        Transaction tx = session.beginTransaction();
//
//        Order order;
//
//        try {
//            while (true) {
//                System.out.println("Choose an option\n1. Update Customer Id\n2. Update Order Date"
//                        + "\n3. Update Total Amount\n4. Exit");
//
//                int option = sc.nextInt();
//                switch (option) {
//                    case 1:
//                        System.out.println("Enter Order Id:");
//                        order = session.get(Order.class, sc.nextInt());
//
//                        if (order == null) {
//                            System.out.println("Order not found");
//                            break;
//                        }
//
//                        System.out.println("Enter new Customer Id:");
//                        int customerId = sc.nextInt();
//                        Customer customer = session.get(Customer.class, customerId);
//
//                        if (customer == null) {
//                            System.out.println("Customer not found");
//                            break;
//                        }
//                        order.setCustomer(customer);
//                        session.saveOrUpdate(order);
//                        tx.commit();
//                        System.out.println("Customer updated successfully.");
//                        break;
//
//                    case 2:
//                        System.out.println("Enter Order Id:");
//                        order = session.get(Order.class, sc.nextInt());
//
//                        if (order == null) {
//                            System.out.println("Order not found");
//                            break;
//                        }
//
//                        System.out.println("Enter new Order Date (YYYY-MM-DD):");
//                        String dateInput = sc.next();
//                        LocalDate newOrderDate;
//
//                        try {
//                            newOrderDate = LocalDate.parse(dateInput);  // Ensure the format is correct
//                            order.setOrderDate(Date.valueOf(newOrderDate));
//                            session.saveOrUpdate(order);
//                            tx.commit();
//                            System.out.println("Order date updated successfully.");
//                        } catch (DateTimeParseException e) {
//                            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
//                        } catch (Exception e) {
//                            System.out.println("An error occurred: " + e.getMessage());
//                        }
//                        break;
//
//                    case 3:
//                        System.out.println("Enter Order Id:");
//                        order = session.get(Order.class, sc.nextInt());
//
//                        if (order == null) {
//                            System.out.println("Order not found");
//                            break;
//                        }
//
//                        System.out.println("Enter new Total Amount:");
//                        order.setTotalAmount(sc.nextFloat());
//                        session.saveOrUpdate(order);
//                        tx.commit();
//                        System.out.println("Total amount updated successfully.");
//                        break;
//
////                    case 4:
////                        System.out.println("Enter Order Id:");
////                        order = session.get(Order.class, sc.nextInt());
////
////                        if (order == null) {
////                            System.out.println("Order not found");
////                            break;
////                        }
////
////                        System.out.println("Enter new Payment Id:");
////                        int paymentId = sc.nextInt();
////                        Payment payment = session.get(Payment.class, paymentId);
////
////                        if (payment == null) {
////                            System.out.println("Payment not found");
////                            break;
////                        }
//                      //  order.setPayment(payment);
//                        session.saveOrUpdate(order);
//                        tx.commit();
//                        System.out.println("Payment updated successfully.");
//                        break;
//
//                    case 4:
//                        System.out.println("Application exited!");
//                        System.exit(0);
//                        break;
//
//                    default:
//                        System.out.println("Choose the correct option");
//                        break;
//                }
//            }
//        } finally {
//            session.close();
//        }
//		
//	}
//
//	
//	//======== DELETE =====================================		
//	@Override
//	public void deleteOrder(SessionFactory sf) {
//	    session = sf.openSession();
//	    Transaction tx = session.beginTransaction();
//
//	    System.out.println("Enter Order Id:");
//	    int orderId = sc.nextInt(); // Capture user input for orderId
//
//	    try {
//	        // First delete associated cart items
//	        Query deleteCartQuery = session.createQuery("delete from Cart where order.id = :orderId");
//	        deleteCartQuery.setParameter("orderId", orderId);
//	        deleteCartQuery.executeUpdate();
//
//	        // Then delete the order
//	        Order order = session.get(Order.class, orderId);
//	        if (order != null) {
//	            session.delete(order);
//	            System.out.println("Order deleted successfully.");
//	        } else {
//	            System.out.println("Order not found.");
//	        }
//	        tx.commit();
//	    } catch (Exception e) {
//	        if (tx != null) tx.rollback();
//	        e.printStackTrace();
//	    } finally {
//	        session.close(); // Close the session to prevent connection leaks
//	    }
//	}
////======== SELECT * FROM ORDER =====================================		
//
//	@Override
//	public void getAllOrder(SessionFactory sf) { // Renamed method to match the interface
//	    try {
//	        session = sf.openSession();
//	        Transaction tx = session.beginTransaction();
//
//	        Query<Order> query = session.createQuery("from Order", Order.class);
//	        List<Order> resultList = query.getResultList();
//
//	        for (Order o : resultList) {
//	            System.out.println(o);
//	        }
//	        tx.commit();
//	    } finally {
//	        if (session != null && session.isOpen()) {
//	            session.close(); // Ensuring the session is closed to prevent connection leaks
//	        }
//	    }
//	}
//
//    // ============== SELECT A SPECIFIC ORDER ===============================
//    @Override
//    public void getOrder(SessionFactory sf) {
//        session = sf.openSession();
//        
//        
//        System.out.println("Enter Order Id:");
//        int id = sc.nextInt();
//
//        Order order = session.get(Order.class, id);
//        System.out.println(order);
//
//        session.close();
//    }
//
//    // ============== COUNT ===============================
//    @Override
//    public void getOrderInformation(SessionFactory sf) {
//        try {
//            session = sf.openSession();
//            Query<Long> query = session.createQuery("select count(orderId) from Order", Long.class);
//
//            Long count = query.getSingleResult();
//            System.out.println("Total number of orders: " + count);
//        } finally {
//            session.close();
//        }
//    }
//
//
//	
//}
