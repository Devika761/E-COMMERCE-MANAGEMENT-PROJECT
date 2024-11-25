package com.Ecommercemgmt.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.Ecommercemgmt.Entity.Cart;
import com.Ecommercemgmt.Entity.Category;
import com.Ecommercemgmt.Entity.Customer;
import com.Ecommercemgmt.Entity.Order;
import com.Ecommercemgmt.Entity.Payment;
import com.Ecommercemgmt.Entity.Product;

import java.util.List;
import java.util.Scanner;

public class EcommerceMgmt {

    public static void main(String[] args) {
        // Setup Hibernate SessionFactory
        Configuration cf = new Configuration();
        cf.configure("hibernate.cfg.xml");  // Make sure you have the correct config file path
        SessionFactory factory = cf.buildSessionFactory();
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Welcome to Ecommerce Management System");

            while (true) {
                // Main menu for User
                System.out.println("\nAre you a User?");
                System.out.println("1. User");
                System.out.println("2. Exit");

                int roleOption = sc.nextInt();
                if (roleOption == 1) {
                    userMenu(sc, factory);
                } else if (roleOption == 2) {
                    System.out.println("Exiting the application...");
                    break; // Exit the loop
                } else {
                    System.out.println("Invalid choice. Please select again.");
                }
            }
        } finally {
            sc.close();
            factory.close();
        }
    }

    private static void userMenu(Scanner sc, SessionFactory factory) {
        while (true) {
            // User Service Options Menu
            System.out.println("\nUser Service Options:");
            System.out.println("1. View Products");
            System.out.println("2. View Categories");
            System.out.println("3. View Orders");
            System.out.println("4. View Cart");
            System.out.println("5. View Payments");
            System.out.println("6. View Customer Information");
            System.out.println("7. Exit");

            int serviceOption = sc.nextInt();
            switch (serviceOption) {
                case 1:
                    viewProducts(factory);
                    break;
                case 2:
                    viewCategories(factory);
                    break;
                case 3:
                    viewOrders(factory);
                    break;
                case 4:
                    viewCart(factory);
                    break;
                case 5:
                    viewPayments(factory);
                    break;
                case 6:
                    viewCustomerInformation(factory);
                    break;
                case 7:
                    System.out.println("Exiting user menu...");
                    return;  // Exit user menu and go back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // Method to view products
    private static void viewProducts(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Query<Product> query = session.createQuery("from Product", Product.class);
            List<Product> products = query.getResultList();

            System.out.println("\nAvailable Products: ");
            for (Product product : products) {
                System.out.println(product);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to view categories
    private static void viewCategories(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Query<Category> query = session.createQuery("from Category", Category.class);
            List<Category> categories = query.getResultList();

            System.out.println("\nProduct Categories: ");
            for (Category category : categories) {
                System.out.println(category);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to view orders
    private static void viewOrders(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Query<Order> query = session.createQuery("from Order", Order.class); // Adjust query if needed for filtering
            List<Order> orders = query.getResultList();

            System.out.println("\nYour Orders: ");
            for (Order order : orders) {
                System.out.println(order);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to view cart
    private static void viewCart(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Object cart = session.get(Cart.class, 1);  // Example: Fetch cart for user ID 1
            System.out.println("\nYour Cart: ");
            System.out.println(cart);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to view payments
    private static void viewPayments(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Payment payment = session.get(Payment.class, 1);  // Example: Fetch payment for order ID 1
            System.out.println("\nYour Payment: ");
            System.out.println(payment);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to view customer information
    private static void viewCustomerInformation(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, 1);  // Example: Fetch customer info for user ID 1
            System.out.println("\nCustomer Information: ");
            System.out.println(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

