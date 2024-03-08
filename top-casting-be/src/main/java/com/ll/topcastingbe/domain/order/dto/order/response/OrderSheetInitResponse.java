package com.ll.topcastingbe.domain.order.dto.order.response;

import java.util.List;
import lombok.Builder;

@Builder
public record OrderSheetInitResponse(String memberName,
                                     String phoneNumber,
                                     String memberAddress,
                                     Long shippingFee,
                                     List<OrderSheetItemInitResponse> orderSheetItemInitResponses) {
}
