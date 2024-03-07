package com.ll.topcastingbe.domain.cart.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.ll.topcastingbe.domain.item.domain.Item;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private CartService cartService;

    /*@Test
    @DisplayName("장바구니가 존재하지 않으면 생성한다.")
    void checkAndCreateCartTest() {

        Member newMember = Member.builder()
                .username("test")
                .password("1234")
                .nickname("test")
                .email("test@gmail.com")
                .build();
        Member savedMember = memberRepository.save(newMember);

        Option option = optionRepository.findById(1L).get();

        CartService mockCartService = mock(CartService.class);
        mockCartService.modifyCartItem(savedMember.getId(), option.getItem().getId(), option.getId(), 10);
        verify(mockCartService).createCart(savedMember.getId());
    }*/
}
