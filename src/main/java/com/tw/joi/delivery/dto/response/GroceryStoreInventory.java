package com.tw.joi.delivery.dto.response;

import com.tw.joi.delivery.domain.GroceryProduct;

import java.util.Set;

public record GroceryStoreInventory(String storeId, String storeName, Set<GroceryProduct> inventory) {
}
