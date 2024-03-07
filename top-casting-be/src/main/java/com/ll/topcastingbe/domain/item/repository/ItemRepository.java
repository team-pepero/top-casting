package com.ll.topcastingbe.domain.item.repository;

import com.ll.topcastingbe.domain.item.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i " +
            "WHERE LOWER(i.itemName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Slice<Item> findSliceByItemNameIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT i FROM Item i")
    Slice<Item> findAllItems(Pageable pageable);
}
