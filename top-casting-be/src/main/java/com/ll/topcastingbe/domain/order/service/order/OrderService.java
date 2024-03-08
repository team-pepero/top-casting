package com.ll.topcastingbe.domain.order.service.order;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.dto.order.request.AddOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.ModifyOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.OrderSheetInitRequest;
import com.ll.topcastingbe.domain.order.dto.order.response.AddOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.OrderSheetInitResponse;
import com.ll.topcastingbe.domain.order.entity.Orders;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    AddOrderResponse addOrder(final AddOrderRequest addOrderRequest, final Member member);

    FindOrderResponse findOrder(final UUID orderId, final Member member);

    List<FindOrderResponse> findOrderList(final Member member);

    void modifyOrder(final UUID orderId, final ModifyOrderRequest modifyOrderRequest, final Member member);

    void removeOrder(final UUID orderId, final Member member);

    Orders findByOrderId(final UUID orderId);

    void checkAuthorizedMemberList(final List<Orders> orders, final Member member);

    OrderSheetInitResponse initOrderSheet(final OrderSheetInitRequest orderSheetInitRequest, final Member member);
}
