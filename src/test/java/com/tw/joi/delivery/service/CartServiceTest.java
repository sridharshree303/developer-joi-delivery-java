package com.tw.joi.delivery.service;

import com.tw.joi.delivery.Exception.NotFoundException;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.User;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.seedData.SeedData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartService service;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    private static final String USERID101 = "user101";
    private static final String USERID102 = "user102";
    private static final String USERID103 = "user103";

    private static final String PRODUCT101 = "product101";
    private static final String STORE101 = "store101";

    @Test
    void testNullUserID_addProductToCartForUser(){
        AddProductRequest productRequest = new AddProductRequest();

        //call the service
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addProductToCartForUser(productRequest));

    }

    @Test
    void testInvalidUserID_addProductToCartForUser(){
        AddProductRequest productRequest = new AddProductRequest();
        productRequest.setProductId("product104");
        productRequest.setUserId(USERID102);
        productRequest.setOutletId("store103");

        //call the service
        Assertions.assertThrows(NotFoundException.class, () -> service.addProductToCartForUser(productRequest));

    }

    @Test
    void testNoCartForUser_addProductToCartForUser(){
        AddProductRequest productRequest = new AddProductRequest();
        productRequest.setProductId("product104");
        productRequest.setUserId(USERID103);
        productRequest.setOutletId("store103");

        User user = User.builder().userId(USERID103).build();

        Mockito.when(userService.fetchUserById(USERID103)).thenReturn(user);
        //call the service
        Assertions.assertThrows(NotFoundException.class, () -> service.addProductToCartForUser(productRequest));

    }

    @Test
    void testCartForUser_addProductToCartForUser(){
        AddProductRequest productRequest = new AddProductRequest();
        productRequest.setProductId(PRODUCT101);
        productRequest.setUserId(USERID101);
        productRequest.setOutletId(STORE101);

        User user = User.builder().userId(USERID101).build();

        GroceryProduct product = SeedData.groceryProducts.getFirst();
        Mockito.when(userService.fetchUserById(USERID101)).thenReturn(user);
        Mockito.when(productService.getProduct(PRODUCT101, STORE101)).thenReturn(product);
        //call the service
        CartProductInfo info = service.addProductToCartForUser(productRequest);

        Assertions.assertNotNull(info);
        Assertions.assertNotNull(info.product());
        Assertions.assertNull(info.sellingPrice());
    }

    @Test
    void testCartNullProduct_addProductToCartForUser(){
        AddProductRequest productRequest = new AddProductRequest();
        productRequest.setProductId("product104");
        productRequest.setUserId(USERID103);
        productRequest.setOutletId("store103");

        User user = User.builder().userId(USERID103).build();

        Mockito.when(userService.fetchUserById(USERID103)).thenReturn(user);
        //call the service
        Assertions.assertThrows(NotFoundException.class, () -> service.addProductToCartForUser(productRequest));
    }

    @Test
    void testCartValidProduct_addProductToCartForUser(){
        AddProductRequest productRequest = new AddProductRequest();
        productRequest.setProductId("product101");
        productRequest.setUserId(USERID101);
        productRequest.setOutletId("store101");

        User user = User.builder().userId(USERID101).build();
        GroceryProduct product = new GroceryProduct();
        product.setProductId("product101");

        Mockito.when(userService.fetchUserById(USERID101)).thenReturn(user);
        Mockito.when(productService.getProduct("product101", "store101")).thenReturn(product);

        //call the service
        CartProductInfo info = service.addProductToCartForUser(productRequest);

        Assertions.assertNotNull(info);
        Assertions.assertEquals(product, info.product());

        Mockito.verify(userService, Mockito.times(1)).fetchUserById(USERID101);
    }
}
