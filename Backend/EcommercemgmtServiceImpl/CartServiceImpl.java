package com.EcommercemgmtServiceImpl;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

import com.Ecommercemgmt.Entity.Cart;
import com.Ecommercemgmt.Entity.Product;
import com.EcommercemgmtService.CartService;

public class CartServiceImpl implements CartService {

    Scanner sc = new Scanner(System.in);
    Session session;

    //========== INSERT======================================
    @Override
    public void insertCart(SessionFactory sf) {

        session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Cart cart = new Cart();

        System.out.println("Welcome to Cart");

        System.out.println("Enter Cart Id:");
        int cartId = sc.nextInt();
        cart.setCartId(cartId);

        System.out.println("Enter Quantity:");
        int quantity = sc.nextInt();
        cart.setQuantity(quantity);

        // Get Product details
        System.out.println("Enter Product Id:");
        int productId = sc.nextInt();
        Product product = session.get(Product.class, productId);
        if (product != null) {
            cart.setProduct(product);
        } else {
            System.out.println("Product not found for the given Id.");
            return;
        }

        // No need to set Order (removed order related code)

        session.persist(cart);
        tx.commit();
        session.close();
    }

    //========== UPDATE =================================================
    @Override
    public void updateCart(SessionFactory sf) {
        session = sf.openSession();
        Scanner sc = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("Choose an option for Update " + "\n1. Update Quantity\n2. Update Product ID\n3. Exit");
                int option = sc.nextInt();

                switch (option) {
                    case 1: // Update Quantity
                        System.out.println("Enter Cart Id:");
                        Cart cart = session.get(Cart.class, sc.nextInt());
                        if (cart != null) {
                            System.out.println("Update Quantity:");
                            int newQuantity = sc.nextInt();
                            cart.setQuantity(newQuantity);

                            // Automatically update the price based on the new quantity
                            // (Price is calculated automatically based on quantity and product price)
                            Transaction tx = session.beginTransaction();
                            session.saveOrUpdate(cart);
                            tx.commit();
                            System.out.println("Quantity updated successfully. New price: " + cart.getPrice());
                        } else {
                            System.out.println("Cart not found for the given Id.");
                        }
                        break;

                    case 2: // Update Product ID
                        System.out.println("Enter Cart Id:");
                        cart = session.get(Cart.class, sc.nextInt());
                        if (cart != null) {
                            System.out.println("Enter New Product ID:");
                            int newProductId = sc.nextInt();
                            Product newProduct = session.get(Product.class, newProductId);
                            if (newProduct != null) {
                                cart.setProduct(newProduct);
                                // Automatically update the price based on the new product's price
                                Transaction tx = session.beginTransaction();
                                session.saveOrUpdate(cart);
                                tx.commit();
                                System.out.println("Product ID updated successfully. New price: " + cart.getPrice());
                            } else {
                                System.out.println("Product not found for the given ID.");
                            }
                        } else {
                            System.out.println("Cart not found for the given Id.");
                        }
                        break;

                    case 3: // Exit update loop
                        System.out.println("Exiting update...");
                        return;

                    default:
                        System.out.println("Choose the correct option!");
                        break;
                }
            }
        } finally {
            session.close();
            sc.close(); // Don't forget to close the Scanner
        }
    }

    //========= DELETE=====================
    @Override
    public void deleteCart(SessionFactory sf) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();

        System.out.println("Enter Cart Id:");
        int cartId = sc.nextInt();
        Cart cart = session.get(Cart.class, cartId);

        if (cart != null) {
            session.delete(cart);
            tx.commit();
            System.out.println("Cart deleted successfully.");
        } else {
            System.out.println("Please enter a valid Cart Id.");
        }

        session.close();
    }

    //====== SELECT * FROM CART =========================
    @Override
    public void getAllCart(SessionFactory sf) {
        session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from Cart");
        List<Cart> cartList = query.getResultList();

        for (Cart c : cartList) {
            System.out.println(c);
        }

        tx.commit();
        session.close();
    }

    //============ SPECIFIC RECORD======================
    @Override
    public void getCart(SessionFactory sf) {
        session = sf.openSession();

        System.out.println("Enter Cart Id:");
        int cartId = sc.nextInt();
        Cart cart = session.get(Cart.class, cartId);

        if (cart != null) {
            System.out.println(cart);
        } else {
            System.out.println("Cart not found for the given Id.");
        }

        session.close();
    }

    //========== COUNT ===================================
    @Override
    public void getCartInformation(SessionFactory sf) {
        session = sf.openSession();

        Query query = session.createQuery("select count(cartId) from Cart");
        Long count = (Long) query.getSingleResult();

        System.out.println("Total number of carts: " + count);

        session.close();
    }
}




















































