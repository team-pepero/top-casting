package com.ll.topcastingbe.domain.review.controller;

import com.ll.topcastingbe.domain.member.exception.UserAndWriterNotMatchException;
import com.ll.topcastingbe.domain.member.service.MemberService;
import com.ll.topcastingbe.domain.review.dto.AddNormalReviewRequestDto;
import com.ll.topcastingbe.domain.review.dto.ModifyReviewRequestDto;
import com.ll.topcastingbe.domain.review.dto.ReviewDetailResponseDto;
import com.ll.topcastingbe.domain.review.dto.ReviewListResponseDto;
import com.ll.topcastingbe.domain.review.service.ReviewService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;

    @GetMapping("/review")
    public ResponseEntity<ReviewListResponseDto> reviewList() {
        return ResponseEntity.ok().body(reviewService.findReviewList());
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDetailResponseDto> reviewDetail(@PathVariable Long reviewId) {
        return ResponseEntity.ok().body(reviewService.findReviewDetail(reviewId));
    }

    @GetMapping("/items/{itemId}/review")
    public ResponseEntity<ReviewListResponseDto> itemReviewList(@PathVariable Long itemId,
                                                                @RequestParam(defaultValue = "DESC") String sort) {

        return ResponseEntity.ok().body(reviewService.findItemReviewList(itemId, sort));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/items/{itemId}/review")
    public ResponseEntity<ReviewDetailResponseDto> reviewAdd(@PathVariable Long itemId, Principal principal,
                                                             @RequestBody AddNormalReviewRequestDto addNormalReviewRequestDto) {
        return ResponseEntity.ok()
                       .body(reviewService.addNormalReview(itemId, principal.getName(), addNormalReviewRequestDto));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDetailResponseDto> reviewModify(@PathVariable Long reviewId, Principal principal,
                                                                @RequestBody ModifyReviewRequestDto modifyReviewRequestDto) {
        if (reviewService.findReviewDetail(reviewId).getWriter() != principal.getName()) {
            throw new UserAndWriterNotMatchException();
        }
        return ResponseEntity.ok()
                       .body(reviewService.modifyReview(reviewId, modifyReviewRequestDto));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/review/{reviewId}")
    public void reviewDelete(@PathVariable Long reviewId, Principal principal) {
        if (reviewService.findReviewDetail(reviewId).getWriter() != principal.getName()) {
            throw new UserAndWriterNotMatchException();
        }
        reviewService.deleteReview(reviewId);
    }

    @GetMapping("items/{itemId}/review/rating/{rating}")
    public ResponseEntity<ReviewListResponseDto> ItemReviewRatingList(@PathVariable int rating,
                                                                      @PathVariable Long itemId) {
        return ResponseEntity.ok().body(reviewService.findReviewRating(rating, itemId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/members/{memberId}/review")
    public ResponseEntity<ReviewListResponseDto> memberReviewList(@PathVariable Long memberId, Principal principal) {
        if (memberService.findMember(principal.getName()).getId() != memberId) {
            throw new UserAndWriterNotMatchException("권한이 없습니다.");
        }
        return ResponseEntity.ok().body(reviewService.findMemberReviewList(memberId));
    }

    @GetMapping("/items/{itemId}/review/summary")
    public ResponseEntity ItemReviewSummary(@PathVariable Long itemId) {
        reviewService.makeReviewSummary();
        return null;
    }


}
