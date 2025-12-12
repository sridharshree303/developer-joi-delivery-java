package com.tw.joi.delivery.service;

import com.tw.joi.delivery.base.InventoryServiceBase;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.domain.Outlet;
import com.tw.joi.delivery.seedData.SeedData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private OutletService outletService;

    @Mock
    private ProductService productService;

    @Test
    void getInventoryFor_nonExistStoreID(){
        String storeId = "store103";
        Mockito.when(outletService.getOutletInfoForOutletID(storeId)).thenReturn(null);

        Assertions.assertThrows(RuntimeException.class,
                () -> inventoryService.getInventoryForStoreID(storeId));
    }

    @Test
    void getInventoryFor_validStoreID(){
        String storeId = "store101";
        Outlet outlet = new Outlet("Fresh Picks", null, storeId);
        Mockito.when(outletService.getOutletInfoForOutletID(storeId)).thenReturn(outlet);

        Set<GroceryProduct> inventory = SeedData.groceryProducts.stream()
                .filter(data -> data.getStore().getOutletId().equals(storeId)).collect(Collectors.toSet());
        Mockito.when(productService.getProductsByOutletID(storeId)).thenReturn(inventory);

        GroceryStore store = inventoryService.getInventoryForStoreID(storeId);
        Assertions.assertNotNull(store);
        Assertions.assertEquals(storeId, store.getOutletId());
        Assertions.assertEquals(inventory, store.getInventory());

        Mockito.verify(outletService, Mockito.times(1)).getOutletInfoForOutletID(storeId);
        Mockito.verify(productService, Mockito.times(1)).getProductsByOutletID(storeId);
    }
}


