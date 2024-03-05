package com.ll.topcastingbe.domain.order.service.order;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.dto.order.request.AddOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.ModifyOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.response.AddOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.exception.EntityNotFoundException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public AddOrderResponse addOrder(final AddOrderRequest addOrderRequest, final Member member) {
        final Orders order = addOrderRequest.toOrder(member);
        orderRepository.save(order);

        final AddOrderResponse addOrderResponse = AddOrderResponse.of(order);
        return addOrderResponse;
    }

    @Override
    public FindOrderResponse findOrder(final UUID orderId, final Member member) {
        final Orders order = findByOrderId(orderId);
        order.checkAuthorizedMember(member);

        final FindOrderResponse findOrderResponse = FindOrderResponse.of(order);
        return findOrderResponse;
    }

    @Override
    public List<FindOrderResponse> findOrderList(final Member member) {
        final List<Orders> orders = orderRepository.findAllByMember(member);
        final List<FindOrderResponse> findOrderResponseList = FindOrderResponse.ofList(orders);

        return findOrderResponseList;
    }

    @Override
    @Transactional
    public void modifyOrder(final UUID orderId, final ModifyOrderRequest modifyOrderRequest, final Member member) {
        final Orders order = findByOrderId(orderId);

        order.checkAuthorizedMember(member);

        order.modifyOrder(modifyOrderRequest);
    }

    
    //todo 주문 삭제 기능 보류
    @Override
    public void removeOrder(UUID orderId, Member member) {

    }

    @Override
    public Orders findByOrderId(final UUID orderId) {
        final Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));
        return order;
    }
}
