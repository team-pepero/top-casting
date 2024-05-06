package com.ll.topcastingbe.domain.item.controller;

import com.ll.topcastingbe.domain.item.dto.request.ItemCreateRequestDto;
import com.ll.topcastingbe.domain.item.dto.request.ItemImageUpdateRequestDto;
import com.ll.topcastingbe.domain.item.dto.request.ItemNameUpdateRequestDto;
import com.ll.topcastingbe.domain.item.dto.request.ItemPriceUpdateRequestDto;
import com.ll.topcastingbe.domain.item.dto.response.ItemDetailResponseDto;
import com.ll.topcastingbe.domain.item.search.dto.SearchItemDto;
import com.ll.topcastingbe.domain.item.search.service.ItemSearchService;
import com.ll.topcastingbe.domain.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
@Slf4j
public class ItemController {
    private final ItemSearchService itemSearchService;
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<?> itemDetails(@PathVariable Long itemId) {
        ItemDetailResponseDto itemDto = itemService.findItem(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(itemDto);
    }

    @PreAuthorize("hasRole('ADMIN')") //상품 추가는 관리자만 가능
    @PostMapping
    public ResponseEntity<?> itemAdd(@RequestBody ItemCreateRequestDto itemRequestDto) {

        log.info("itemRequestDto={}", itemRequestDto);
        log.info("itemColors={}", itemRequestDto.getItemColors());

        Long itemId = itemService.saveItem(itemRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemId);
    }

    /*@PreAuthorize("hasRole('ADMIN')") //상품 삭제는 관리자만 가능
    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> itemRemove(){

    }*/

    @GetMapping("")
    public ResponseEntity<?> itemsList(Pageable pageable) {
        Slice<SearchItemDto> itemList = itemSearchService.getItems(pageable);
        return ResponseEntity.ok().body(itemList);
    }


    @GetMapping(params = "keyword")
    public ResponseEntity<?> searchItems(@RequestParam(value = "keyword") String keyword,
                                         Pageable pageable) {
        Slice<SearchItemDto> searchResult = itemSearchService.ItemsSearch(keyword, pageable);
        return ResponseEntity.ok().body(searchResult);
    }

    @GetMapping(params = "sort")
    public ResponseEntity<?> searchSort(Pageable pageable) {
        Slice<SearchItemDto> sortItemResult = itemSearchService.sortSearch(pageable);
        return ResponseEntity.ok().body(sortItemResult);
    }

    @GetMapping(params = "maincategory")
    public ResponseEntity<?> mainCategoryItems(@RequestParam(value = "maincategory") Long id,
                                               Pageable pageable) {
        Slice<SearchItemDto> mainCategoryItem = itemSearchService.getItemsByMainCategory(pageable, id);
        return ResponseEntity.ok().body(mainCategoryItem);
    }

    @GetMapping(params = "subcategory")
    public ResponseEntity<?> subCategoryItems(@RequestParam(value = "subcategory") Long id,
                                              Pageable pageable) {
        Slice<SearchItemDto> subCategoryItem = itemSearchService.getItemsBySubcategory(pageable, id);
        return ResponseEntity.ok().body(subCategoryItem);
    }

    @PreAuthorize("hasRole('ADMIN')") //아이템 이름 변경은 관리자만 가능
    @PatchMapping("/{itemId}/itemName")
    public ResponseEntity<?> itemNameModify(@PathVariable Long itemId,
                                            @RequestBody @Valid ItemNameUpdateRequestDto updateDto) {
        itemService.modifyItemName(itemId, updateDto);
        return ResponseEntity.ok(null);
    }

    @PreAuthorize("hasRole('ADMIN')") //아이템 이름 변경은 관리자만 가능
    @PatchMapping("/{itemId}/itemPrice")
    public ResponseEntity<?> itemPriceModify(@PathVariable Long itemId,
                                             @RequestBody @Valid ItemPriceUpdateRequestDto updateDto) {
        itemService.modifyItemPrice(itemId, updateDto);
        return ResponseEntity.ok(null);
    }

    @PreAuthorize("hasRole('ADMIN')") //아이템 이미지 변경은 관리자만 가능
    @PatchMapping("/{itemId}/itemImage")
    public ResponseEntity<?> itemImageModify(@PathVariable Long itemId,
                                             @RequestBody ItemImageUpdateRequestDto updateDto) {

        //Todo: Exception 고민해보기
        //이미지와 상세이미지 모두 없다면 예외처리
        if (!updateDto.hasImage() && !updateDto.hasDetailedImage()) {
            throw new IllegalArgumentException();
        }

        itemService.modifyItemImage(itemId, updateDto);
        return ResponseEntity.ok(null);
    }

}
