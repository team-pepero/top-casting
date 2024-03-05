package com.ll.topcastingbe.domain.order.service.order_item;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import com.ll.topcastingbe.domain.order.dto.order_item.request.AddOrderItemRequest;
import com.ll.topcastingbe.domain.order.dto.order_item.request.ModifyOrderItemRequest;
import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.exception.EntityNotFoundException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {
    private final OptionRepository optionRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public void addOrderItem(Orders order, AddOrderItemRequest addOrderItemRequest) {
        final Option option = optionRepository.findById(addOrderItemRequest.optionId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND));

        final OrderItem orderItem = OrderItem.builder()
                .order(order)
                .option(option)
                .itemQuantity(Long.valueOf(option.getStock()))
                .totalPrice(getTotalPrice(option))
                .build();

        orderItemRepository.save(orderItem);
    }

    private Long getTotalPrice(final Option option) {
        //todo 코드 더러움 수정 필요
        final Long totalPrice = option.getItem().getItemPrice().longValue() * option.getStock();

        return totalPrice;
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
