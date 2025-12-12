package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.seedData.SeedData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final List<GroceryProduct> products= SeedData.groceryProducts;

    public GroceryProduct getProduct(String productId, String outletId) {
        return products.stream()
            .filter(groceryProduct ->
                        groceryProduct.getProductId().equals(productId)
                            && groceryProduct.getStore().getOutletId().equals(outletId))
            .findFirst()
            .orElse(null);
    }

    public Set<GroceryProduct> getProductsByOutletID(String outletID){
        return products.stream().filter(p -> p.getStore().getOutletId().equals(outletID))
                .collect(Collectors.toSet());
    }

}
