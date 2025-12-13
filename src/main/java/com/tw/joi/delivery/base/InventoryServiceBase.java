package com.tw.joi.delivery.base;

import com.tw.joi.delivery.domain.Outlet;
import com.tw.joi.delivery.dto.response.GroceryStoreInventory;

public interface InventoryServiceBase {

    GroceryStoreInventory getInventoryForStoreID(String storeId);
}
