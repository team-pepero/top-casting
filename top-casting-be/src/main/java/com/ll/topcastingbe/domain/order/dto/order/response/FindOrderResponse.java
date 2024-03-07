package com.ll.topcastingbe.domain.order.dto.order.response;

import com.ll.topcastingbe.domain.order.entity.Orders;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record FindOrderResponse(UUID orderId,
                                String customerName,
                                String customerPhoneNumber,
                                String customerAddress,
                                String orderStatus,
                                LocalDateTime orderCreatedDate,
                                Long totalItemQuantity,
                                Long totalItemPrice) {

    public static FindOrderResponse of(final Orders order) {
        FindOrderResponse findOrdersResponse = FindOrderResponse.builder()
                .orderId(order.getId())
                .customerName(order.getCustomerName())
                .customerPhoneNumber(order.getCustomerPhoneNumber())
                .customerAddress(order.getCustomerAddress())
                .orderStatus(order.getOrderStatus().toString())
                .totalItemQuantity(order.getTotalItemQuantity())
                .totalItemPrice(order.getTotalItemPrice())
                .build();

        return findOrdersResponse;
    }

    public static List<FindOrderResponse> ofList(final List<Orders> orders) {
        List<FindOrderResponse> findOrdersResponses = orders.stream()
                .map(FindOrderResponse::of)
                .toList();

        return findOrdersResponses;
    }
}
