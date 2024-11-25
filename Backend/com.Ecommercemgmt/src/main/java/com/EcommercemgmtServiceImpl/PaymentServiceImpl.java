package com.EcommercemgmtServiceImpl;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.Ecommercemgmt.Entity.Order;
import com.Ecommercemgmt.Entity.Payment;
import com.EcommercemgmtService.PaymentService;

public class PaymentServiceImpl implements PaymentService {

    Scanner sc = new Scanner(System.in);
    Session session;

    // Method to insert a new payment
    @Override
    public void insertPayment(SessionFactory sf) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();

        try {
            Payment payment = new Payment();

            // Get Payment Id
            System.out.println("Enter Payment Id:");
            if (sc.hasNext()) {
                int paymentId = sc.nextInt();
                payment.setPaymentId(paymentId);
            } else {
                System.out.println("Please Enter a valid payment Id!");
                return;
            }

            // Automatically set the Payment Date to the current date
            payment.setPaymentDate(new Date(System.currentTimeMillis()));  // Use the current system date

            // Get Payment Mode
            System.out.println("Enter Payment Mode (e.g., 'Credit Card', 'PayPal', etc.):");
            sc.nextLine();  // Clear the buffer
            String paymentMode = sc.nextLine();
            payment.setPaymentMode(paymentMode);

            // Get Order Id associated with this payment
            System.out.println("Enter Order Id associated with this payment:");
            int orderId = sc.nextInt();
            Order order = session.get(Order.class, orderId);
            if (order != null) {
                payment.setOrder_Id(order);  // Automatically sets the order for the payment

                // Automatically set amountPaid from the Order's total price
                payment.setAmountPaid(order.getTotalPrice());  // Assume getTotalPrice() returns the total price of the order
            } else {
                System.out.println("Order not found, payment cannot be associated.");
                return;
            }

            // Save the Payment
            session.persist(payment);
            tx.commit();
            System.out.println("Payment inserted successfully.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();  // Ensure the session is closed to avoid connection leak
        }
    }

