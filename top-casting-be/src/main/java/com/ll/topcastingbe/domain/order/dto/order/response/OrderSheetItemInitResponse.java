package com.ll.topcastingbe.domain.order.dto.order.response;

import lombok.Builder;

@Builder
public record OrderSheetItemInitResponse(Long optionId,
                                         Long itemQuantity) {
}
