package com.ll.topcastingbe.domain.cart.controller;

import com.ll.topcastingbe.domain.cart.dto.CartItemListResponseDto;
import com.ll.topcastingbe.domain.cart.dto.AddToCartRequestDto;
import com.ll.topcastingbe.domain.cart.dto.CartItemQuantityUpdateRequestDto;
import com.ll.topcastingbe.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    //Todo:MemberId 하드코딩 수정
    @GetMapping
    public ResponseEntity<?> cartList() {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.findCartItemList(1L));
    }

    //@PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> cartItemAdd(//@AuthenticationPrincipal UserPrincipal user,
                                         @RequestBody AddToCartRequestDto cartItemDto) {
        cartService.addCartItem(1L, cartItemDto.getOptionId(), cartItemDto.getItemQuantity());
        CartItemListResponseDto cartItemList = cartService.findCartItemList(1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemList);
    }

    //@PreAuthorize("isAuthenticated()")
    @PostMapping("/{cartItemId}")
    public ResponseEntity<?> cartItemModify(//@AuthenticationPrincipal UserPrincipal user,
                                            @PathVariable Long cartItemId,
                                            @RequestBody CartItemQuantityUpdateRequestDto itemQuantityDto) {
        cartService.modifyCartItem(1L, cartItemId, itemQuantityDto.getItemQuantity());
        CartItemListResponseDto cartItemList = cartService.findCartItemList(1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemList);
    }

    //@PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> cartItemDelete(//@AuthenticationPrincipal UserPrincipal user,
                                            @PathVariable Long cartItemId) {
        cartService.removeCartItem(1L, cartItemId);
        return ResponseEntity.ok(null);
    }

}
