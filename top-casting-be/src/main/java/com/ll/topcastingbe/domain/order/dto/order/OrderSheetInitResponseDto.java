package com.ll.topcastingbe.domain.order.dto.order;

import com.ll.topcastingbe.domain.order.dto.order.response.OrderSheetInitResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderSheetInitResponseDto(String memberName,
                                        String phoneNumber,
                                        String memberAddress,
                                        Long shippingFee,
                                        List<OrderSheetItemInitResponseDto> orderSheetItemInitResponseDtos) {

    public static OrderSheetInitResponseDto of(final OrderSheetInitResponse orderSheetInitResponse) {
        final OrderSheetInitResponseDto orderSheetInitResponseDto = OrderSheetInitResponseDto.builder()
                .memberName(orderSheetInitResponse.memberName())
                .phoneNumber(orderSheetInitResponse.phoneNumber())
                .memberAddress(orderSheetInitResponse.memberAddress())
                .shippingFee(orderSheetInitResponse.shippingFee())
                .orderSheetItemInitResponseDtos(
                        OrderSheetItemInitResponseDto.ofList(orderSheetInitResponse.orderSheetItemInitResponses()))
                .build();
        return orderSheetInitResponseDto;
    }
}