//package com.EcommercemgmtServiceImpl;
//
//import java.util.Scanner;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import java.util.List;
//
//import com.Ecommercemgmt.Entity.Cart;
//import com.Ecommercemgmt.Entity.Order;
//import com.Ecommercemgmt.Entity.Product;
//import com.EcommercemgmtService.CartService;
//
//public class CartServiceImpl implements CartService{
//
//	Scanner sc=new Scanner(System.in);
//	Session session;
//	
////========== INSERT======================================	
//	@Override
//	public void insertCart(SessionFactory sf) {
//		
//		session = sf.openSession();
//        Transaction tx = session.beginTransaction();
//
//        Cart cart = new Cart();
//
//        System.out.println("Welcome to Cart");
//
//        System.out.println("Enter Cart Id:");
//        int cartId = sc.nextInt();
//        cart.setCartId(cartId);
//
//        System.out.println("Enter Quantity:");
//        int quantity = sc.nextInt();
//        cart.setQuantity(quantity);
//
//        System.out.println("Enter Price:");
//        int price = sc.nextInt();
//        cart.setPrice(price);
//
//        System.out.println("Enter Product Id:");
//        int productId = sc.nextInt();
//        Product product = session.get(Product.class, productId);
//        if (product != null) {
//            cart.setProduct(product);
//        } else {
//            System.out.println("Product not found for the given Id.");
//            return;
//        }
//
//        System.out.println("Enter Order Id:");
//        int orderId = sc.nextInt();
//        Order order = session.get(Order.class, orderId);
//        if (order != null) {
//            cart.setOrder(order);
//        } else {
//            System.out.println("Order not found for the given Id.");
//            return;
//        }
//
//        session.persist(cart);
//        tx.commit();
//        session.close();
//	}
//	
//	
////========== UPDATE =================================================
//	public void updateCart(SessionFactory sf) {
//	    Session session = sf.openSession();
//	    Scanner sc = new Scanner(System.in); // Assuming you're using a Scanner for input
//
//	    try {
//	        while (true) {
//	            System.out.println("Choose an option for Update " + "\n1. Update Quantity\n2. Update Price\n3. Update Order ID\n4. Update Product ID\n5. Exit");
//	            int option = sc.nextInt();
//
//	            switch (option) {
//	                case 1:
//	                    System.out.println("Enter Cart Id:");
//	                    Cart cart = session.get(Cart.class, sc.nextInt());
//	                    if (cart != null) {
//	                        System.out.println("Update Quantity:");
//	                        cart.setQuantity(sc.nextInt());
//	                        Transaction tx = session.beginTransaction();
//	                        session.saveOrUpdate(cart);
//	                        tx.commit();
//	                        System.out.println("Quantity updated successfully.");
//	                    } else {
//	                        System.out.println("Cart not found for the given Id.");
//	                    }
//	                    break;
//
//	                case 2:
//	                    System.out.println("Enter Cart Id:");
//	                    cart = session.get(Cart.class, sc.nextInt());
//	                    if (cart != null) {
//	                        System.out.println("Update Price:");
//	                        cart.setPrice(sc.nextInt());
//	                        Transaction tx = session.beginTransaction();
//	                        session.saveOrUpdate(cart);
//	                        tx.commit();
//	                        System.out.println("Price updated successfully.");
//	                    } else {
//	                        System.out.println("Cart not found for the given Id.");
//	                    }
//	                    break;
//
//	                case 3:
//	                    System.out.println("Enter Cart Id:");
//	                    cart = session.get(Cart.class, sc.nextInt());
//	                    if (cart != null) {
//	                        System.out.println("Enter New Order ID:");
//	                        int newOrderId = sc.nextInt();
//	                        // Assuming you have a method to get the Order by ID
//	                        Order newOrder = session.get(Order.class, newOrderId);
//	                        if (newOrder != null) {
//	                            cart.setOrder(newOrder);
//	                            Transaction tx = session.beginTransaction();
//	                            session.saveOrUpdate(cart);
//	                            tx.commit();
//	                            System.out.println("Order ID updated successfully.");
//	                        } else {
//	                            System.out.println("Order not found for the given ID.");
//	                        }
//	                    } else {
//	                        System.out.println("Cart not found for the given Id.");
//	                    }
//	                    break;
//
//	                case 4:
//	                    System.out.println("Enter Cart Id:");
//	                    cart = session.get(Cart.class, sc.nextInt());
//	                    if (cart != null) {
//	                        System.out.println("Enter New Product ID:");
//	                        int newProductId = sc.nextInt();
//	                        // Assuming you have a method to get the Product by ID
//	                        Product newProduct = session.get(Product.class, newProductId);
//	                        if (newProduct != null) {
//	                            cart.setProduct(newProduct);
//	                            Transaction tx = session.beginTransaction();
//	                            session.saveOrUpdate(cart);
//	                            tx.commit();
//	                            System.out.println("Product ID updated successfully.");
//	                        } else {
//	                            System.out.println("Product not found for the given ID.");
//	                        }
//	                    } else {
//	                        System.out.println("Cart not found for the given Id.");
//	                    }
//	                    break;
//
//	                case 5:
//	                    System.out.println("Exiting update...");
//	                    return;
//
//	                default:
//	                    System.out.println("Choose the correct option!");
//	                    break;
//	            }
//	        }
//	    } finally {
//	        session.close();
//	        sc.close(); // Don't forget to close the Scanner
//	    }
//	}
////========= DELETE=====================
//	@Override
//	public void deleteCart(SessionFactory sf) {
//		session = sf.openSession();
//        Transaction tx = session.beginTransaction();
//
//        System.out.println("Enter Cart Id:");
//        int cartId = sc.nextInt();
//        Cart cart = session.get(Cart.class, cartId);
//
//        if (cart != null) {
//            session.delete(cart);
//            tx.commit();
//            System.out.println("Cart deleted successfully.");
//        } else {
//            System.out.println("Please enter a valid Cart Id.");
//        }
//
//        session.close();
//	}
//
//
////====== SELECT * FROM CART =========================
//	@Override
//	public void getAllCart(SessionFactory sf) {
//        session = sf.openSession();
//        Transaction tx = session.beginTransaction();
//
//        Query query = session.createQuery("from Cart");
//        List<Cart> cartList = query.getResultList();
//
//        for (Cart c : cartList) {
//            System.out.println(c);
//        }
//        
//        tx.commit();
//        session.close();
//
//	}
//	
////============ SPECIFIC RECORD======================
//
//	@Override
//	public void getCart(SessionFactory sf) {
//        session = sf.openSession();
//
//        System.out.println("Enter Cart Id:");
//        int cartId = sc.nextInt();
//        Cart cart = session.get(Cart.class, cartId);
//
//        if (cart != null) {
//            System.out.println(cart);
//        } else {
//            System.out.println("Cart not found for the given Id.");
//        }
//
//        session.close();
//
//		
//	}
//
//	
////========== COUNT ===================================
//	@Override
//	public void getCartInformation(SessionFactory sf) {
//		session = sf.openSession();
//
//        Query query = session.createQuery("select count(cartId) from Cart");
//        Long count = (Long) query.getSingleResult();
//
//        System.out.println("Total number of carts: " + count);
//        
//        session.close();
//  
//		
//	}
//
//
//}
