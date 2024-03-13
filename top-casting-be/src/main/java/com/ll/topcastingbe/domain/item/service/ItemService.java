package com.ll.topcastingbe.domain.item.service;

import com.ll.topcastingbe.domain.category.entity.SubCategory;
import com.ll.topcastingbe.domain.category.exception.CategoryErrorMessage;
import com.ll.topcastingbe.domain.category.exception.CategoryNotExistException;
import com.ll.topcastingbe.domain.category.repository.SubCategoryRepository;
import com.ll.topcastingbe.domain.item.dto.request.ItemCreateRequestDto;
import com.ll.topcastingbe.domain.item.dto.response.ItemDetailResponseDto;
import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.item.exception.ItemNotExistException;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import com.ll.topcastingbe.domain.option.dto.ItemDetailOptionResponseDto;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final OptionRepository optionRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ItemDetailResponseDto findItem(Long itemId) {
        Item item = itemRepository.findByItemIdWithImageAndOption(itemId)
                .orElseThrow(() -> new ItemNotExistException());

        List<ItemDetailOptionResponseDto> optionDtos = optionRepository.findByItemId(item.getId())
                .stream()
                .map(ItemDetailOptionResponseDto::toDto)
                .toList();

        return ItemDetailResponseDto.toDto(item, optionDtos);

    }

    public Long saveItem(ItemCreateRequestDto itemRequestDto) {

        //요청된 카테고리가 있는지 검증
        SubCategory subCategory = subCategoryRepository.findByMainCategoryIdAndSubcategoryId(
                        itemRequestDto.getMainCategoryId(), itemRequestDto.getSubCategoryId())
                .orElseThrow(() -> new CategoryNotExistException(CategoryErrorMessage.CATEGORY_NOT_EXIST));

        //아이템 엔티티 생성
        //Todo: 이미지 업로드 기능 구현
        Item item = Item.builder()
                .itemName(itemRequestDto.getItemName())
                .itemPrice(itemRequestDto.getItemPrice())
                .image(null)
                .detailedImage(null)
                .mainCategory(subCategory.getParentCategory())
                .subCategory(subCategory)
                .build();
        Item createdItem = itemRepository.save(item);

        //옵션 엔티티 생성
        itemRequestDto.getItemColors()
                .forEach(o -> optionRepository.save(Option.builder()
                        .colorName(o.getColorName())
                        .stock(o.getStock())
                        .item(item)
                        .build())
                );

        return createdItem.getId();
    }
}
