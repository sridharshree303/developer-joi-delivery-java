package com.tw.joi.delivery.service;

import com.tw.joi.delivery.base.InventoryServiceBase;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.Outlet;
import com.tw.joi.delivery.dto.response.GroceryStoreInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryService implements InventoryServiceBase {

    @Autowired
    private ProductService productService;

    @Autowired
    private OutletService outletService;

    @Override
    public GroceryStoreInventory getInventoryForStoreID(String storeId) {
        //fetch outlet information
        Outlet store = Optional.ofNullable(outletService.getOutletInfoForOutletID(storeId))
                .orElseThrow(() -> new RuntimeException("Store not found for StoreID: " + storeId));

        //fetch the inventory
        Set<GroceryProduct> inventory = Optional.ofNullable(productService.getProductsByOutletID(store.getOutletId()))
                .orElseGet(Collections::emptySet);

        return new GroceryStoreInventory(store.getOutletId(), store.getName(), inventory);
    }
}
