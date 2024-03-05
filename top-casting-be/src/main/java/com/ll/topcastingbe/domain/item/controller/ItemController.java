package com.ll.topcastingbe.domain.item.controller;

import com.ll.topcaastingbe.domain.item.model.Item;
import com.ll.topcaastingbe.domain.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {

        this.itemService = itemService;
    }

    @GetMapping
    public String listItems(Model model) {

        List<Item> items = itemService.findAllItems();

        model.addAttribute("items",items);

        return "items";

    }

    @GetMapping("/{id}")
    public String getItemDetail(@PathVariable Long id, Model model) {

        Item item = itemService.findItemById(id)
                .orElseThrow(() ->
                        .orElse
        new RuntimeException("Item not found with id " + id));

        model.addAttribute("item", item);


        return "item-detail";
    }
    @PostMapping
    public String addItem(@ModelAttribute Item item) {

        itemService.saveItem(item);

        return "redirect:/items";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {

        itemService.deleteItem(id);

        return "redirect:/items";
    }


}





