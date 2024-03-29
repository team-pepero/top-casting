package com.ll.topcastingbe.domain.listener;

import com.ll.topcastingbe.domain.email.service.EmailService;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import com.ll.topcastingbe.domain.order.service.order.OrderService;
import com.ll.topcastingbe.domain.payment.event.OrderMailEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class EmailEventListener {
    private final EmailService emailService;
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;

    @EventListener
    public void successPaymentEmail(final OrderMailEvent event) {
        Orders order = orderService.findByOrderId(event.orderId());
        final String setForm = "kissshot.heartunderblade1104@gmail.com";
        final String toMail = order.getMember().getEmail();
        final String title = "결제가 완료되었습니다.";
        final String content = getContent(order);

        emailService.sendMail(setForm, toMail, title, content);
    }

    private String getContent(final Orders order) {
        StringBuilder content = new StringBuilder();
        content.append("주문번호 : " + getOrderNumber(order));
        content.append("\r\n------------------\r\n");
        content.append(getOrderItems(order));
        content.append(order.getTotalItemPrice());
        return content.toString();
    }

    private String getOrderNumber(final Orders order) {
        String orderId = order.getId().toString();
        return orderId;
    }

    private String getOrderItems(final Orders order) {
        final List<OrderItem> orderItems = orderItemRepository.findAllByOrderWithOptionAndPessimisticWriteLock(order);
        StringBuilder content = new StringBuilder();
        for (final OrderItem orderItem : orderItems) {
            content.append(getOrderItem(orderItem));
        }
        return content.toString();
    }

    private String getOrderItem(final OrderItem orderItem) {
        final String itemName = orderItem.getItemName();
        final String option = orderItem.getOption().getColorName();
        final String quantity = orderItem.getItemQuantity().toString();
        final String totalPrice = orderItem.getTotalPrice().toString();

        final String content =
                itemName + " - " + option + " \r\n "
                        + "수량 : " + quantity + "\r\n"
                        + "가격 : " + totalPrice + "\r\n\r\n";
        return content;
    }

}
