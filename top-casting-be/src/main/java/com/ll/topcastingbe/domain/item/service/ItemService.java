package com.ll.topcastingbe.domain.item.service;

import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    // 아이템 등록
    public void saveItem(Item item , MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        item.setFilename(fileName);
        item.setFilepath("/files/" + fileName);

        itemRepository.save(item);

    }

    //아이템 리스트 불러오기
    public List<Item> allItemView(){
        return itemRepository.findAll();
    }

    //아이템 개별로 불러오기
    public Item itemView(long id){

        return itemRepository.findById(id).get();
    }

    //아이템 수정
    @Transactional
    public void itemModify(Item item, long id , MultipartFile file) {

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);

        try{
            file.transferTo(saveFile);
        } catch (IOException e){
            throw new RuntimeException("Failed to save file" + fileName, e);

        }

        Item before = itemRepository.findItemById(id);
        before.setFilename(fileName);
        before.setFilepath("/files/" + fileName);
        before.setItemName(item.getItemName());
        before.setItemPrice(item.getItemPrice());

        itemRepository.save(before);

    }

    //아이템 삭제
    @Transactional
    public void itemDelete(long id){
        itemRepository.deleteById(id);
    }






}

