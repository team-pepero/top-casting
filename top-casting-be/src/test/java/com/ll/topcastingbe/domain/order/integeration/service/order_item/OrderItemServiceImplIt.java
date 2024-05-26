package com.ll.topcastingbe.domain.order.integeration.service.order_item;


import static com.ll.topcastingbe.domain.order.entity.OrderStatus.SHIPPING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.ll.topcastingbe.domain.cart.entity.CartItem;
import com.ll.topcastingbe.domain.cart.repository.CartItemRepository;
import com.ll.topcastingbe.domain.cart.repository.CartRepository;
import com.ll.topcastingbe.domain.image.ImageRepository;
import com.ll.topcastingbe.domain.image.entity.Image;
import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import com.ll.topcastingbe.domain.order.dto.order_item.request.AddOrderItemRequest;
import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import com.ll.topcastingbe.domain.order.service.order_item.OrderItemService;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderItemServiceImplIt {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        orderItemRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        cartItemRepository.deleteAllInBatch();
        optionRepository.deleteAllInBatch();
        itemRepository.deleteAllInBatch();
        imageRepository.deleteAllInBatch();
        cartRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();

        em.createNativeQuery("ALTER TABLE ORDER_ITEM AUTO_INCREMENT 1").executeUpdate();
//        em.createNativeQuery("ALTER TABLE ORDER_ITEM AUTO_INCREMENT 1").executeUpdate();
    }

    @Test
    @DisplayName("OrderItem를 추가한다.")
    public void addOrderItem() {
        //given
        final Image image = Image.builder()
                .path("Test Image Path")
                .build();
        imageRepository.save(image);

        final Item item = Item.builder()
                .image(image)
                .itemPrice(BigDecimal.valueOf(1000))
                .build();
        itemRepository.save(item);

        final Option option1 = Option.builder().item(item).stock(9).build();
        final Option option2 = Option.builder().item(item).stock(9).build();
        final Option option3 = Option.builder().item(item).stock(9).build();
        optionRepository.saveAll(List.of(option1, option2, option3));

        final CartItem cartItem1 = CartItem.builder().option(option1).itemQuantity(7).build();
        final CartItem cartItem2 = CartItem.builder().option(option2).itemQuantity(8).build();
        final CartItem cartItem3 = CartItem.builder().option(option3).itemQuantity(9).build();
        cartItemRepository.saveAll(List.of(cartItem1, cartItem2, cartItem3));

        final Member member = Member.builder()
                .username("username")
                .nickname("nickname")
                .email("email")
                .build();
        memberRepository.save(member);

        final Orders order = Orders.builder()
                .member(member)
                .orderStatus(SHIPPING)
                .customerName("Test Name")
                .customerPhoneNumber("01012345678")
                .customerAddress("Test Address")
                .totalItemQuantity(9L)
                .totalItemPrice(90000L)
                .build();
        orderRepository.save(order);

        final AddOrderItemRequest addOrderItemRequest = AddOrderItemRequest.builder()
                .cartItemId(cartItem1.getId())
                .itemQuantity(9L)
                .build();

        //when
        orderItemService.addOrderItem(order, addOrderItemRequest);

        //then
        final OrderItem orderItem = orderItemRepository.findById(1L).get();
        assertThat(orderItem)
                .extracting("order",
                        "itemQuantity",
                        "totalPrice")
                .contains(order,
                        9L,
                        9000L);
    }

    @Test
    @DisplayName("OrderItem를 조회한다.")
    public void findAllByOrderId() {
        //given

        final Image image = Image.builder()
                .path("Test Image Path")
                .build();
        imageRepository.save(image);

        final Item item = Item.builder()
                .image(image)
                .itemName("Test Item Name")
                .itemPrice(BigDecimal.valueOf(1000))
                .build();
        itemRepository.save(item);

        final Option option1 = Option.builder()
                .item(item)
                .stock(9)
                .build();

        final Option option2 = Option.builder()
                .item(item)
                .stock(9)
                .build();

        final Option option3 = Option.builder()
                .item(item)
                .stock(9)
                .build();

        optionRepository.saveAll(List.of(option1, option2, option3));

        final Member member = Member.builder()
                .username("username")
                .nickname("nickname")
                .email("email")
                .build();
        memberRepository.save(member);

        final Orders order = Orders.builder()
                .member(member)
                .orderStatus(SHIPPING)
                .customerName("Test Name")
                .customerPhoneNumber("01012345678")
                .customerAddress("Test Address")
                .totalItemQuantity(9L)
                .totalItemPrice(90000L)
                .build();
        orderRepository.save(order);

        final OrderItem orderItem1 = OrderItem.builder()
                .order(order)
                .option(option1)
                .itemQuantity(7L)
                .totalPrice(7000L)
                .build();
        final OrderItem orderItem2 = OrderItem.builder()
                .order(order)
                .option(option2)
                .itemQuantity(8L)
                .totalPrice(8000L)
                .build();
        final OrderItem orderItem3 = OrderItem.builder()
                .order(order)
                .option(option3)
                .itemQuantity(9L)
                .totalPrice(9000L)
                .build();
        orderItemRepository.saveAll(List.of(orderItem1, orderItem2, orderItem3));

        //when
        final List<FindOrderItemResponse> findOrderItemResponses =
                orderItemService.findAllByOrderId(order.getId(), member);

        //then
        assertThat(findOrderItemResponses)
                .extracting(
                        "itemName",
                        "itemQuantity",
                        "totalPrice")
                .contains(tuple(
                                "Test Item Name",
                                7L,
                                7000L),
                        tuple(
                                "Test Item Name",
                                8L,
                                8000L),
                        tuple(
                                "Test Item Name",
                                9L,
                                9000L)
                );
    }


    @Test
    @DisplayName("주문과 관련된 모든 OrderItem을 삭제한다.")
    public void deleteByOrder() {
        //given
        final Item item = Item.builder()
                .itemName("Test Item Name")
                .itemPrice(BigDecimal.valueOf(1000))
                .build();
        itemRepository.save(item);

        final Option option1 = Option.builder()
                .item(item)
                .stock(9)
                .build();

        final Option option2 = Option.builder()
                .item(item)
                .stock(9)
                .build();

        final Option option3 = Option.builder()
                .item(item)
                .stock(9)
                .build();

        optionRepository.saveAll(List.of(option1, option2, option3));

        final Member member = Member.builder()
                .username("username")
                .nickname("nickname")
                .email("email")
                .build();
        memberRepository.save(member);

        final Orders order = Orders.builder()
                .member(member)
                .orderStatus(SHIPPING)
                .customerName("Test Name")
                .customerPhoneNumber("01012345678")
                .customerAddress("Test Address")
                .totalItemQuantity(9L)
                .totalItemPrice(90000L)
                .build();
        orderRepository.save(order);

        final OrderItem orderItem1 = OrderItem.builder()
                .order(order)
                .option(option1)
                .itemQuantity(7L)
                .totalPrice(7000L)
                .build();
        final OrderItem orderItem2 = OrderItem.builder()
                .order(order)
                .option(option2)
                .itemQuantity(8L)
                .totalPrice(8000L)
                .build();
        final OrderItem orderItem3 = OrderItem.builder()
                .order(order)
                .option(option3)
                .itemQuantity(9L)
                .totalPrice(9000L)
                .build();
        orderItemRepository.saveAll(List.of(orderItem1, orderItem2, orderItem3));

        //when
        orderItemService.removeAllByOrder(order);

        //then
        List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order);
        assertThat(orderItems.size()).isEqualTo(0);
    }
}
