package com.EcommercemgmtService;

import org.hibernate.SessionFactory;

import com.Ecommercemgmt.utility.InventoryUnavailableException;

public interface InventoryService {

	void insertInventory(SessionFactory sf)throws InventoryUnavailableException;

	void updateInventory(SessionFactory sf)throws InventoryUnavailableException;

	void deleteInventory(SessionFactory sf) ;

	void getAllInventory(SessionFactory sf);

	void getInventory(SessionFactory sf);

	void getInventoryInformation(SessionFactory sf);

}
