package com.ll.topcastingbe.domain.item.search.service;

import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import com.ll.topcastingbe.domain.item.search.dto.SearchItemDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemSearchService {
    private final ItemRepository itemRepository;

    public Slice<SearchItemDto> searchItems(String keyword, Pageable pageable) {
        Slice<Item> itemSlice = itemRepository.findSliceByItemNameIgnoreCase(keyword, pageable);
        List<SearchItemDto> itemDTOList = mapToSearchItemDtoList(itemSlice);

        return new SliceImpl<>(itemDTOList, pageable, itemSlice.hasNext());
    }

    public Slice<SearchItemDto> getItems(Pageable pageable) {
        Slice<Item> itemSlice = itemRepository.findAllItems(pageable);
        List<SearchItemDto> itemDTOList = mapToSearchItemDtoList(itemSlice);

        return new SliceImpl<>(itemDTOList, pageable, itemSlice.hasNext());
    }

    public Slice<SearchItemDto> getsortItems(int page, int size, String sortKeyword) {
        Pageable pageable;
        if (sortKeyword != null && !sortKeyword.isEmpty()) {
            Sort.Direction direction = sortKeyword.endsWith(",desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            String property = sortKeyword.replace(",desc", "").replace(",asc", ""); // 정렬 방향 키워드 제거
            Sort sort = Sort.by(direction, property);
            pageable = PageRequest.of(page - 1, size, sort);
        } else {
            pageable = PageRequest.of(page - 1, size);
        }
        return getItems(pageable);
    }


    private List<SearchItemDto> mapToSearchItemDtoList(Slice<Item> itemSlice) {
        return itemSlice.getContent().stream()
                .map(item -> {
                    SearchItemDto searchItemDto = new SearchItemDto();
                    searchItemDto.setItemName(item.getItemName());
                    searchItemDto.setItemPrice(item.getItemPrice());
                    searchItemDto.setImageUrl(item.getDetailedImage().getPath());
                    return searchItemDto;
                })
                .collect(Collectors.toList());
    }
}

