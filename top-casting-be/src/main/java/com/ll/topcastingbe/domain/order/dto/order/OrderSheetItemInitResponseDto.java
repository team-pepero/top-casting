package com.ll.topcastingbe.domain.order.dto.order;

import com.ll.topcastingbe.domain.order.dto.order.response.OrderSheetItemInitResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderSheetItemInitResponseDto(Long optionId,
                                            Long itemQuantity) {

    public static OrderSheetItemInitResponseDto of(final OrderSheetItemInitResponse orderSheetItemInitResponse) {
        final OrderSheetItemInitResponseDto orderSheetItemInitResponseDto = OrderSheetItemInitResponseDto.builder()
                .optionId(orderSheetItemInitResponse.optionId())
                .itemQuantity(orderSheetItemInitResponse.itemQuantity())
                .build();
        return orderSheetItemInitResponseDto;
    }

    public static List<OrderSheetItemInitResponseDto> ofList(
            final List<OrderSheetItemInitResponse> orderSheetItemInitResponses) {
        final List<OrderSheetItemInitResponseDto> orderSheetInitResponseDtos = orderSheetItemInitResponses.stream()
                .map(OrderSheetItemInitResponseDto::of)
                .toList();
        return orderSheetInitResponseDtos;
    }
}
