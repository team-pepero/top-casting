package com.ll.topcastingbe.cart.repository;

import com.ll.topcastingbe.domain.cart.repository.CartItemRepository;
import com.ll.topcastingbe.domain.cart.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartItemRepositoryTest {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CartRepository cartRepository;

	@Test
	void findByMemberIdTest(){
		Long memberId = 1L;
		cartItemRepository.findByMemberId(memberId);
	}
}
