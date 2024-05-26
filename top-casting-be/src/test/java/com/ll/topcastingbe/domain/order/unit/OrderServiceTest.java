package com.ll.topcastingbe.domain.order.unit;

import static com.ll.topcastingbe.domain.order.entity.OrderStatus.REFUND;
import static com.ll.topcastingbe.domain.order.entity.OrderStatus.SHIPPING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ll.topcastingbe.domain.cart.entity.Cart;
import com.ll.topcastingbe.domain.cart.entity.CartItem;
import com.ll.topcastingbe.domain.cart.repository.CartItemRepository;
import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.order.dto.order.request.ModifyOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.service.order.OrderServiceImpl;
import com.ll.topcastingbe.domain.order.service.order_item.OrderItemService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private Orders mockOrder;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Orders order1;
    private Member member;
    private Item item1;
    private Option option1;
    private OrderItem orderItem1;
    private CartItem cartItem1;


    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .id(1L)
                .build();
        order1 = Orders.builder()
                .id(UUID.fromString("12345678-1234-1234-1234-123456781234"))
                .member(member)
                .orderStatus(SHIPPING)
                .customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1")
                .customerAddress("Test Customer Address1")
                .totalItemQuantity(1L)
                .totalItemPrice(11000L).build();

        item1 = Item.builder()
                .id(1L)
                .itemName("Test Item Name1")
                .itemPrice(BigDecimal.valueOf(1000))
                .build();

        option1 = Option.builder()
                .id(1L)
                .item(item1)
                .colorName("red")
                .stock(21)
                .build();

        orderItem1 = OrderItem.builder()
                .id(1L)
                .order(order1)
                .option(option1)
                .itemQuantity(11L)
                .totalPrice(11000L)
                .build();

        final Cart cart = Cart.builder()
                .member(member)
                .build();

        cartItem1 = CartItem.builder()
                .id(1L)
                .cart(cart)
                .option(option1)
                .itemQuantity(11)
                .build();

//        final AddOrderItemRequest addOrderItemRequest = AddOrderItemRequest.builder()
//                .cartItemId(cartItem1.getId())
//                .itemQuantity(9L)
//                .build();
//
//        final AddOrderRequest addOrderRequest = AddOrderRequest.builder()
//                .customerName("Test Customer Name")
//                .customerAddress("Test Customer Address")
//                .customerPhoneNumber("Test Customer PhoneNumber")
//                .totalItemPrice(99999L)
//                .totalItemQuantity(1L)
//                .addOrderItemRequest(List.of(addOrderItemRequest))
//                .build();

    }

//    @Test
//    @DisplayName("주문을 추가한다.")
//    public void addOrderTest() {
//        //given
//        final AddOrderItemRequest addOrderItemRequest = AddOrderItemRequest.builder()
//                .cartItemId(cartItem1.getId())
//                .itemQuantity(11L)
//                .build();
//
//        final AddOrderRequest addOrderRequest = AddOrderRequest.builder()
//                .customerName("Test Customer Name")
//                .customerAddress("Test Customer Address")
//                .customerPhoneNumber("Test Customer PhoneNumber")
//                .totalItemPrice(13500L)
//                .totalItemQuantity(1L)
//                .addOrderItemRequest(List.of(addOrderItemRequest))
//                .build();
//        given(cartItemRepository.findById(any())).willReturn(Optional.ofNullable(cartItem1));
//
//        final List<FindOrderItemResponse> findOrderItemResponses = List.of(FindOrderItemResponse.builder()
//                .itemImagePath("Test Image Path1")
//                .itemName("Test Item Name1")
//                .itemOption("Test Option1")
//                .itemQuantity(11L)
//                .totalPrice(13500L)
//                .build());
//        given(orderItemService.findAllByOrderId(any(), any())).willReturn(findOrderItemResponses);
//        //        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//        final Orders order = Orders.builder()
//                .id(UUID.fromString("12345678-1234-1234-1234-123456781234"))
//                .member(new Member())
//                .orderStatus(WAITING)
//                .customerName("Test Customer Name")
//                .customerAddress("Test Customer Address")
//                .customerPhoneNumber("Test Customer PhoneNumber")
//                .totalItemPrice(13500L)
//                .totalItemQuantity(1L)
//                .build();
//        given(orderRepository.save(any())).willReturn(order);
//
//        final List<OrderItem> orderItems = List.of(OrderItem.builder()
//                .id(1L)
//                .order(order)
//                .option(Option.builder().build())
//                .totalPrice(11000L)
//                .itemQuantity(11L)
//                .build());
//        given(orderItemService.findOrderItems(any())).willReturn(orderItems);
//        doNothing().when(orderItemService).addOrderItem(any(), any());
//
//        orderService.addOrder(addOrderRequest, member);
//    }

