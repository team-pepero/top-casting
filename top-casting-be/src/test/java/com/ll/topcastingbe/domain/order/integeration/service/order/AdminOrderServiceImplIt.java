package com.ll.topcastingbe.domain.order.integeration.service.order;

import static com.ll.topcastingbe.domain.order.entity.OrderStatus.ORDER_EXCHANGE_REQUESTED;
import static com.ll.topcastingbe.domain.order.entity.OrderStatus.REFUND;
import static org.assertj.core.api.Assertions.assertThat;
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
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderForAdminResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.dto.order_item.response.FindOrderItemResponse;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import com.ll.topcastingbe.domain.order.service.order.AdminOrderServiceImpl;
import com.ll.topcastingbe.domain.payment.entity.Payment;
import com.ll.topcastingbe.domain.payment.repository.PaymentRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AdminOrderServiceImplIt {

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
    private AdminOrderServiceImpl adminOrderService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Orders order1;
    private Orders order2;
    private Orders order3;

    @BeforeEach
    public void init() {
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

        final Member member = Member.builder().username("username").password(passwordEncoder.encode("1"))
                .nickname("nickname")
                .email("kissshot.heartunderblade1104@gmail.com").roles("ROLE_USER").build();

        final Member admin = Member.builder().username("admin2").password(passwordEncoder.encode("1"))
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

        final CartItem cartItem1 = CartItem.builder().cart(cart).option(option1).itemQuantity(11).build();
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


    @DisplayName("관리자가 환불 요청중인 주문을 모두 조회한다.")
    @Test
    void requestCancelOrderTest() throws Exception {

        //given
        //when
        List<FindOrderResponse> findOrderResponses = adminOrderService.findOrderListForAdmin();

        //then
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

        assertThat(findOrderResponses.get(0).findOrderItemResponses()).extracting("itemImagePath",
                        "itemName",
                        "itemOption",
                        "itemQuantity",
                        "totalPrice")
                .contains(tuple("Test Path1",
                        "Test Item Name1",
                        "red",
                        11L,
                        11000L));

        assertThat(findOrderResponses.get(1).findOrderItemResponses())
                .extracting("itemImagePath",
                        "itemName",
                        "itemOption",
                        "itemQuantity",
                        "totalPrice")
                .contains(
                        tuple("Test Path2",
                                "Test Item Name2",
                                "blue",
                                12L,
                                24000L),
                        tuple("Test Path2",
                                "Test Item Name2",
                                "green",
                                13L,
                                26000L));

        assertThat(findOrderResponses.get(2).findOrderItemResponses())
                .extracting("itemImagePath",
                        "itemName",
                        "itemOption",
                        "itemQuantity",
                        "totalPrice")
                .contains(tuple("Test Path3",
                                "Test Item Name3",
                                "red",
                                14L,
                                42000L),
                        tuple("Test Path3",
                                "Test Item Name3",
                                "blue",
                                15L,
                                45000L),
                        tuple("Test Path3",
                                "Test Item Name3",
                                "green",
                                16L,
                                48000L));

    }

    @DisplayName("관리자가 모든 환불요청을 조회한다.")
    @Test
    void cancelOrderRequestsFindAllForAdminTest() throws Exception {

        //given

        //when
        List<FindOrderResponse> allCancelOrderRequestsForAdmin = adminOrderService.findAllCancelOrderRequestsForAdmin();

        //then
        assertThat(allCancelOrderRequestsForAdmin)
                .extracting("orderId",
                        "customerName",
                        "customerPhoneNumber",
                        "customerAddress",
                        "orderStatus",
                        "totalItemQuantity",
                        "totalItemPrice").contains(
                        tuple(order3.getId(),
                                "Test Customer Name3",
                                "Test Customer PhoneNumber3",
                                "Test Customer Address3",
                                "ORDER_EXCHANGE_REQUESTED",
                                3L,
                                135000L));
        List<FindOrderItemResponse> orderItemResponses = allCancelOrderRequestsForAdmin.get(0).findOrderItemResponses();
        assertThat(allCancelOrderRequestsForAdmin.get(0).findOrderItemResponses())
                .extracting("itemImagePath",
                        "itemName",
                        "itemOption",
                        "itemQuantity",
                        "totalPrice")
                .contains(tuple("Test Path2", "Test Item Name2", "blue", 12L, 24000L),
                        tuple("Test Path2", "Test Item Name2", "green", 13L, 26000L));

    }

    @DisplayName("관리자가 환불 요청한 특정 주문을 조회한다.")
    @Test
    void findAllCancelOrderRequestsForAdminTest() throws Exception {

        //given
        //when
        List<FindOrderResponse> allCancelOrderRequestsForAdmin = adminOrderService.findAllCancelOrderRequestsForAdmin();

        //then
        assertThat(allCancelOrderRequestsForAdmin)
                .extracting("orderId",
                        "customerName",
                        "customerPhoneNumber",
                        "customerAddress",
                        "orderStatus",
                        "totalItemQuantity",
                        "totalItemPrice")
                .contains(
                        tuple(order3.getId(),
                                "Test Customer Name3",
                                "Test Customer PhoneNumber3",
                                "Test Customer Address3",
                                "ORDER_EXCHANGE_REQUESTED",
                                3L,
                                135000L));

        assertThat(allCancelOrderRequestsForAdmin.get(0).findOrderItemResponses())
                .extracting("itemImagePath",
                        "itemName",
                        "itemOption",
                        "itemQuantity",
                        "totalPrice")
                .contains(
                        tuple("Test Path2", "Test Item Name2", "blue", 12L, 24000L),
                        tuple("Test Path2", "Test Item Name2", "green", 13L, 26000L));
    }

    @DisplayName("관리자가 환불 요청한 특정 주문을 조회한다.")
    @Test
    void findOrderForAdminTest() throws Exception {

        //given
        final Payment payment3 = Payment.builder()
                .order(order3)
                .paymentKey("Test Payment Key3")
                .build();
        paymentRepository.save(payment3);

        //when
        FindOrderForAdminResponse findOrderForAdminResponse = adminOrderService.findOrderForAdmin(order3.getId());

        //then
        assertThat(findOrderForAdminResponse).extracting("orderId", "customerName", "customerPhoneNumber",
                        "customerAddress", "orderStatus", "totalItemQuantity", "totalItemPrice")
                .contains(order3.getId(), "Test Customer Name3", "Test Customer PhoneNumber3",
                        "Test Customer Address3", "ORDER_EXCHANGE_REQUESTED", 3L, 135000L);

        assertThat(findOrderForAdminResponse.findOrderItemResponses()).extracting("itemImagePath", "itemName",
                        "itemOption", "itemQuantity", "totalPrice")
                .contains(tuple("Test Path3", "Test Item Name3", "red", 14L, 42000L),
                        tuple("Test Path3", "Test Item Name3", "blue", 15L, 45000L),
                        tuple("Test Path3", "Test Item Name3", "green", 16L, 48000L));
    }


}
