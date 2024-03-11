package com.ll.topcastingbe.domain.item.service;

import com.ll.topcastingbe.domain.item.dto.ItemDetailResponseDto;
import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.item.exception.ItemNotExistException;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import com.ll.topcastingbe.domain.option.dto.ItemDetailOptionResponseDto;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    public ItemDetailResponseDto findItem(Long itemId) {
        Item item = itemRepository.findByItemIdWithImageAndOption(itemId)
                .orElseThrow(() -> new ItemNotExistException());

        List<ItemDetailOptionResponseDto> optionDtos = optionRepository.findByItemId(item.getId())
                .stream()
                .map(ItemDetailOptionResponseDto::toDto)
                .toList();

        return ItemDetailResponseDto.toDto(item, optionDtos);

    }
}
