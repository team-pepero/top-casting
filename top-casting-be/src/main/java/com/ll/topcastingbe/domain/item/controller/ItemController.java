package com.ll.topcastingbe.domain.item.controller;


import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    // 메인 페이지 (로그인 안 한 유저)
    @GetMapping("/")
    public String mainPageNoneLogin(Model model) {
        // 로그인을 안 한 경우
        List<Item> items = itemService.allItemView();

        model.addAttribute("items", items);
        return "/main";
    }


//    // 메인 페이지 (로그인 유저)
//    @GetMapping("/main")
//    public String mainPage(Model model, PrincipalDetails principalDetails) {
//        List<Item> items = itemService.allItemView();
//        User loginUser = userPageService.findUser(principalDetails.getUser().getId());
//
//        if(principalDetails.getUser().getRole().equals("ROLE_USER")) {
//            // 일반 유저일 경우
//            int cartCount = 0;
//            Cart userCart = cartFinderService.findCart(loginUser.getId());
//            List<Cart_item> userItems = cartFinderService.findUserCart_items(userCart);
//            cartCount = userItems.size();
//            model.addAttribute("cartCount", cartCount);
//        }
//
//        model.addAttribute("items", items);
//        model.addAttribute("user", loginUser);
//
//        return "mainLoginPage";
//    }


    // 아이템 상세 페이지
    @GetMapping("/item/{id}")
    public String itemView(@PathVariable("id") long id, Model model) {
        model.addAttribute("item", itemService.itemView(id));
        return "/item";



        //
//
//        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//            // 어드민
//            model.addAttribute("user", principalDetails.getUser());
//            model.addAttribute("item", itemService.itemView(id));
//            return "/adm/item";
//        } else {
//            // 일반 회원
//            int cartCount = 0;
//            User loginUser = userPageService.findUser(principalDetails.getUser().getId());
//            Cart userCart = cartFinderService.findCart(loginUser.getId());
//            List<Cart_item> userItems = cartFinderService.findUserCart_items(userCart);
//            cartCount = userItems.size();
//
//            model.addAttribute("cartCount", cartCount);
//            model.addAttribute("user", principalDetails.getUser());
//            model.addAttribute("item", itemService.itemView(id));
//            return "/user/item";
    }



//    // 아이템 업로드 페이지
//    @GetMapping("/item/upload")
//    public String itemUpload(PrincipalDetails principalDetails) {
//        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//            // 어드민
//            return "/adm/itemUpload";
//        } else {
//            // 어드민이 아니면 거절 당해서 main으로 되돌아감
//            return "redirect:/main";
//        }
//    }


//    // 아이템 업로드 진행
//    @PostMapping("/item/upload/process")
//    public String itemUploadProcess(Item item, MultipartFile file, PrincipalDetails principalDetails) throws Exception {
//        System.out.println("filename == " + file);
//        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//            // 어드민
//            itemService.saveItem(item, file);
//            return "redirect:/main";
//        } else {
//            // 어드민이 아니면 거절 당해서 main으로 되돌아감
//            return "redirect:/main";
//        }
//
//    }


//    // 아이템 수정 페이지
//    @GetMapping("/item/{id}/modify")
//    public String itemModify(@PathVariable("id") Integer id, Model model, PrincipalDetails principalDetails) {
//        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//            // 어드민
//            User user = itemService.itemView(id).getUser();
//            model.addAttribute("item", itemService.itemView(id));
//            return "/adm/itemModify";
//
//        } else {
//            // 어드민이 아니면 거절 당해서 main으로 되돌아감
//            return "redirect:/main";
//        }
//
//    }


//    // 아이템 수정 처리
//    @PostMapping("/item/{id}/modify/process")
//    public String itemModifyProcess(Item item, MultipartFile file, @PathVariable("id") Integer id, PrincipalDetails principalDetails) throws Exception {
//        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//            // 어드민
//            User user = itemService.itemView(id).getUser();
//
//            itemService.itemModify(item, id, file);
//            return "redirect:/main";
//
//        } else {
//            // 어드민이 아니면 거절 당해서 main으로 되돌아감
//            return "redirect:/main";
//        }
//    }

//    // 아이템 삭제
//    @GetMapping("/item/{id}/delete")
//    public String itemDelete(@PathVariable("id") Integer id, PrincipalDetails principalDetails) {
//        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
//            // 어드민
//            User user = itemService.itemView(id).getUser();
//
//            itemService.itemDelete(id);
//            return "redirect:/main";
//
//        } else {
//            // 어드민이 아니면 거절 당해서 main으로 되돌아감
//            return "redirect:/main";
//        }
//
//    }
}





