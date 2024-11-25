package com.EcommercemgmtServiceImpl;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Ecommercemgmt.Entity.Customer;
import com.EcommercemgmtService.CustomerService;

import jakarta.persistence.Query;

public class CustomerServiceImpl implements CustomerService {

	
	Scanner sc=new Scanner(System.in);
	Session session;
	
//============== INSERT ===============================	
	@Override
	public void insertCustomer(SessionFactory sf) {
		
		session =sf.openSession();
		Transaction tx=session.beginTransaction();
		
		Customer cust=new Customer();
		
		System.out.println("Sign up with Ayyan Online Shopping to experience the best deals.We're excited to have you!" );
		//System.out.println("Have a :) ");
		
		System.out.println("Please Enter your Phone Number:");
		Long phno=sc.nextLong();
		cust.setPhoneNumber(phno);
		
		System.out.println("Enter your Id");
		int id=sc.nextInt();
		cust.setCustomerId(id);
		
		System.out.println("Please Complete Your Profile!");
		System.out.println("Enter your Name:");
		String name=sc.next();
		cust.setCustomerName(name);

		sc.nextLine();
		System.out.println("Enter your email:");
		String email=sc.next();
		cust.setEmail(email);

		System.out.println("Create a password:");
		String pass=sc.next();
		cust.setPassword(pass);

		
		System.out.println("Enter your Address:");
		String add=sc.next();
		cust.setAddress(add);

		
		System.out.println("Enter your Phone Number:");
		Long phno1=sc.nextLong();
		cust.setPhoneNumber(phno1);
		
		session.persist(cust);
		tx.commit();
		session.close();
	}

	
//====================== UPDATE =============================
	@Override
	public void updateCustomer(SessionFactory sf) {
	    session = sf.openSession();
	    Customer customer;

	    try {
	        while (true) {
	            System.out.println("Choose an Option for Update"
	                    + "\n1.Update Name\n2.email\n3.password"
	                    + "\n4.Address\n5.phone Number\n6.Exit");

	            int option = sc.nextInt();
	            
	            // Exit the loop if the exit option is selected
	            if (option == 6) {
	                System.out.println("");
	                break;
	            }

	            System.out.println("Change your Name:");
	            
	            if (!sc.hasNextInt()) {
	                System.out.println("Invalid input. Please enter a numeric Customer ID.");
	                sc.next(); // clear the invalid input
	                continue;
	            }

	            int customerId = sc.nextInt();
	            customer = session.get(Customer.class, customerId);

	            if (customer == null) {
	                System.out.println("Sorry Please enter correct Id. Please try again.");
	                continue;
	            }

	            Transaction tx = session.beginTransaction();

	            switch (option) {
	                case 1:
	                    System.out.println("Update Your Name");
	                    customer.setCustomerName(sc.next());
	                    break;
	                case 2:
	                    System.out.println("Update your email");
	                    customer.setEmail(sc.next());
	                    break;
	                case 3:
	                    System.out.println("Change password");
	                    customer.setPassword(sc.next());
	                    break;
	                case 4:
	                    System.out.println("Update your Address");
	                    customer.setAddress(sc.next());
	                    break;
	                case 5:
	                    System.out.println("Update your Phone Number");
	                    customer.setPhoneNumber(sc.nextLong());
	                    break;
	                default:
	                    System.out.println("Invalid option, please try again.");
	                    continue;
	            }

	            session.saveOrUpdate(customer);
	            tx.commit();
	            System.out.println("Your Profile Updated Successfully!");
	        }
	    } finally {
	        if (session != null) {
	            session.close(); // Ensure session is closed to prevent leaks
	        }
	    }
	}
	
//=============== DELETE ==============================	
@Override
public void deleteCustomer(SessionFactory sf) {
	
	session =sf.openSession();
	Transaction tx = session.beginTransaction();
	
	//System.out.println("");
	System.out.println("Enter Your Id:");
	int id=sc.nextInt();
	Customer cust=session.get(Customer.class, id);
	
	try {
		if(cust!=null) {
			session.delete(cust);
			
			tx.commit();
		}else {
			System.out.println("Please Recheck Your Id");
		}
	}finally {
		session.close();
	}
}
//========== specific record==========================

@Override
public void getCustomer(SessionFactory sf) {
	session=sf.openSession();
	System.out.println("Please Enter Your Id .To get all details:");
	int id=sc.nextInt();
	 
	Customer cust=session.get(Customer.class, id);
	System.out.println(cust);
	session.close();
}


//============= SELECT * FROM CUSTOMER ===================
//@Override
//public void getAllCustomer(SessionFactory sf) {
//	session=sf.openSession();
//	Transaction tx = session.beginTransaction();
//	
//	Query query=session.createQuery("from Customer");
//	List<Customer> resultList=query.getResultList();
//	
//	for(Customer cust:resultList)
//		System.out.println(cust);
//	tx.commit();
//	session.close();
//	
//}


//========= COUNT ======================================
//@Override
//public void getCustomerInformation(SessionFactory sf) {
//	session = sf.openSession();
//
//	Query query = session.createQuery("select count(customerId) from Customer");
//	List<Integer> list = query.getResultList();
//
//	System.out.println("Total number of customers:" + list.get(0));
//	session.close();
//
//	
//}
//
	}
