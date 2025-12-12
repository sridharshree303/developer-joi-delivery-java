package com.tw.joi.delivery.base;

import com.tw.joi.delivery.domain.GroceryStore;

public interface InventoryServiceBase {

    GroceryStore getInventoryForStoreID(String storeId);
}
