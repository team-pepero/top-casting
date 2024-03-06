package com.ll.topcastingbe.domain.order.dto.order_item;

import com.ll.topcastingbe.domain.order.dto.order_item.request.AddOrderItemRequest;
import lombok.Builder;

@Builder
public record AddOrderItemDto(Long optionId,
                              Long itemQuantity) {

    public AddOrderItemRequest toOrderItemDto() {
        final AddOrderItemRequest addOrderItemRequest = AddOrderItemRequest.builder()
                .optionId(optionId)
                .itemQuantity(itemQuantity)
                .build();

        return addOrderItemRequest;
    }
}
