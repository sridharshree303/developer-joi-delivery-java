package com.tw.joi.delivery.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tw.joi.delivery.base.InventoryServiceBase;
import com.tw.joi.delivery.domain.GroceryStore;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryServiceBase inventoryService;

    @Test
    void getInventoryHealthForNonExistStoreID() throws Exception {
        String storeId = "store103";
        String getUrl = "/inventory/health?storeId={storeId}";

        Mockito.when(inventoryService.getInventoryForStoreID(storeId))
                .thenThrow(new RuntimeException("Store not found"));

        //add mocking
        mockMvc.perform(MockMvcRequestBuilders.get(getUrl, storeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getInventoryHealthForStoreID() throws Exception {
        String storeId = "store101";
        String getUrl = "/inventory/health?storeId={storeId}";

        Mockito.when(inventoryService.getInventoryForStoreID(storeId))
                .thenReturn(Mockito.any(GroceryStore.class));

        //add mocking
        mockMvc.perform(MockMvcRequestBuilders.get(getUrl, storeId))
                .andExpect(status().isOk());
    }

}