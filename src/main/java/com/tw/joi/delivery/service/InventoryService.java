package com.tw.joi.delivery.service;

import com.tw.joi.delivery.base.InventoryServiceBase;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.domain.Outlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class InventoryService implements InventoryServiceBase {

    @Autowired
    private ProductService productService;

    @Autowired
    private OutletService outletService;

    @Override
    public GroceryStore getInventoryForStoreID(String storeId) {
        //fetch outlet information
        Outlet store = outletService.getOutletInfoForOutletID(storeId);
        if(Objects.isNull(store)){
            throw new RuntimeException("Store not found for StoreID: "+storeId);
        }

        //fetch the inventory
        Set<GroceryProduct> inventory = productService.getProductsByOutletID(storeId);
        GroceryStore groceryStore = GroceryStore.builder()
                .name(store.getName()).outletId(store.getOutletId()).build();
        groceryStore.setInventory(inventory);

        return groceryStore;
    }
}
