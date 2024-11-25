 package com.Ecommercemgmt.test;

import org.junit.jupiter.api.Assertions;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import com.EcommercemgmtServiceImpl.InventoryServiceImpl;

public class InventoryServiceImplTest {

    InventoryServiceImpl service = new InventoryServiceImpl();

    @ParameterizedTest
    @ValueSource(ints = {-1, 5, 10})  
    void testCheckInventoryAvailable(int qnt) {
        int actual = service.testcheckInventoryUnavailableException(qnt);
        Assertions.assertEquals(1, actual, "Quantity should be positive");
    }
    

    
}























/*
 * @ParameterizedTest
    @ValueSource(ints = {-1,0,-6}) 
    void testCheckInventoryUnavailable(int qnt) {
    	
        int actual = service.testcheckInventoryUnavailableException(qnt);
        Assertions.assertEquals(-1, actual, "Quantity should be zero or negative");
    }
 * 
 */
