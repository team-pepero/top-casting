package com.ll.topcastingbe.domain.order.service.order_item;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.dto.order_item.request.AddOrderItemRequest;
import com.ll.topcastingbe.domain.order.dto.order_item.request.ModifyOrderItemRequest;
import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import com.ll.topcastingbe.domain.order.entity.Orders;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {

    @Override
    @Transactional
    public void addOrderItem(Orders order, AddOrderItemRequest addOrderItemRequest) {

    }

    @Override
    public List<FindOrderItemResponse> findAllByOrderId(UUID ordersId, Member member) {
        return null;
    }

    @Override
    @Transactional
    public void updateOrderItem(Long orderItemId, ModifyOrderItemRequest modifyOrderItemRequest, Member member) {

    }

    @Override
    @Transactional
    public void removeOrderItem(Long orderItemId, Member member) {

    }
}
