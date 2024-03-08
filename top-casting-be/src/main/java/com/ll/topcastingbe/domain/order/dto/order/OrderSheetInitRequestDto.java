package com.ll.topcastingbe.domain.order.dto.order;

import com.ll.topcastingbe.domain.order.dto.order.request.OrderSheetInitRequest;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderSheetInitRequestDto(
        @NotNull(message = "아이템 정보는 필수입니다.") List<OrderSheetItemInitRequestDto> orderSheetItemInitRequestDtos,
        @NotNull(message = "주문 가격은 필수입니다.") Long totalPrice,
        @NotNull(message = "배달비는 필수입니다.") Long shippingFee) {

    public OrderSheetInitRequest toOrderSheetRequest() {
        final OrderSheetInitRequest orderSheetInitRequest = OrderSheetInitRequest.builder()
                .orderSheetItemInitRequests(
                        OrderSheetItemInitRequestDto.toOrderSheetItemRequests(orderSheetItemInitRequestDtos))
                .totalPrice(totalPrice)
                .shippingFee(shippingFee)
                .build();
        return orderSheetInitRequest;
    }
}