//    @Test
//    @DisplayName("주문 하나를 조회한다.")
//    public void findOrderTest() {
//        //given
//        given(orderRepository.findById(any())).willReturn(Optional.ofNullable(order1));
//        List<FindOrderItemResponse> findOrderItemResponses = List.of(FindOrderItemResponse.builder()
//                .itemImagePath("Test Image Path1")
//                .itemName("Test Item Name1")
//                .itemOption("Test Option1")
//                .itemQuantity(11L)
//                .totalPrice(11000L)
//                .build());
//
//        given(orderItemService.findAllByOrderId(any(), any())).willReturn(findOrderItemResponses);
//
//        //when
//        FindOrderResponse findOrderResponse = orderService.findOrder(order1.getId(), member);
//
//        //then
//        assertThat(findOrderResponse).extracting("orderId",
//                        "customerName",
//                        "customerPhoneNumber",
//                        "customerAddress",
//                        "orderStatus",
//                        "totalItemQuantity",
//                        "totalItemPrice")
//                .contains("12345678-1234-1234-1234-123456781234",
//                        "Test Customer Name1",
//                        "Test Customer PhoneNumber1",
//                        "Test Customer Address1",
//                        "SHIPPING",
//                        1L,
//                        11000L);
//    }

    @Test
    @DisplayName("특정 회원의 주문들을 조회한다.")
    public void findOrderListTest() {
        given(orderRepository.findAllByMember(member))
                .willReturn(List.of(order1));
        List<FindOrderResponse> findOrderResponses = orderService.findOrderList(member);
        assertThat(findOrderResponses).extracting("orderId",
                        "customerName",
                        "customerPhoneNumber",
                        "customerAddress",
                        "orderStatus",
                        "totalItemQuantity",
                        "totalItemPrice")
                .contains(
                        tuple(UUID.fromString("12345678-1234-1234-1234-123456781234"),
                                "Test Customer Name1",
                                "Test Customer PhoneNumber1",
                                "Test Customer Address1",
                                "SHIPPING",
                                1L,
                                11000L));
    }

    @Test
    @DisplayName("주문 상태를 환불(REFUND)로 변경한다.")
    @Transactional
    public void modifyOrderTest() {
        //given
        final ModifyOrderRequest modifyOrderRequest = ModifyOrderRequest.builder()
                .orderStatus("REFUND")
                .build();
        given(orderRepository.findById(any())).willReturn(Optional.ofNullable(order1));

        //when
        orderService.modifyOrder(order1.getId(), modifyOrderRequest, member);

        //then
        assertThat(order1.getOrderStatus()).isEqualTo(REFUND);
    }

//    @Test
//    @DisplayName("주문 상태를 환불(REFUND)로 변경한다.")
//    @Transactional
//    public void modifyOrderTest2() {
//        //given
//        final ModifyOrderRequest modifyOrderRequest = ModifyOrderRequest.builder()
//                .orderStatus("REFUND")
//                .build();
//        given(orderRepository.findById(any())).willReturn(Optional.ofNullable(mockOrder));
//
//        //when
//        orderService.modifyOrder(order1.getId(), modifyOrderRequest, member);
//
//        //then
//        assertThat(order1.getOrderStatus()).isEqualTo(REFUND);
//    }

    @Test
    @DisplayName("주문을 삭제한다.")
    public void removeOrder() {
        //given
        given(orderRepository.findById(any())).willReturn(Optional.ofNullable(mockOrder));
        doNothing().when(mockOrder).checkAuthorizedMember(any());
        doNothing().when(orderItemService).removeAllByOrder(any());
        doNothing().when(orderRepository).delete(any());

        //when
        orderService.removeOrder(order1.getId(), member);

        //then
        verify(orderItemService, times(1)).removeAllByOrder(any());
        verify(orderRepository, times(1)).delete(any());
        verify(mockOrder, times(1)).checkAuthorizedMember(any());
    }

}
