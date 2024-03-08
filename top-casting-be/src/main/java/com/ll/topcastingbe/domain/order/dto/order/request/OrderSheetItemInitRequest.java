package com.ll.topcastingbe.domain.order.dto.order.request;

import lombok.Builder;

@Builder
public record OrderSheetItemInitRequest(Long cartItemId,
                                        Long itemQuantity) {//이게 왜 필요하지?
}
