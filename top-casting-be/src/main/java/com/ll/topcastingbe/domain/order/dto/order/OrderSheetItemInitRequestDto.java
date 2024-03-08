package com.ll.topcastingbe.domain.order.dto.order;

import com.ll.topcastingbe.domain.order.dto.order.request.OrderSheetItemInitRequest;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderSheetItemInitRequestDto(Long cartItemId,
                                           Long itemQuantity) {

    public static List<OrderSheetItemInitRequest> toOrderSheetItemRequests(
            List<OrderSheetItemInitRequestDto> orderSheetItemInitRequestDtos) {
        final List<OrderSheetItemInitRequest> orderSheetItemInitRequests = orderSheetItemInitRequestDtos.stream()
                .map(OrderSheetItemInitRequestDto::toOrderSheetItemRequest)
                .toList();
        return orderSheetItemInitRequests;
    }

    public OrderSheetItemInitRequest toOrderSheetItemRequest() {
        final OrderSheetItemInitRequest orderSheetItemInitRequest = OrderSheetItemInitRequest.builder()
                .cartItemId(cartItemId)
                .itemQuantity(itemQuantity)
                .build();
        return orderSheetItemInitRequest;
    }


}
