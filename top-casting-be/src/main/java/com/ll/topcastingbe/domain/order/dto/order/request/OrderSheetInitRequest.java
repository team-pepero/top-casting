package com.ll.topcastingbe.domain.order.dto.order.request;

import java.util.List;
import lombok.Builder;

@Builder
public record OrderSheetInitRequest(List<OrderSheetItemInitRequest> orderSheetItemInitRequests,
                                    Long totalPrice,
                                    Long shippingFee) {
}
