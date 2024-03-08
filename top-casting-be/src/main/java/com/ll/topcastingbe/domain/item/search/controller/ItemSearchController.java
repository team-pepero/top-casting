package com.ll.topcastingbe.domain.item.search.controller;

import com.ll.topcastingbe.domain.item.search.dto.SearchItemDto;
import com.ll.topcastingbe.domain.item.search.service.ItemSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemSearchController {
    private final ItemSearchService itemSearchService;

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
}
