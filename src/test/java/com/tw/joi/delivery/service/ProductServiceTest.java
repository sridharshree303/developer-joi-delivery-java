package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.seedData.SeedData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Test
    void fetchListOfProducts_forBlankStoreID(){
        String storeId = "";

        Set<GroceryProduct> products = productService.getProductsByOutletID(storeId);

        Assertions.assertEquals(Set.of(), products);
    }

    @Test
    void fetchListOfProducts_forValidOutletID(){
        String storeId = "store101";
        Set<GroceryProduct> expected = SeedData.groceryProducts.stream()
                .filter(data -> data.getStore().getOutletId().equals(storeId)).collect(Collectors.toSet());
        Set<GroceryProduct> products = productService.getProductsByOutletID(storeId);

        Assertions.assertEquals(expected, products);
    }

    @Test
    void fetchListOfProducts_forNonExistOutletID(){
        String storeId = "store103";
        Set<GroceryProduct> expected = SeedData.groceryProducts.stream()
                .filter(data -> data.getStore().getOutletId().equals(storeId)).collect(Collectors.toSet());
        Set<GroceryProduct> products = productService.getProductsByOutletID(storeId);

        Assertions.assertEquals(Set.of(), products);
    }
}
