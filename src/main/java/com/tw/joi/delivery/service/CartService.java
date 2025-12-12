package com.tw.joi.delivery.service;

import com.tw.joi.delivery.Exception.NotFoundException;
import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.User;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.dto.response.CartProductInfo;
import com.tw.joi.delivery.seedData.SeedData;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final Map<String,Cart> userCarts = SeedData.cartForUsers;
    private final UserService userService;
    private final ProductService productService;

    public CartProductInfo addProductToCartForUser(AddProductRequest addProductRequest) {
        if(Objects.isNull(addProductRequest.getUserId()) ||
                Objects.isNull(addProductRequest.getProductId()) ||
                Objects.isNull(addProductRequest.getOutletId())){
            throw new IllegalArgumentException("Invalid request info");
        }

        User user=userService.fetchUserById(addProductRequest.getUserId());
        if(Objects.isNull(user)){
            throw new NotFoundException("User not found");
        }

        Cart cart = fetchCartForUser(user);
        //instead could a created a new cart foe the user
        if(Objects.isNull(cart)){
            throw new NotFoundException("Cart not found");
        }

        GroceryProduct product = productService.getProduct(addProductRequest.getProductId(),
                                                           addProductRequest.getOutletId());
        if(Objects.isNull(product)){
            throw new NotFoundException("Product not found for ProductID : "+addProductRequest.getProductId());
        }
        cart.getProducts().add(product);
        return new CartProductInfo(cart, product, product.getSellingPrice());
    }

    public Cart getCartForUser(String userId) {
        User user=userService.fetchUserById(userId);
        return fetchCartForUser(user);
    }

    private Cart fetchCartForUser(User user) {
        return userCarts.get(user.getUserId());
    }

}
