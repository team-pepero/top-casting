package com.ll.topcastingbe.domain.order.repository.order_item;

import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrder(final Orders order);

    void removeAllByOrder(final Orders order);
}
