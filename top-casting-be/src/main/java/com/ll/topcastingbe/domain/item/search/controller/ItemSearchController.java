package com.ll.topcastingbe.domain.item.search.controller;

import com.ll.topcastingbe.domain.item.search.dto.SearchItemDto;
import com.ll.topcastingbe.domain.item.search.service.ItemSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<?> getItems(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Slice<SearchItemDto> itemList = itemSearchService.getItems(pageable);
        return ResponseEntity.ok().body(itemList);
    }

    @GetMapping(params = "keyword")
    public ResponseEntity<?> searchItems(@RequestParam(value = "keyword") String keyword,
                                         @RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Slice<SearchItemDto> searchResult = itemSearchService.searchItems(keyword, pageable);
        return ResponseEntity.ok().body(searchResult);
    }

    @GetMapping(params = "sortKeyword")
    public ResponseEntity<?> getSortItems(@RequestParam(value = "sortKeyword") String sortKeyword,
                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        Slice<SearchItemDto> sortItemResult = itemSearchService.getsortItems(page, size, sortKeyword);
        return ResponseEntity.ok().body(sortItemResult);
    }
}
