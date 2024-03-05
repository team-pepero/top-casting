package com.ll.topcastingbe.domain.item.service;

import com.yourmall.model.Item;
import com.yourmall.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {

        this.itemRepository = itemRepository;
    }

    public List<Item> findAllItems() {

        return itemRepository.findAll();
    }
    public Optional<Item> findItemById(Long id) {

        return itemRepository.findById(id);
    }
    public Item saveItem(Item item) {

        return itemRepository.save(item);
    }
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

}