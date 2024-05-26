package com.ll.topcastingbe.domain.order.integeration.controller.order;

import static com.ll.topcastingbe.domain.order.entity.OrderStatus.ORDER_EXCHANGE_REQUESTED;
import static com.ll.topcastingbe.domain.order.entity.OrderStatus.REFUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.oneOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.ll.topcastingbe.domain.order.dto.order.AddOrderDto;
import com.ll.topcastingbe.domain.order.dto.order.AddOrderResponseDto;
import com.ll.topcastingbe.domain.order.dto.order_item.AddOrderItemDto;
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.OrderStatus;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import com.ll.topcastingbe.domain.order.service.order.OrderService;
import com.ll.topcastingbe.domain.payment.repository.PaymentRepository;
import com.ll.topcastingbe.global.security.auth.PrincipalDetailsService;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class OrderControllerIt {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PrincipalDetailsService userDetailsService;

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
    private MockMvc mock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private OrderItem orderItem1;
    private CartItem cartItem1;
    private CartItem cartItem2;
    private CartItem cartItem3;

    private Member member;
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

        member = Member.builder().username("username").password(passwordEncoder.encode("1"))
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

        cartItem1 = CartItem.builder().cart(cart).option(option1).itemQuantity(11).build();
        cartItem2 = CartItem.builder().cart(cart).option(option2).itemQuantity(12).build();
        cartItem3 = CartItem.builder().cart(cart).option(option2).itemQuantity(13).build();
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

    @DisplayName("주문을 추가한다.")
    @Test
    void orderAdd() throws Exception {

        //given
        final AddOrderItemDto addOrderItemDto1 = AddOrderItemDto.builder()
                .cartItemId(cartItem1.getId())
                .itemQuantity(11L)
                .build();
        final AddOrderItemDto addOrderItemDto2 = AddOrderItemDto.builder()
                .cartItemId(cartItem2.getId())
                .itemQuantity(12L)
                .build();
        final AddOrderItemDto addOrderItemDto3 = AddOrderItemDto.builder()
                .cartItemId(cartItem3.getId())
                .itemQuantity(13L)
                .build();
        List<AddOrderItemDto> addOrderItemDtos = List.of(addOrderItemDto1, addOrderItemDto2, addOrderItemDto3);
        final AddOrderDto addOrderDto = AddOrderDto.builder()
                .customerName("Test Customer Name")
                .customerAddress("Test Customer Address")
                .customerPhoneNumber("Test Customer PhoneNumber")
                .totalItemPrice(61000L)
                .totalItemQuantity(1L)
                .addOrderItemDtos(addOrderItemDtos)
                .build();

        final UserDetails user = userDetailsService.loadUserByUsername("username");
        //when
        MvcResult mvcResult = mock.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addOrderDto))
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpectAll(jsonPath("$.orderId").isNotEmpty())
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        AddOrderResponseDto addOrderResponseDto = objectMapper.readValue(jsonResponse, AddOrderResponseDto.class);

        //then
        Orders order = orderRepository.findById(addOrderResponseDto.orderId()).get();
        assertThat(order).extracting("member",
                        "orderStatus",
                        "customerName",
                        "customerPhoneNumber",
                        "customerAddress",
                        "totalItemQuantity",
                        "totalItemPrice")
                .contains(member,
                        OrderStatus.WAITING,
                        "Test Customer Name",
                        "Test Customer PhoneNumber",
                        "Test Customer Address",
                        1L,
                        61000L);
    }


    @DisplayName("주문에 필요한 정보가 없으면 예외가 발생한다.")
    @Test
    void addOrderNotInputRequestException() throws Exception {

        //given
        final AddOrderItemDto addOrderItemDto1 = AddOrderItemDto.builder().build();
        List<AddOrderItemDto> addOrderItemDtos = List.of(addOrderItemDto1);
        final AddOrderDto addOrderDto = AddOrderDto.builder().addOrderItemDtos(addOrderItemDtos)
                .build();

        final UserDetails user = userDetailsService.loadUserByUsername("username");
        //when
        mock.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addOrderDto))
                        .with(SecurityMockMvcRequestPostProcessors.user(user))).andDo(print())
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.errors", hasSize(7)),
                        jsonPath("$.errors[*].field", everyItem(notNullValue())),
                        jsonPath("$.errors[*].message", everyItem(matchesPattern(".*필수입니다.")))
                );
    }


    @DisplayName("특정 주문을 조회한다.")
    @Test
    void orderFindTest() throws Exception {

        //given
        final UserDetails user = userDetailsService.loadUserByUsername("username");

        //when && then
        mock.perform(get("/api/v1/orders/{orderId}", order3.getId()).contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(user))).andDo(print()).andExpect(status().isOk())
                .andExpectAll(jsonPath("$.orderId").isNotEmpty(),
                        jsonPath("$.customerName").value("Test Customer Name3"),
                        jsonPath("$.customerPhoneNumber").value("Test Customer PhoneNumber3"),
                        jsonPath("$.customerAddress").value("Test Customer Address3"),
                        jsonPath("$.orderStatus").value("ORDER_EXCHANGE_REQUESTED"),
                        jsonPath("$.totalItemQuantity").value(3L),
                        jsonPath("$.totalItemPrice").value(135000L),
                        jsonPath("$[*].findOrderItemDtos[*].itemImagePath",
                                everyItem(matchesPattern("Test Path.*"))),
                        jsonPath("$[*].findOrderItemDtos[*].itemName",
                                everyItem(matchesPattern("Test Item Name.*"))),
                        jsonPath("$[*].findOrderItemDtos[*].itemOption",
                                everyItem(matchesPattern("red|blue|green"))),
                        jsonPath("$[*].findOrderItemDtos[*].itemQuantity", everyItem(oneOf(14, 15, 16))),
                        jsonPath("$[*].findOrderItemDtos[*].totalPrice", everyItem(oneOf(42000, 45000, 48000)))
                );

    }

    @DisplayName("특정 회원의 모든 주문을 조회한다.")
    @Test
    void orderFindAllTest() throws Exception {

        //given
        final UserDetails user = userDetailsService.loadUserByUsername("username");

        //when && then
        mock.perform(get("/api/v1/orders").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(user))).andDo(print()).andExpect(status().isOk())
                .andExpectAll(jsonPath("$[*].orderId", everyItem(notNullValue())),
                        jsonPath("$[*].customerName", everyItem(matchesPattern("Test Customer Name.*"))),
                        jsonPath("$[*].customerPhoneNumber", everyItem(matchesPattern("Test Customer PhoneNumber.*"))),
                        jsonPath("$[*].customerAddress", everyItem(matchesPattern("Test Customer Address.*"))),
                        jsonPath("$[*].orderStatus",
                                everyItem(matchesPattern("REFUND|SHIPPING|ORDER_EXCHANGE_REQUESTED"))),
                        jsonPath("$[*].totalItemQuantity", everyItem(oneOf(1, 2, 3))),
                        jsonPath("$[*].totalItemPrice", everyItem(oneOf(11000, 50000, 135000))),
                        jsonPath("$[*].findOrderItemDtos[*].itemImagePath",
                                everyItem(matchesPattern("Test Path.*"))),
                        jsonPath("$[*].findOrderItemDtos[*].itemName",
                                everyItem(matchesPattern("Test Item Name.*"))),
                        jsonPath("$[*].findOrderItemDtos[*].itemOption",
                                everyItem(matchesPattern("red|blue|green"))),
                        jsonPath("$[*].findOrderItemDtos[*].itemQuantity", everyItem(oneOf(11, 12, 13, 14, 15, 16))),
                        jsonPath("$[*].findOrderItemDtos[*].totalPrice",
                                everyItem(oneOf(11000, 24000, 26000, 42000, 45000, 48000)))
                );
    }

    //todo 이것은 나중에 삭제에 오더와 관련된 모든것을 삭제하ㅡㄴ 것을 구현 후 적용
    @DisplayName("특정 주문을 삭제한다.")
    @Test
    void orderRemoveTest() throws Exception {

        //given
        final UserDetails user = userDetailsService.loadUserByUsername("username");

        //when
        mock.perform(delete("/api/v1/orders/{orderId}", order2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpect(status().isNoContent());

        //then
        List<Orders> orders = orderRepository.findAll();
        assertThat(orders.size()).isEqualTo(2);
    }
}
