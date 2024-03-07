package com.ll.topcastingbe.domain.item.search.service;

import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import com.ll.topcastingbe.domain.item.search.dto.SearchItemDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemSearchService {
    private final ItemRepository itemRepository;

    public Slice<SearchItemDto> ItemsSearch(String keyword, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        List<Item> itemList = itemRepository.findListByItemNameIgnoreCase(keyword, pageable);
        List<SearchItemDto> itemDtoList = mapToSearchItemDtoList(itemList);

        boolean hasNext = false;
        if (itemList.size() > pageSize) {
            itemDtoList.remove(pageSize);
            hasNext = true;
        }

        return new SliceImpl<>(itemDtoList, pageable, hasNext);
    }

    public Slice<SearchItemDto> getItems(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        List<Item> itemList = itemRepository.findAllItems(pageable);
        List<SearchItemDto> itemDtoList = mapToSearchItemDtoList(itemList);

        boolean hasNext = false;
        if (itemList.size() > pageSize) {
            itemDtoList.remove(pageSize);
            hasNext = true;
        }

        return new SliceImpl<>(itemDtoList, pageable, hasNext);
    }

    public Slice<SearchItemDto> sortSearch(Pageable pageable) {
        return getItems(pageable);
    }


    private List<SearchItemDto> mapToSearchItemDtoList(List<Item> itemList) {
        return itemList.stream()
                .map(item -> {
                    SearchItemDto searchItemDto = new SearchItemDto();
                    searchItemDto.setItemName(item.getItemName());
                    searchItemDto.setItemPrice(item.getItemPrice());
                    searchItemDto.setImageUrl(item.getImage().getPath());
                    return searchItemDto;
                })
                .collect(Collectors.toList());
    }
}

