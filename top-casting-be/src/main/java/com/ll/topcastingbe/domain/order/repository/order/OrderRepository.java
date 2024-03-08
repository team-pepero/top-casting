package com.ll.topcastingbe.domain.order.repository.order;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.entity.Orders;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, UUID> {
    List<Orders> findAllByMember(final Member member);
}
