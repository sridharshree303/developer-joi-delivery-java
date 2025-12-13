package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.base.InventoryServiceBase;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.domain.Outlet;
import com.tw.joi.delivery.dto.response.GroceryStoreInventory;
import com.tw.joi.delivery.seedData.SeedData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryServiceBase inventoryServiceBase;

    @Test
    void getInventoryHealthForNonExistStoreID() throws Exception {
        String storeId = "store103";
        String getUrl = "/inventory/health?storeId={storeId}";

        Mockito.when(inventoryServiceBase.getInventoryForStoreID(storeId))
                .thenThrow(new RuntimeException("Store not found"));

        //add mocking
        mockMvc.perform(MockMvcRequestBuilders.get(getUrl, storeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getInventoryHealthForStoreID() throws Exception {
        String storeId = "store101";
        String storeName = "Grocery Store";
        String getUrl = "/inventory/health?storeId={storeId}";

        GroceryProduct apple = SeedData.groceryProducts.getFirst();
        GroceryStoreInventory storeInfo = new GroceryStoreInventory(storeId, storeName, Set.of(apple));

        Mockito.when(inventoryServiceBase.getInventoryForStoreID(storeId))
                .thenReturn(storeInfo);

        //add mocking
        mockMvc.perform(MockMvcRequestBuilders.get(getUrl, storeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.storeId").value(storeId))
                .andExpect(jsonPath("$.storeName").value(storeName))
                .andExpect(jsonPath("$.inventory[0].productId").value(apple.getProductId()));

        Mockito.verify(inventoryServiceBase, Mockito.times(1)).getInventoryForStoreID(storeId);
    }

}