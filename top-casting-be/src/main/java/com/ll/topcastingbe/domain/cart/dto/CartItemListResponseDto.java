package com.ll.topcastingbe.domain.cart.dto;

import com.ll.topcastingbe.domain.cart.entity.CartItem;
import com.ll.topcastingbe.global.constant.ShippingConst;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class CartItemListResponseDto {
    private List<CartItemResponseDto> cartItems;
    private BigDecimal totalPrice;
    private BigDecimal deliveryFee;

    public CartItemListResponseDto(List<CartItemResponseDto> cartItems, BigDecimal totalPrice) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.deliveryFee = ShippingConst.SHIPPING_FEE;
    }

    public static CartItemListResponseDto toDto(List<CartItem> cartItems) {
        List<CartItemResponseDto> cartItemResponseDtos = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            cartItemResponseDtos.add(CartItemResponseDto.toDto(cartItem));
            BigDecimal itemPrice = cartItem.getOption().getItem().getItemPrice();
            totalPrice = totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(cartItem.getItemQuantity())));
        }

        //총 금액이 무료배송조건이상인 경우
        if (ShippingConst.FREE_SHIPPING_COND.compareTo(totalPrice) <= 0) {
            return new CartItemListResponseDto(cartItemResponseDtos, totalPrice, BigDecimal.ZERO);
        }
        //총 금액이 무료배송조건보다 작은 경우
        return new CartItemListResponseDto(cartItemResponseDtos, totalPrice);
    }
}
