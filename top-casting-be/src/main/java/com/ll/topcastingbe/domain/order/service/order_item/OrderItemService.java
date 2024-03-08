package com.ll.topcastingbe.domain.order.service.order_item;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.dto.order_item.request.AddOrderItemRequest;
import com.ll.topcastingbe.domain.order.dto.order_item.request.ModifyOrderItemRequest;
import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import com.ll.topcastingbe.domain.order.entity.Orders;
import java.util.List;
import java.util.UUID;

public interface OrderItemService {
    void addOrderItem(final Orders order,
                      final AddOrderItemRequest addOrderItemRequest);

    List<FindOrderItemResponse> findAllByOrderId(final UUID orderId, final Member member);

    void updateOrderItem(final Long orderItemId,
                         final ModifyOrderItemRequest modifyOrderItemRequest,
                         final Member member);

    void removeAllByOrderItem(final Orders order);
}
