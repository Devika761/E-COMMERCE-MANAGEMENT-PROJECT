package com.Ecommercemgmt.utility;

public class InventoryUnavailableException extends Exception{

	String name;
	public InventoryUnavailableException(String message){
		super(message);
	}
}
