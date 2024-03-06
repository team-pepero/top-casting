package com.ll.topcastingbe.domain.item.repository;

import com.ll.topcastingbe.domain.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
