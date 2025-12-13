package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.base.InventoryServiceBase;
import com.tw.joi.delivery.dto.response.GroceryStoreInventory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryServiceBase inventoryServiceBase;

    @GetMapping("/health")
    public ResponseEntity<GroceryStoreInventory> fetchStoreInventoryHealth(@RequestParam(name = "storeId") String storeId) {
        return ResponseEntity.ok(inventoryServiceBase.getInventoryForStoreID(storeId));
    }
}
