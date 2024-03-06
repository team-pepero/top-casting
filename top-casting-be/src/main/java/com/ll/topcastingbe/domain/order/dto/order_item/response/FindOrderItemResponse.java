package com.ll.topcastingbe.domain.order.dto.order_item.response;

import com.ll.topcastingbe.domain.order.entity.OrderItem;
import java.util.List;
import lombok.Builder;

@Builder
public record FindOrderItemResponse(String itemName,
                                    Long itemQuantity,
                                    Long totalPrice) {

    public static FindOrderItemResponse of(final OrderItem orderItem) {
        final FindOrderItemResponse findOrderItemResponse = FindOrderItemResponse.builder()
                .itemName(orderItem.getItemName())
                .itemQuantity(orderItem.getItemQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .build();
        return findOrderItemResponse;
    }

    public static List<FindOrderItemResponse> ofList(final List<OrderItem> orderItems) {
        final List<FindOrderItemResponse> findorderItemResponses = orderItems.stream()
                .map(FindOrderItemResponse::of)
                .toList();
        return findorderItemResponses;
    }
}
