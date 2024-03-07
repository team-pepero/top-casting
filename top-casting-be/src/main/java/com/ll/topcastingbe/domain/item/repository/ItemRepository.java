package com.ll.topcastingbe.domain.item.repository;

import com.ll.topcastingbe.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // 아이템 이름으로 찾기
    List<Item> findByItemName(String itemName);

    // 카테고리명으로 찾기(메인카테고리)

    List<Item> findByMainCategory_Id(Long mainCategoryId);

    // 카테고리명으로 찾기(서브카테고리)

    List<Item> findBySubCategory_Id(Long subCategoryId);

    Item findItemById(long id);
}

