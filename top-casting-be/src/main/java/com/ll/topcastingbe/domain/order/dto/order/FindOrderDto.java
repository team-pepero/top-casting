package com.ll.topcastingbe.domain.order.dto.order;

import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record FindOrderDto(UUID orderId,
                           String customerName,
                           String customerPhoneNumber,
                           String customerAddress,
                           String orderStatus,
                           LocalDateTime orderCreatedDate,
                           Long totalItemQuantity,
                           Long totalItemPrice) {

    public static FindOrderDto of(final FindOrderResponse findOrderResponse) {
        final FindOrderDto findOrderDto = FindOrderDto.builder()
                .orderId(findOrderResponse.orderId())
                .customerName(findOrderResponse.customerName())
                .customerPhoneNumber(findOrderResponse.customerPhoneNumber())
                .customerAddress(findOrderResponse.customerAddress())
                .orderStatus(findOrderResponse.orderStatus())
                .orderCreatedDate(findOrderResponse.orderCreatedDate())
                .totalItemQuantity(findOrderResponse.totalItemQuantity())
                .totalItemPrice(findOrderResponse.totalItemPrice())
                .build();
        return findOrderDto;
    }
}
