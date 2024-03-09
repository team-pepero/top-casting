package com.ll.topcastingbe.domain.order.controller.order;


import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import com.ll.topcastingbe.domain.member.service.MemberService;
import com.ll.topcastingbe.domain.order.dto.order.AddOrderDto;
import com.ll.topcastingbe.domain.order.dto.order.AddOrderResponseDto;
import com.ll.topcastingbe.domain.order.dto.order.FindOrderDto;
import com.ll.topcastingbe.domain.order.dto.order.FindOrderForAdminDto;
import com.ll.topcastingbe.domain.order.dto.order.OrderSheetInitRequestDto;
import com.ll.topcastingbe.domain.order.dto.order.OrderSheetInitResponseDto;
import com.ll.topcastingbe.domain.order.dto.order.RequestCancelOrderDto;
import com.ll.topcastingbe.domain.order.dto.order.request.OrderSheetInitRequest;
import com.ll.topcastingbe.domain.order.dto.order.request.RequestCancelOrderRequest;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderForAdminResponse;
import com.ll.topcastingbe.domain.order.dto.order.response.FindOrderResponse;
import com.ll.topcastingbe.domain.order.exception.AuthException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.order.service.order.OrderService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //    @PreAuthorize("isAuthenticated()")
    @PostMapping("/order")
    public ResponseEntity<AddOrderResponseDto> orderAdd(@Valid @RequestBody final AddOrderDto addOrderDto,
                                                        @AuthenticationPrincipal final UserDetails userDetails) {
//        final Member member = memberService.findMember(userDetails.getUsername());
        final Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new AuthException(ErrorMessage.UNAUTHORIZED_USER));
        final AddOrderResponseDto addOrderResponseDto =
                AddOrderResponseDto.of(orderService.addOrder(addOrderDto.toAddOrderRequest(), member));

        return ResponseEntity.created(URI.create(""))
                .header("Location", "")
                .body(addOrderResponseDto);
    }

    //    @PreAuthorize("isAuthenticated()")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<FindOrderDto> orderFind(@PathVariable("orderId") final UUID orderId,
                                                  @AuthenticationPrincipal final UserDetails userDetails) {
//        final Member member = memberService.findMember(userDetails.getUsername());
        final Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new AuthException(ErrorMessage.UNAUTHORIZED_USER));
        final FindOrderDto findOrderDto = FindOrderDto.of(orderService.findOrder(orderId, member));

        return ResponseEntity.ok(findOrderDto);
    }

    //    @PreAuthorize("isAuthenticated()")
    @GetMapping("/orders")
    public ResponseEntity<List<FindOrderDto>> orderFindAll(@AuthenticationPrincipal final UserDetails userDetails) {
//        final Member member = memberService.findMember(userDetails.getUsername());
        final Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new AuthException(ErrorMessage.UNAUTHORIZED_USER));
        List<FindOrderResponse> findOrderResponses = orderService.findOrderList(member);
        final List<FindOrderDto> findOrderDtos = FindOrderDto.ofList(findOrderResponses);
        return ResponseEntity.ok(findOrderDtos);
    }

    //    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<List<FindOrderDto>> orderRemove(@PathVariable("orderId") final UUID orderId,
                                                          @AuthenticationPrincipal final UserDetails userDetails) {
//        final Member member = memberService.findMember(userDetails.getUsername());
        final Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new AuthException(ErrorMessage.UNAUTHORIZED_USER));
        orderService.removeOrder(orderId, member);
        return ResponseEntity.noContent().build();
    }

    //    @PreAuthorize("isAuthenticated()")
    @PostMapping("/order-sheet")
    public ResponseEntity<OrderSheetInitResponseDto> initSheet(
            @RequestBody OrderSheetInitRequestDto orderSheetInitRequestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
//        final Member member = memberService.findMember(userDetails.getUsername());
        final Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new AuthException(ErrorMessage.UNAUTHORIZED_USER));
        final OrderSheetInitRequest orderSheetInitRequest = orderSheetInitRequestDto.toOrderSheetRequest();
        final OrderSheetInitResponseDto orderSheetInitResponseDto = OrderSheetInitResponseDto.of(
                orderService.initOrderSheet(orderSheetInitRequest, member));

        return ResponseEntity.ok(orderSheetInitResponseDto);
    }

    //    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/orders/{orderId}/refund")
    public ResponseEntity<Void> cancelOrderRequest(@RequestBody final RequestCancelOrderDto requestCancelOrderDto,
                                                   @PathVariable("orderId") final UUID orderId,
                                                   @AuthenticationPrincipal final UserDetails userDetails) {
//        final Member member = memberService.findMember(userDetails.getUsername());
        final Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new AuthException(ErrorMessage.UNAUTHORIZED_USER));
        RequestCancelOrderRequest requestCancelOrderRequest = requestCancelOrderDto.toRequestCancelOrderRequest();
        orderService.requestCancelOrder(orderId, requestCancelOrderRequest, member);
        return ResponseEntity.ok().build();
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/orders/refund")
    public ResponseEntity<List<FindOrderDto>> cancelOrderRequestsFindAllForAdmin() {
        List<FindOrderResponse> findOrderResponses = orderService.findAllCancelOrderRequestsForAdmin();
        List<FindOrderDto> findOrderDtos = FindOrderDto.ofList(findOrderResponses);
        return ResponseEntity.ok().body(findOrderDtos);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/orders")
    public ResponseEntity<List<FindOrderDto>> orderFindAllForAdmin() {
        final List<FindOrderDto> findOrderDtos = FindOrderDto.ofList(orderService.findOrderListForAdmin());
        return ResponseEntity.ok(findOrderDtos);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/order/{orderId}")
    public ResponseEntity<FindOrderForAdminDto> orderFindForAdmin(@PathVariable("orderId") final UUID orderId) {
        FindOrderForAdminResponse findOrderForAdminResponse = orderService.findOrderForAdmin(orderId);
        final FindOrderForAdminDto findOrderForAdminDto = FindOrderForAdminDto.of(findOrderForAdminResponse);
        return ResponseEntity.ok(findOrderForAdminDto);
    }

}