    // Method to update a payment
    @Override
    public void updatePayment(SessionFactory sf) {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();

            System.out.println("Enter Payment Id:");
            int paymentId = sc.nextInt();
            Payment payment = session.get(Payment.class, paymentId);

            if (payment == null) {
                System.out.println("Payment not found");
                return;
            }

            System.out.println("Choose an option to update:\n1. Payment Mode\n2. Order Id\n3. Exit");
            int option = sc.nextInt();
            sc.nextLine();  // Clear the buffer

            switch (option) {
                case 1:
                    // Update Payment Mode
                    System.out.println("Enter new Payment Mode:");
                    payment.setPaymentMode(sc.nextLine());
                    break;
                case 2:
                    // Update Order associated with the payment
                    System.out.println("Enter new Order Id:");
                    int orderId = sc.nextInt();
                    Order order = session.get(Order.class, orderId);
                    if (order != null) {
                        payment.setOrder_Id(order);
                        // Automatically set amountPaid from the Order's total price
                        payment.setAmountPaid(order.getTotalPrice());
                    } else {
                        System.out.println("Order not found.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting update.");
                    return;
                default:
                    System.out.println("Invalid option.");
                    return;
            }

            session.saveOrUpdate(payment);
            tx.commit();
            System.out.println("Payment updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to delete a payment
    @Override
    public void deletePayment(SessionFactory sf) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();

        System.out.println("Enter Payment Id:");
        int paymentId = sc.nextInt();

        Payment payment = session.get(Payment.class, paymentId);
        if (payment != null) {
            if (payment.getOrder_Id() != null) {
                Order order = payment.getOrder_Id();
                order.setPayment(null);  // Break the link to the payment
                session.saveOrUpdate(order);  // Save the update in Order
            }
            session.delete(payment);
            System.out.println("Payment deleted successfully.");
            tx.commit();
        } else {
            System.out.println("Payment not found.");
        }

        session.close();
    }

    // Method to get a specific payment by Payment Id
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

  }

// =====ADD THIS IN ADMIIN=================


// Method to get total number of payments
//@Override
//public void getPaymentInformation(SessionFactory sf) {
//    try {
//        session = sf.openSession();
//        Query query = session.createQuery("select count(paymentId) from Payment");
//
//        List<Integer> list = query.getResultList();
//
//        System.out.println("Total number of Payments: " + list.get(0));
//    } finally {
//        session.close();
//    }
//}

//// Method to get all payments
//@Override
//public void getAllPayment(SessionFactory sf) {
//  Session session = null;
//  try {
//      session = sf.openSession();
//      Transaction tx = session.beginTransaction();
//
//      Query<Payment> query = session.createQuery("from Payment", Payment.class);
//      List<Payment> resultList = query.getResultList();
//
//      for (Payment payment : resultList) {
//          System.out.println(payment);
//          if (payment.getOrder_Id() != null) {
//              System.out.println("Associated Order: " + payment.getOrder_Id());
//          }
//      }
//
//      tx.commit();
//  } catch (Exception e) {
//      e.printStackTrace();
//  }
//}
//



//====================================================================================















//package com.EcommercemgmtServiceImpl;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//
//import com.Ecommercemgmt.Entity.Order;
//import com.Ecommercemgmt.Entity.Payment;
//import com.EcommercemgmtService.PaymentService;
//
//public class PaymentServiceImpl implements PaymentService{
//
//	Scanner sc = new Scanner(System.in);
//    Session session;
//	
//    @Override
//    public void insertPayment(SessionFactory sf) {
//        session = sf.openSession();
//        Transaction tx = session.beginTransaction();
//
//        try {
//            Payment payment = new Payment();
//
//            System.out.println("Enter Payment Id:");
//            if (sc.hasNext()) {
//                int paymentId = sc.nextInt();
//                payment.setPaymentId(paymentId);
//            } else {
//                System.out.println("Please Enter a valid payment Id!");
//            }
//
//            System.out.println("Enter Payment Date (YYYY-MM-DD):");
//            sc.nextLine();  // Clear the buffer
//            String dateInput = sc.nextLine(); 
//            try {
//                payment.setPaymentDate(Date.valueOf(dateInput));
//            } catch (IllegalArgumentException e) {
//                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
//                return;
//            }
//            
//            System.out.println("Enter Payment Mode:");
//            String paymentMode = sc.nextLine();
//            payment.setPaymentMode(paymentMode);
//            
//            System.out.println("Enter Order Id associated with this payment:");
//            int orderId = sc.nextInt();
//            Order order = session.get(Order.class, orderId);  
//            if (order != null) {
//                payment.setOrder_Id(order);
//                //order.setPayment(payment);  
//            } else {
//                System.out.println("Order not found, payment cannot be associated.");
//                return;
//            }
//
//            System.out.println("Enter Amount Paid:");
//            float amountPaid = sc.nextFloat();
//            payment.setAmountPaid(amountPaid);
//
//            session.persist(payment);
//            tx.commit();
//            System.out.println("Payment inserted successfully.");
//        } catch (Exception e) {
//            if (tx != null) tx.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();  // Ensure the session is closed to avoid connection leak
//        }		
//    }
//    //========== update==============================
//	@Override
//	public void updatePayment(SessionFactory sf) {
//	    try (Session session = sf.openSession()) {
//	        Transaction tx = session.beginTransaction();
//
//	        System.out.println("Enter Payment Id:");
//	        int paymentId = sc.nextInt();
//	        Payment payment = session.get(Payment.class, paymentId);
//
//	        if (payment == null) {
//	            System.out.println("Payment not found");
//	            return;
//	        }
//
//	        System.out.println("Choose an option to update:\n1. Payment Date\n2. Payment Mode\n3. Amount Paid\n4. Exit");
//	        int option = sc.nextInt();
//	        sc.nextLine();
//
//	        switch (option) {
//	            case 1:
//	                System.out.println("Enter new Payment Date (YYYY-MM-DD):");
//	                String dateInput = sc.nextLine();
//	                payment.setPaymentDate(Date.valueOf(dateInput));
//	                break;
//	            case 2:
//	                System.out.println("Enter new Payment Mode:");
//	                payment.setPaymentMode(sc.nextLine());
//	                break;
//	            case 3:
//	                System.out.println("Enter new Amount Paid:");
//	                payment.setAmountPaid(sc.nextFloat());
//	                break;
//	            case 4:
//	                    System.out.println("Enter new Order Id:");
//	                    int orderId = sc.nextInt();
//	                    Order order = session.get(Order.class, orderId);
//	                    if (order != null) {
//	                        payment.setOrder_Id(order);
//	                        order.setPayment(payment);  // Maintain bidirectional consistency
//	                    } else {
//	                        System.out.println("Order not found.");
//	                    }
//	                    break;
//	                case 5:
//	                    System.out.println("Exiting update.");
//	                    return;
//	                default:
//	                    System.out.println("Invalid option.");
//	                    return;
//
//	        }
//
//	        session.saveOrUpdate(payment);
//	        tx.commit();
//	        System.out.println("Payment updated successfully.");
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	}
//
////============== delete================================
//   
//    	    @Override
//    	    public void deletePayment(SessionFactory sf) {
//    	        session = sf.openSession();
//    	        Transaction tx = session.beginTransaction();
//
//    	        System.out.println("Enter Payment Id:");
//    	        int paymentId = sc.nextInt();
//
//    	        Payment payment = session.get(Payment.class, paymentId);
//    	        if (payment != null) {
//    	        	 if(payment.getOrder_Id() != null) {
//    	                 Order order = payment.getOrder_Id();
//    	                 order.setPayment(null);
//    	                 session.saveOrUpdate(order);  // Save the update in Order
//    	             }
//    	             session.delete(payment);
//    	             System.out.println("Payment deleted successfully.");
//    	             tx.commit();
//
//    	        } else {
//    	            System.out.println("Payment not found.");
//    	        }
//
//    	        session.close();
//    	    }
//
//  // ======== SELECT ALL PAYMENTS =====================================
//    	    public void getAllPayment(SessionFactory sf) {
//    	        Session session = null;  // Initialize session as null
//    	        try {
//    	            session = sf.openSession();  // Open a new session
//    	            Transaction tx = session.beginTransaction();  // Begin transaction
//
//    	            Query<Payment> query = session.createQuery("from Payment", Payment.class);
//    	            List<Payment> resultList = query.getResultList();
//
//    	            for (Payment payment : resultList) {
//    	                System.out.println(payment);
//    	                if (payment.getOrder_Id() != null) {
//    	                    System.out.println("Associated Order: " + payment.getOrder_Id());
//    	                }
//    	            }
//
//    	            tx.commit();
//    	        } catch (Exception e) {
//    	            e.printStackTrace();
//    	        }
//    	    }
//  // ======== SELECT A SPECIFIC PAYMENT =====================================
//    	    @Override
//    	    public void getPayment(SessionFactory sf) {
//    	        session = sf.openSession();
//
//    	        System.out.println("Enter Payment Id:");
//    	        int paymentId = sc.nextInt();
//
//    	        Payment payment = session.get(Payment.class, paymentId);
//    	        if (payment != null) {
//    	            System.out.println(payment);
//    	            if (payment.getOrder_Id() != null) {
//    	                System.out.println("Associated Order: " + payment.getOrder_Id());
//    	            }
//    	        } else {
//    	            System.out.println("Payment not found.");
//    	        }
//
//    	        session.close();
//    	    }
//    	    
// //=====count===========================================   	    
//    	    public void getPaymentInformation(SessionFactory sf) {
//    		   	try {
//    		   		session=sf.openSession();
//    		   		Query query=session.createQuery("select count(paymentId) from Payment");
//    		   		
//    		   		List<Integer> list=query.getResultList();
//    		   		
//    		   		System.out.println("Total number of Payments:"+list.get(0));
//    		   	}finally {
//    		   		session.close();
//    		   	}
//    	    }
//    		
//    		
//
//}
