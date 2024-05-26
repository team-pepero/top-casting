package com.ll.topcastingbe.domain.order.integeration.service.order;

import static com.ll.topcastingbe.domain.order.entity.OrderStatus.DELIVERED;
import static com.ll.topcastingbe.domain.order.entity.OrderStatus.ORDER_EXCHANGE_REQUESTED;
import static com.ll.topcastingbe.domain.order.entity.OrderStatus.REFUND;
import static com.ll.topcastingbe.domain.order.entity.OrderStatus.WAITING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import com.ll.topcastingbe.domain.cart.entity.Cart;
import com.ll.topcastingbe.domain.cart.entity.CartItem;
import com.ll.topcastingbe.domain.cart.repository.CartItemRepository;
import com.ll.topcastingbe.domain.cart.repository.CartRepository;
import com.ll.topcastingbe.domain.category.MainCategoryRepository;
import com.ll.topcastingbe.domain.category.entity.MainCategory;
import com.ll.topcastingbe.domain.category.entity.SubCategory;
import com.ll.topcastingbe.domain.category.repository.SubCategoryRepository;
import com.ll.topcastingbe.domain.image.DetailedImageRepository;
import com.ll.topcastingbe.domain.image.ImageRepository;
import com.ll.topcastingbe.domain.image.entity.DetailedImage;
import com.ll.topcastingbe.domain.image.entity.Image;
import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import com.ll.topcastingbe.domain.option.entity.Option;
import com.ll.topcastingbe.domain.option.repository.OptionRepository;
import com.ll.topcastingbe.domain.order.dto.order.request.AddOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.ModifyOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.RequestCancelOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.response.AddOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order_item.request.AddOrderItemRequest;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.exception.BusinessException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import com.ll.topcastingbe.domain.order.service.order.OrderService;
import com.ll.topcastingbe.domain.order.service.order.OrderServiceImpl;
import com.ll.topcastingbe.domain.payment.repository.PaymentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class OrderServiceImplIt {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private DetailedImageRepository detailedImageRepository;

    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @InjectMocks
    private OrderServiceImpl orderService2;

    @Mock
    private OrderRepository orderRepository2;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private OrderItem orderItem1;
    private CartItem cartItem1;

    private Member member;
    private Orders order1;
    private Orders order2;
    private Orders order3;

    private Member admin;


    @BeforeEach
    public void setUpTest() {
        orderItemRepository.deleteAllInBatch();
        paymentRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        cartItemRepository.deleteAllInBatch();
        optionRepository.deleteAllInBatch();
        itemRepository.deleteAllInBatch();
        subCategoryRepository.deleteAllInBatch();
        mainCategoryRepository.deleteAllInBatch();
        detailedImageRepository.deleteAllInBatch();
        imageRepository.deleteAllInBatch();
        cartRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();

        em.createNativeQuery("ALTER TABLE order_item AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE payment AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_item AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE main_category AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE sub_category AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE detailed_image AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE image AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE member AUTO_INCREMENT 1").executeUpdate();

        final DetailedImage detailedImage1 = DetailedImage.builder()
                .path("Test Path1")
                .imageName("Test Detailed Image Name1")
                .originFileName("Test Origin File Name1")
                .build();
        final DetailedImage detailedImage2 = DetailedImage.builder()
                .path("Test Path2")
                .imageName("Test Detailed Image Name2")
                .originFileName("Test Origin File Name2")
                .build();
        final DetailedImage detailedImage3 = DetailedImage.builder()
                .path("Test Path3")
                .imageName("Test Detailed Image Name3")
                .originFileName("Test Origin File Name3")
                .build();
        detailedImageRepository.saveAll(List.of(detailedImage1, detailedImage2, detailedImage3));

        final Image image1 = Image.builder()

                .path("Test Path1")
                .imageName("Test Image Name1")
                .originFileName("Test Original File Name1")
                .build();
        final Image image2 = Image.builder()
                .path("Test Path2")
                .imageName("Test Image Name2")
                .originFileName("Test Original File Name2")
                .build();
        final Image image3 = Image.builder()
                .path("Test Path3")
                .imageName("Test Image Name3")
                .originFileName("Test Original File Name3")
                .build();
        imageRepository.saveAll(List.of(image1, image2, image3));

        final MainCategory mainCategory1 = MainCategory.builder()
                .categoryName("Test Main Category Name")
                .build();
        final MainCategory mainCategory2 = MainCategory.builder()
                .categoryName("Test Main Category Name")
                .build();
        final MainCategory mainCategory3 = MainCategory.builder()
                .categoryName("Test Main Category Name")
                .build();
        mainCategoryRepository.saveAll(List.of(mainCategory1, mainCategory2, mainCategory3));

        final SubCategory subCategory1 = SubCategory.builder()
                .parentCategory(mainCategory1)
                .subcategoryName("Test Sub Category Name1")
                .build();
        final SubCategory subCategory2 = SubCategory.builder()
                .parentCategory(mainCategory2)
                .subcategoryName("Test Sub Category Name2")
                .build();
        final SubCategory subCategory3 = SubCategory.builder()
                .parentCategory(mainCategory2)
                .subcategoryName("Test Sub Category Name3")
                .build();
        final SubCategory subCategory4 = SubCategory.builder()
                .parentCategory(mainCategory3)
                .subcategoryName("Test Sub Category Name4")
                .build();
        final SubCategory subCategory5 = SubCategory.builder()
                .parentCategory(mainCategory3)
                .subcategoryName("Test Sub Category Name5")
                .build();
        final SubCategory subCategory6 = SubCategory.builder()
                .parentCategory(mainCategory3)
                .subcategoryName("Test Sub Category Name6")
                .build();
        subCategoryRepository.saveAll(
                List.of(subCategory1, subCategory2, subCategory3, subCategory4, subCategory5, subCategory6));

        final Item item1 = Item.builder()
                .itemName("Test Item Name1")
                .itemPrice(BigDecimal.valueOf(1000))
                .image(image1)
                .detailedImage(detailedImage1)
                .mainCategory(mainCategory1)
                .subCategory(subCategory1)
                .build();
        final Item item2 = Item.builder()
                .itemName("Test Item Name2")
                .itemPrice(BigDecimal.valueOf(2000))
                .image(image2)
                .detailedImage(detailedImage2)
                .mainCategory(mainCategory2)
                .subCategory(subCategory2)
                .build();
        final Item item3 = Item.builder()
                .itemName("Test Item Name3")
                .itemPrice(BigDecimal.valueOf(3000))
                .image(image3)
                .detailedImage(detailedImage3)
                .mainCategory(mainCategory3)
                .subCategory(subCategory3)
                .build();
        itemRepository.saveAll(List.of(item1, item2, item3));

        member = Member.builder().username("username").password(passwordEncoder.encode("1"))
                .nickname("nickname")
                .email("kissshot.heartunderblade1104@gmail.com").roles("ROLE_USER").build();

        admin = Member.builder().username("admin2").password(passwordEncoder.encode("1"))
                .nickname("adminName")
                .email("adminEmail").roles("ROLE_ADMIN").build();

        memberRepository.saveAll(List.of(member, admin));

        final Cart cart = Cart.builder()
                .member(member)
                .build();
        cartRepository.save(cart);

        final Option option1 = Option.builder().item(item1).colorName("red").stock(21).build();
        final Option option2 = Option.builder().item(item2).colorName("blue").stock(22).build();
        final Option option3 = Option.builder().item(item2).colorName("green").stock(23).build();
        final Option option4 = Option.builder().item(item3).colorName("red").stock(24).build();
        final Option option5 = Option.builder().item(item3).colorName("blue").stock(25).build();
        final Option option6 = Option.builder().item(item3).colorName("green").stock(26).build();
        optionRepository.saveAll(List.of(option1, option2, option3, option4, option5, option6));

        cartItem1 = CartItem.builder().cart(cart).option(option1).itemQuantity(11).build();
        final CartItem cartItem2 = CartItem.builder().cart(cart).option(option2).itemQuantity(12).build();
        final CartItem cartItem3 = CartItem.builder().cart(cart).option(option2).itemQuantity(13).build();
        final CartItem cartItem4 = CartItem.builder().cart(cart).option(option3).itemQuantity(14).build();
        final CartItem cartItem5 = CartItem.builder().cart(cart).option(option3).itemQuantity(15).build();
        final CartItem cartItem6 = CartItem.builder().cart(cart).option(option3).itemQuantity(16).build();
        cartItemRepository.saveAll(List.of(cartItem1, cartItem2, cartItem3, cartItem4, cartItem5, cartItem6));

        order1 = Orders.builder()
                .member(member)
                .orderStatus(REFUND)
                .customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1")
                .customerAddress("Test Customer Address1")
                .totalItemQuantity(1L)
                .totalItemPrice(11000L).build();

        order2 = Orders.builder()
                .member(member)
                .orderStatus(ORDER_EXCHANGE_REQUESTED)
                .customerName("Test Customer Name2")
                .customerPhoneNumber("Test Customer PhoneNumber2")
                .customerAddress("Test Customer Address2")
                .totalItemQuantity(2L)
                .totalItemPrice(50000L).build();

        order3 = Orders.builder()
                .member(member)
                .orderStatus(ORDER_EXCHANGE_REQUESTED)
                .customerName("Test Customer Name3")
                .customerPhoneNumber("Test Customer PhoneNumber3")
                .customerAddress("Test Customer Address3")
                .totalItemQuantity(3L)
                .totalItemPrice(135000L).build();
        orderRepository.saveAll(List.of(order1, order2, order3));

        final OrderItem orderItem1 = OrderItem.builder().order(order1).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        //50000
        final OrderItem orderItem2 = OrderItem.builder().order(order2).option(option2).itemQuantity(12L)
                .totalPrice(24000L).build();
        final OrderItem orderItem3 = OrderItem.builder().order(order2).option(option3).itemQuantity(13L)
                .totalPrice(26000L).build();
        //135000
        final OrderItem orderItem4 = OrderItem.builder().order(order3).option(option4).itemQuantity(14L)
                .totalPrice(42000L).build();
        final OrderItem orderItem5 = OrderItem.builder().order(order3).option(option5).itemQuantity(15L)
                .totalPrice(45000L).build();
        final OrderItem orderItem6 = OrderItem.builder().order(order3).option(option6).itemQuantity(16L)
                .totalPrice(48000L).build();
        orderItemRepository.saveAll(List.of(orderItem1, orderItem2, orderItem3, orderItem4, orderItem5, orderItem6));
    }

    @DisplayName("Order를 추가한다.")
    @Test
    public void saveOrderTest() {
        //given
        final AddOrderItemRequest addOrderItemRequest = AddOrderItemRequest.builder().cartItemId(cartItem1.getId())
                .itemQuantity(9L).build();

        final AddOrderRequest saveOrdersRequest = AddOrderRequest.builder()
                .customerName("Test Customer Name")
                .customerAddress("Test Customer Address")
                .customerPhoneNumber("Test Customer PhoneNumber")
                .totalItemPrice(11500L)
                .totalItemQuantity(1L)
                .addOrderItemRequest(List.of(addOrderItemRequest))
                .build();

        //when
        AddOrderResponse addOrderResponse = orderService.addOrder(saveOrdersRequest, member);
        Orders order = orderService.findByOrderId(addOrderResponse.orderId());

        //then
        assertThat(order).extracting("orderStatus",
                        "customerName",
                        "customerAddress",
                        "customerPhoneNumber",
                        "totalItemQuantity",
                        "totalItemPrice")
                .contains(WAITING,
                        "Test Customer Name",
                        "Test Customer Address",
                        "Test Customer Address",
                        11500L,
                        1L);
    }

    @DisplayName("Order를 추가 시 Order의 총 가격과 아이템끼리의 가격이 다르다면 예외가 발생한다.")
    @Test
    public void notMatchTotalItemPriceTest() {
        //given
        final AddOrderItemRequest addOrderItemRequest = AddOrderItemRequest.builder()
                .cartItemId(cartItem1.getId())
                .itemQuantity(9L)
                .build();

        final AddOrderRequest saveOrdersRequest = AddOrderRequest.builder().customerName("Test Customer Name")
                .customerAddress("Test Customer Address").customerPhoneNumber("Test Customer PhoneNumber")
                .totalItemPrice(99999L).totalItemQuantity(1L).addOrderItemRequest(List.of(addOrderItemRequest)).build();

        //when && then
        assertThatThrownBy(() -> orderService.addOrder(saveOrdersRequest, member))
                .isInstanceOf(BusinessException.class)
                .hasMessage("허용되지 않는 입력값입니다.");

    }


    @DisplayName("주문을 조회한다.")
    @Test
    public void findOrderTest() {
        //given
        //when
        final FindOrderResponse findOrderResponse = orderService.findOrder(order1.getId(), member);

        //then
        //todo 날짜 테스트 코드를 아직 안짬 이러때는 어떻게 해야하는지 고민
        assertThat(findOrderResponse).extracting("orderId",
                        "customerName",
                        "customerPhoneNumber",
                        "customerAddress",
                        "orderStatus",
                        "totalItemQuantity",
                        "totalItemPrice")
                .contains(order1.getId(),
                        "Test Customer Name1",
                        "Test Customer PhoneNumber1",
                        "Test Customer Address1",
                        "REFUND",
                        1L,
                        11000L);
    }

    @DisplayName("특정 회원과 관련된 모든 주문을 조회한다.")
    @Test
    public void findOrderListTest() {
        //given

        //when
        final List<FindOrderResponse> findOrderResponses = orderService.findOrderList(member);

        //then
        assertThat(findOrderResponses.size()).isEqualTo(3);

        assertThat(findOrderResponses).extracting("orderId",
                        "customerName",
                        "customerPhoneNumber",
                        "customerAddress",
                        "orderStatus",
                        "totalItemQuantity",
                        "totalItemPrice")
                .contains(
                        tuple(order1.getId(),
                                "Test Customer Name1",
                                "Test Customer PhoneNumber1",
                                "Test Customer Address1",
                                "REFUND",
                                1L,
                                11000L),
                        tuple(order2.getId(),
                                "Test Customer Name2",
                                "Test Customer PhoneNumber2",
                                "Test Customer Address2",
                                "ORDER_EXCHANGE_REQUESTED",
                                2L,
                                50000L),
                        tuple(order3.getId(),
                                "Test Customer Name3",
                                "Test Customer PhoneNumber3",
                                "Test Customer Address3",
                                "ORDER_EXCHANGE_REQUESTED",
                                3L,
                                135000L));
    }

    @DisplayName("주문 상태를 변경한다.")
    @Test
    public void modifyOrdersTest() {
        //given
        ModifyOrderRequest updateOrdersRequest = ModifyOrderRequest.builder()
                .orderStatus("DELIVERED")
                .build();
        // when
        orderService.modifyOrder(order1.getId(), updateOrdersRequest, member);

        // then
        assertThat(order1.getOrderStatus()).isEqualTo(DELIVERED);
    }

    @DisplayName("주문을 삭제한다.")
    @Test
    public void deleteOrderTest() {
        //given

        //when
        orderService.removeOrder(order1.getId(), member);

        //then
        assertThatThrownBy(() -> orderRepository.findById(order1.getId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.ENTITY_NOT_FOUND)))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Entity를 찾을 수 없습니다.");
    }


    @DisplayName("사용자가 환불을 요청한다.")
    @Test
    void requestCancelOrder() throws Exception {

        //given
        RequestCancelOrderRequest requestCancelOrderRequest = RequestCancelOrderRequest.builder()
                .orderStatus("ORDER_EXCHANGE_REQUESTED")
                .build();

        //when
        orderService.requestCancelOrder(order2.getId(), requestCancelOrderRequest, member);

        //then
        assertThat(order2).extracting("orderStatus").isEqualTo(ORDER_EXCHANGE_REQUESTED);
    }

    @DisplayName("사용자가 환불을 요청한다.")
    @Test
    void requestCancelOrder2() throws Exception {

        //given
        RequestCancelOrderRequest requestCancelOrderRequest = RequestCancelOrderRequest.builder()
                .orderStatus("ORDER_EXCHANGE_REQUESTED")
                .build();

        //when
        orderService.requestCancelOrder(order2.getId(), requestCancelOrderRequest, admin);

        //then
        assertThat(order2).extracting("orderStatus").isEqualTo(ORDER_EXCHANGE_REQUESTED);
    }
}
