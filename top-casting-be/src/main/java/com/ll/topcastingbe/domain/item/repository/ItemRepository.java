package com.ll.topcastingbe.domain.item.repository;

import com.yourmall.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // 아이템 이름으로 찾기

    List<Item> findByItemName;

    findByItemName(String itemName);

    // 카테고리명으로 찾기(메인카테고리)

    List<Item> findByMainCategory_Id;

    findByMainCategory_Id(Long mainCategoryId);

    // 카테고리명으로 찾기(서브카테고리)

    List<Item> findBySubCategory_Id;

    findBySubCategory_Id(Long subCategoryId);

}

