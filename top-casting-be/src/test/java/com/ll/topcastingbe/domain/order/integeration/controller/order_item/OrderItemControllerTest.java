package com.ll.topcastingbe.domain.order.integeration.controller.order_item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.topcastingbe.domain.cart.repository.CartItemRepository;
import com.ll.topcastingbe.domain.image.ImageRepository;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderItemControllerTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void init() {
        orderItemRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        cartItemRepository.deleteAllInBatch();
        optionRepository.deleteAllInBatch();
        itemRepository.deleteAllInBatch();
        imageRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

//    @DisplayName("주문과 관련된 모든 아이템을 조회한다.")
//    @Test
//    void orderItemFindAllTest() throws Exception {
//
//        //given
//        //todo image를 아직 설정을 하지 못하여 임의로 넣을 예정
//        final Image image = Image.builder()
//                .path("Test Image Path")
//                .build();
//        imageRepository.save(image);
//
//        final Item item = Item.builder().image(image).itemName("Test Item Name").itemPrice(BigDecimal.valueOf(1000))
//                .build();
//        itemRepository.save(item);
//
//        final Option option1 = Option.builder().colorName("blue").item(item).stock(9).build();
//
//        final Option option2 = Option.builder().colorName("red").item(item).stock(9).build();
//
//        final Option option3 = Option.builder().colorName("green").item(item).stock(9).build();
//
//        optionRepository.saveAll(List.of(option1, option2, option3));
//
//        final Member member = Member.builder().username("username").password("password").nickname("nickname")
//                .email("email").build();
//        memberRepository.save(member);
//
//        final Orders order = Orders.builder().member(member).orderStatus(SHIPPING).customerName("Test Name")
//                .customerPhoneNumber("01012345678").customerAddress("Test Address").totalItemQuantity(9L)
//                .totalItemPrice(90000L).build();
//
//        final Orders order2 = Orders.builder().member(member).orderStatus(SHIPPING).customerName("Test Name")
//                .customerPhoneNumber("01012345678").customerAddress("Test Address").totalItemQuantity(9L)
//                .totalItemPrice(90000L).build();
//
//        final Orders order3 = Orders.builder().member(member).orderStatus(SHIPPING).customerName("Test Name")
//                .customerPhoneNumber("01012345678").customerAddress("Test Address").totalItemQuantity(9L)
//                .totalItemPrice(90000L).build();
//        orderRepository.saveAll(List.of(order, order2, order3));
//
//        final OrderItem orderItem1 = OrderItem.builder().order(order).option(option1).itemQuantity(7L).totalPrice(7000L)
//                .build();
//        final OrderItem orderItem2 = OrderItem.builder().order(order).option(option2).itemQuantity(8L).totalPrice(8000L)
//                .build();
//        final OrderItem orderItem3 = OrderItem.builder().order(order).option(option3).itemQuantity(9L).totalPrice(9000L)
//                .build();
//        orderItemRepository.saveAll(List.of(orderItem1, orderItem2, orderItem3));
//
//        List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);
//
//        final UserDetails user = userDetailsService.loadUserByUsername("username");
//
//        mock.perform(get("/api/v1/order-item/{orderId}", order.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpectAll(
//                        jsonPath("$[*].itemImagePath", everyItem(matchesPattern("Test Image Path"))),
//                        jsonPath("$[*].itemName", everyItem(matchesPattern("Test Item Name"))),
//                        jsonPath("$[*].itemOption", everyItem(matchesPattern("green|red|blue"))),
//                        jsonPath("$[*].itemQuantity", everyItem(oneOf(7, 8, 9))),
//                        jsonPath("$[*].totalPrice", everyItem(oneOf(7000, 8000, 9000)))
//                );
//    }
}
