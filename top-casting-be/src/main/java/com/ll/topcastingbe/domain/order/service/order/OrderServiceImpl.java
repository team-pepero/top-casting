package com.ll.topcastingbe.domain.order.service.order;

import static com.ll.topcastingbe.domain.order.entity.OrderStatus.ORDER_EXCHANGE_REQUESTED;
import static com.ll.topcastingbe.domain.order.entity.OrderStatus.ORDER_REFUND_REQUESTED;

import com.ll.topcastingbe.domain.cart.entity.CartItem;
import com.ll.topcastingbe.domain.cart.repository.CartItemRepository;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.dto.order.request.AddOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.ModifyOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.OrderSheetInitRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.OrderSheetItemInitRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.RequestCancelOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.response.AddOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.OrderSheetInitResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.OrderSheetItemInitResponse;
import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import com.ll.topcastingbe.domain.order.entity.OrderStatus;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.exception.BusinessException;
import com.ll.topcastingbe.domain.order.exception.EntityNotFoundException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import com.ll.topcastingbe.domain.order.service.order_item.OrderItemService;
import java.util.ArrayList;
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
    private final OrderItemService orderItemService;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public AddOrderResponse addOrder(final AddOrderRequest addOrderRequest, final Member member) {
        final Orders order = addOrderRequest.toOrder(member);
        orderRepository.save(order);

        addOrderItem(order, addOrderRequest);

        final AddOrderResponse addOrderResponse = AddOrderResponse.of(order);
        return addOrderResponse;
    }

    //todo 나중에 리팩토링 해야함
    @Override
    public FindOrderResponse findOrder(final UUID orderId, final Member member) {
        final Orders order = findByOrderId(orderId);
        order.checkAuthorizedMember(member);

        List<FindOrderItemResponse> findOrderItemResponses = orderItemService.findAllByOrderId(orderId, member);
        final FindOrderResponse findOrderResponse = FindOrderResponse.of(order, findOrderItemResponses);

        return findOrderResponse;
    }

    @Override
    public List<FindOrderResponse> findOrderList(final Member member) {
        final List<Orders> orders = orderRepository.findAllByMember(member);

        List<FindOrderResponse> findOrderResponses = new ArrayList<>();
        for (Orders order : orders) {
            final List<FindOrderItemResponse> findOrderItemResponses =
                    orderItemService.findAllByOrderId(order.getId(), member);
            final FindOrderResponse findOrderResponse = FindOrderResponse.of(order, findOrderItemResponses);
            findOrderResponses.add(findOrderResponse);

        }

        return findOrderResponses;
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
    @Transactional
    public void removeOrder(final UUID orderId, final Member member) {
        final Orders order = findByOrderId(orderId);
        order.checkAuthorizedMember(member);
        orderRepository.delete(order);
    }

    @Override
    public Orders findByOrderId(final UUID orderId) {
        final Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));
        return order;
    }

    @Override
    public void checkAuthorizedMemberList(List<Orders> orders, Member member) {
        orders.stream()
                .forEach(order -> order.checkAuthorizedMember(member));
    }

    @Override
    public List<FindOrderResponse> findOrderListForAdmin() {
        final List<Orders> orders = orderRepository.findAll();
        List<FindOrderResponse> findOrderResponses = new ArrayList<>();
        for (Orders order : orders) {
            final List<FindOrderItemResponse> findOrderItemResponses =
                    orderItemService.findAllByOrderIdForAdmin(order.getId());
            final FindOrderResponse findOrderResponse = FindOrderResponse.of(order, findOrderItemResponses);
            findOrderResponses.add(findOrderResponse);
        }
        return findOrderResponses;
    }

    @Override
    @Transactional
    public void requestCancelOrder(final UUID orderId,
                                   final RequestCancelOrderRequest requestCancelOrderRequest,
                                   final Member member) {
        final OrderStatus orderStatus = OrderStatus.checkOrderStatus(requestCancelOrderRequest.orderStatus());
        if (orderStatus == ORDER_REFUND_REQUESTED ||
                orderStatus == ORDER_EXCHANGE_REQUESTED) {
            final Orders order = findByOrderId(orderId);
            order.checkAuthorizedMember(member);
            order.modifyOrderStatus(orderStatus);
        }
    }

    @Override
    public List<FindOrderResponse> findAllCancelOrderRequestsForAdmin() {
        final List<Orders> orders = orderRepository.findByOrderStatusRefundOrOrderStatusExchange(ORDER_REFUND_REQUESTED,
                ORDER_EXCHANGE_REQUESTED);
        List<FindOrderResponse> findOrderResponses = new ArrayList<>();
        for (Orders order : orders) {
            final List<FindOrderItemResponse> findOrderItemResponses =
                    orderItemService.findAllByOrderIdForAdmin(order.getId());
            final FindOrderResponse findOrderResponse = FindOrderResponse.of(order, findOrderItemResponses);
            findOrderResponses.add(findOrderResponse);
        }
        return findOrderResponses;
    }


    //주문 시트 초기화시 필요한 정보 생성
    @Override
    public OrderSheetInitResponse initOrderSheet(final OrderSheetInitRequest orderSheetInitRequest,
                                                 final Member member) {

        List<OrderSheetItemInitResponse> orderSheetItemInitResponses = createOrderSheetItemResponses(
                orderSheetInitRequest);

        final OrderSheetInitResponse orderSheetInitResponse = OrderSheetInitResponse.builder()
                .memberName(member.getName())
                .memberAddress(member.getAddress().getAddress())
                .phoneNumber(member.getPhoneNumber())
                .shippingFee(orderSheetInitRequest.shippingFee())
                .orderSheetItemInitResponses(orderSheetItemInitResponses)
                .build();

        return orderSheetInitResponse;
    }

    ////itemQuantity를 검증 후 List<OrderSheetItemInitResponse>를 만듬
    private List<OrderSheetItemInitResponse> createOrderSheetItemResponses(
            final OrderSheetInitRequest orderSheetInitRequest) {

        List<OrderSheetItemInitResponse> orderSheetItemInitResponses = new ArrayList<>();

        for (OrderSheetItemInitRequest orderSheetItemInitRequest : orderSheetInitRequest.orderSheetItemInitRequests()) {
            final CartItem cartItem = cartItemRepository.findById(orderSheetItemInitRequest.cartItemId())
                    .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));
            //ItemQuantity검증 메서드
            matchItemQuantity(orderSheetItemInitRequest, cartItem);

            final OrderSheetItemInitResponse orderSheetItemInitResponse = OrderSheetItemInitResponse.builder()
                    .optionId(cartItem.getOption().getId())
                    .itemQuantity(Long.valueOf(cartItem.getItemQuantity()))
                    .build();
            orderSheetItemInitResponses.add(orderSheetItemInitResponse);
        }
        return orderSheetItemInitResponses;
    }

    //갯수 검증 로직
    private void matchItemQuantity(final OrderSheetItemInitRequest orderSheetItemInitRequest, final CartItem cartItem) {
        if (cartItem.getItemQuantity() != orderSheetItemInitRequest.itemQuantity()) {
            throw new BusinessException(ErrorMessage.INVALID_INPUT_VALUE);
        }
    }

    private void addOrderItem(final Orders order, final AddOrderRequest addOrderRequest) {
        addOrderRequest.addOrderItemRequest()
                .forEach(addOrderItemRequest -> {
                    orderItemService.addOrderItem(order, addOrderItemRequest);
                });
    }
}
