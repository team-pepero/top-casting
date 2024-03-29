package com.ll.topcastingbe.domain.order.repository.order_item;

import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrder(final Orders order);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order = :order")
    List<OrderItem> findAllByOrderWithPessimisticWriteLock(final Orders order);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.option WHERE oi.order = :order")
    List<OrderItem> findAllByOrderWithOptionAndPessimisticWriteLock(final Orders order);

    void removeAllByOrder(final Orders order);
}
