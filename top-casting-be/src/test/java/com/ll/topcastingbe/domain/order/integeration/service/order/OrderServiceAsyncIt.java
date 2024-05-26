package com.ll.topcastingbe.domain.order.integeration.service.order;

import static com.ll.topcastingbe.domain.order.entity.OrderStatus.REFUND;

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
import com.ll.topcastingbe.domain.order.entity.OrderItem;
import com.ll.topcastingbe.domain.order.entity.Orders;
import com.ll.topcastingbe.domain.order.repository.order.OrderRepository;
import com.ll.topcastingbe.domain.order.repository.order_item.OrderItemRepository;
import com.ll.topcastingbe.domain.order.service.order.OrderService;
import com.ll.topcastingbe.domain.payment.repository.PaymentRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceAsyncIt {

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
    private EntityManager em;

    @BeforeAll
    public void init() {
//        orderItemRepository.deleteAllInBatch();
//        paymentRepository.deleteAllInBatch();
//        orderRepository.deleteAllInBatch();
//        cartItemRepository.deleteAllInBatch();
//        optionRepository.deleteAllInBatch();
//        itemRepository.deleteAllInBatch();
//        subCategoryRepository.deleteAllInBatch();
//        mainCategoryRepository.deleteAllInBatch();
//        detailedImageRepository.deleteAllInBatch();
//        imageRepository.deleteAllInBatch();
//        cartRepository.deleteAllInBatch();
//        memberRepository.deleteAllInBatch();
//
//        em.createNativeQuery("ALTER TABLE order_item AUTO_INCREMENT 1").executeUpdate();
//        em.createNativeQuery("ALTER TABLE payment AUTO_INCREMENT 1").executeUpdate();
//        em.createNativeQuery("ALTER TABLE cart_item AUTO_INCREMENT 1").executeUpdate();
//        em.createNativeQuery("ALTER TABLE option AUTO_INCREMENT 1").executeUpdate();
//        em.createNativeQuery("ALTER TABLE item AUTO_INCREMENT 1").executeUpdate();
//        em.createNativeQuery("ALTER TABLE main_category AUTO_INCREMENT 1").executeUpdate();
//        em.createNativeQuery("ALTER TABLE sub_category AUTO_INCREMENT 1").executeUpdate();
//        em.createNativeQuery("ALTER TABLE detailed_image AUTO_INCREMENT 1").executeUpdate();
//        em.createNativeQuery("ALTER TABLE image AUTO_INCREMENT 1").executeUpdate();
//        em.createNativeQuery("ALTER TABLE member AUTO_INCREMENT 1").executeUpdate();

        //given
        final DetailedImage detailedImage1 = DetailedImage.builder().path("Test Path1")
                .imageName("Test Detailed Image Name1").originFileName("Test Origin File Name1").build();
        final DetailedImage detailedImage2 = DetailedImage.builder().path("Test Path2")
                .imageName("Test Detailed Image Name2").originFileName("Test Origin File Name2").build();
        final DetailedImage detailedImage3 = DetailedImage.builder().path("Test Path3")
                .imageName("Test Detailed Image Name3").originFileName("Test Origin File Name3").build();
        detailedImageRepository.saveAll(List.of(detailedImage1, detailedImage2, detailedImage3));

        final Image image1 = Image.builder().path("Test Path1").imageName("Test Image Name1")
                .originFileName("Test Original File Name1").build();
        final Image image2 = Image.builder().path("Test Path2").imageName("Test Image Name2")
                .originFileName("Test Original File Name2").build();
        final Image image3 = Image.builder().path("Test Path3").imageName("Test Image Name3")
                .originFileName("Test Original File Name3").build();
        imageRepository.saveAll(List.of(image1, image2, image3));

        final MainCategory mainCategory1 = MainCategory.builder().categoryName("Test Main Category Name").build();
        final MainCategory mainCategory2 = MainCategory.builder().categoryName("Test Main Category Name").build();
        final MainCategory mainCategory3 = MainCategory.builder().categoryName("Test Main Category Name").build();
        mainCategoryRepository.saveAll(List.of(mainCategory1, mainCategory2, mainCategory3));

        final SubCategory subCategory1 = SubCategory.builder().parentCategory(mainCategory1)
                .subcategoryName("Test Sub Category Name1").build();
        final SubCategory subCategory2 = SubCategory.builder().parentCategory(mainCategory2)
                .subcategoryName("Test Sub Category Name2").build();
        final SubCategory subCategory3 = SubCategory.builder().parentCategory(mainCategory2)
                .subcategoryName("Test Sub Category Name3").build();
        final SubCategory subCategory4 = SubCategory.builder().parentCategory(mainCategory3)
                .subcategoryName("Test Sub Category Name4").build();
        final SubCategory subCategory5 = SubCategory.builder().parentCategory(mainCategory3)
                .subcategoryName("Test Sub Category Name5").build();
        final SubCategory subCategory6 = SubCategory.builder().parentCategory(mainCategory3)
                .subcategoryName("Test Sub Category Name6").build();
        subCategoryRepository.saveAll(
                List.of(subCategory1, subCategory2, subCategory3, subCategory4, subCategory5, subCategory6));

        final Item item1 = Item.builder().itemName("Test Item Name1").itemPrice(BigDecimal.valueOf(1000)).image(image1)
                .detailedImage(detailedImage1).mainCategory(mainCategory1).subCategory(subCategory1).build();
        final Item item2 = Item.builder().itemName("Test Item Name2").itemPrice(BigDecimal.valueOf(2000)).image(image2)
                .detailedImage(detailedImage2).mainCategory(mainCategory2).subCategory(subCategory2).build();
        final Item item3 = Item.builder().itemName("Test Item Name3").itemPrice(BigDecimal.valueOf(3000)).image(image3)
                .detailedImage(detailedImage3).mainCategory(mainCategory3).subCategory(subCategory3).build();
        itemRepository.saveAll(List.of(item1, item2, item3));

        final Option option1 = Option.builder().item(item1).colorName("red").stock(100).build();
        final Option option2 = Option.builder().item(item2).colorName("blue").stock(22).build();
        final Option option3 = Option.builder().item(item2).colorName("green").stock(23).build();
        final Option option4 = Option.builder().item(item3).colorName("red").stock(24).build();
        final Option option5 = Option.builder().item(item3).colorName("blue").stock(25).build();
        final Option option6 = Option.builder().item(item3).colorName("green").stock(26).build();
        optionRepository.saveAll(List.of(option1, option2, option3, option4, option5, option6));

        final CartItem cartItem1 = CartItem.builder().option(option1).itemQuantity(11).build();
        final CartItem cartItem2 = CartItem.builder().option(option2).itemQuantity(12).build();
        final CartItem cartItem3 = CartItem.builder().option(option2).itemQuantity(13).build();
        final CartItem cartItem4 = CartItem.builder().option(option3).itemQuantity(14).build();
        final CartItem cartItem5 = CartItem.builder().option(option3).itemQuantity(15).build();
        final CartItem cartItem6 = CartItem.builder().option(option3).itemQuantity(16).build();
        cartItemRepository.saveAll(List.of(cartItem1, cartItem2, cartItem3, cartItem4, cartItem5, cartItem6));

        final Member member = Member.builder().username("username1").password("password1").nickname("nickname1")
                .email("email1").roles("ROLE_ADMIN").build();
        memberRepository.save(member);

        final Orders order1 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order2 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order3 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order4 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order5 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order6 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order7 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order8 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order9 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order10 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order11 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order12 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        final Orders order13 = Orders.builder().member(member).orderStatus(REFUND).customerName("Test Customer Name1")
                .customerPhoneNumber("Test Customer PhoneNumber1").customerAddress("Test Customer Address1")
                .totalItemQuantity(1L).totalItemPrice(11000L).build();
        List<Orders> orders = List.of(order1, order2, order3, order4, order5, order6, order7, order8, order9, order10,
                order11,
                order12, order13);
        orderRepository.saveAll(orders);

        final OrderItem orderItem1 = OrderItem.builder().order(order1).option(option1).itemQuantity(3L)
                .totalPrice(11000L).build();
        final OrderItem orderItem2 = OrderItem.builder().order(order2).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem3 = OrderItem.builder().order(order3).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem4 = OrderItem.builder().order(order4).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem5 = OrderItem.builder().order(order5).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem6 = OrderItem.builder().order(order6).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem7 = OrderItem.builder().order(order7).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem8 = OrderItem.builder().order(order8).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem9 = OrderItem.builder().order(order9).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem10 = OrderItem.builder().order(order10).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem11 = OrderItem.builder().order(order11).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem12 = OrderItem.builder().order(order12).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();
        final OrderItem orderItem13 = OrderItem.builder().order(order13).option(option1).itemQuantity(11L)
                .totalPrice(11000L).build();

        orderItemRepository.saveAll(
                List.of(orderItem1, orderItem2, orderItem3, orderItem4, orderItem5, orderItem6, orderItem7, orderItem8,
                        orderItem9, orderItem10, orderItem11, orderItem12, orderItem13));
    }

//    @DisplayName("재고가 1이 된다.")
//    @Test
//    @Transactional
//    public void test() throws InterruptedException {
//
//        List<Orders> orders = orderRepository.findAllByMember(memberRepository.findById(6L).get());
//
//        final CountDownLatch countDownLatch = new CountDownLatch(50);
//        ExecutorService executorService = Executors.newFixedThreadPool(50);
//
//        for (int i = 0; i < 50; i++) {
//            executorService.submit(() -> {
//                orderService.deductStockForOrder(orders.get(0))
//                        .whenComplete((result, throwable) -> {
//                            countDownLatch.countDown();
//                        });
//            });
//        }
//        countDownLatch.await();
//
//        Option option = optionRepository.findById(20L).get();
//        assertEquals(1, option.getStock());
//    }
}
