package com.ll.topcastingbe.domain.review.service;


import com.ll.topcastingbe.domain.item.entity.Item;
import com.ll.topcastingbe.domain.item.exception.ItemNotExistException;
import com.ll.topcastingbe.domain.item.repository.ItemRepository;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.exception.UserNotFoundException;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import com.ll.topcastingbe.domain.review.dto.AddNormalReviewRequestDto;
import com.ll.topcastingbe.domain.review.dto.ModifyReviewRequestDto;
import com.ll.topcastingbe.domain.review.dto.ReviewDetailResponseDto;
import com.ll.topcastingbe.domain.review.dto.ReviewListResponseDto;
import com.ll.topcastingbe.domain.review.entity.Review;
import com.ll.topcastingbe.domain.review.exception.ReviewNotFoundException;
import com.ll.topcastingbe.domain.review.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    public ReviewListResponseDto findReviewList() {

        List<Review> all = reviewRepository.findAll();
        return new ReviewListResponseDto(all);
    }


    public ReviewDetailResponseDto findReviewDetail(Long reviewId) {
        Optional<Review> or = reviewRepository.findById(reviewId);
        if (or.isEmpty()) {
            throw new ReviewNotFoundException();
        }

        return new ReviewDetailResponseDto(or.get());
    }


    @Transactional
    public ReviewDetailResponseDto addNormalReview(Long itemId, String username,
                                                   AddNormalReviewRequestDto addNormalReviewRequestDto) {
        Optional<Item> oi = itemRepository.findById(itemId);
        if (oi.isEmpty()) {
            throw new ItemNotExistException();
        }
        Item item = oi.get();

        Member findMember = memberRepository.findByUsername(username);
        if (findMember == null) {
            throw new UserNotFoundException();
        }

        Review review = Review.builder()
                                .writer(findMember)
                                .item(item)
                                .image(null)
                                .title(addNormalReviewRequestDto.getTitle())
                                .content(addNormalReviewRequestDto.getContent())
                                .rating(addNormalReviewRequestDto.getRating())
                                .build();

        return new ReviewDetailResponseDto((Review) reviewRepository.save(review));
    }

    @Transactional
    public ReviewDetailResponseDto modifyReview(Long reviewId, ModifyReviewRequestDto modifyReviewRequestDto) {
        Review findReview = reviewRepository.findById(reviewId)
                                    .orElseThrow(() -> new ReviewNotFoundException());

        if (modifyReviewRequestDto.getImgUrl() != null) {
            //TODO 이미지 url 존재할시
        }
        findReview.modifyTitle(modifyReviewRequestDto.getTitle());
        findReview.modifyContent(modifyReviewRequestDto.getContent());
        findReview.modifyImg(null);
        findReview.modifyRating(modifyReviewRequestDto.getRating());
        return new ReviewDetailResponseDto(findReview);
    }


    @Transactional
    public void deleteReview(Long reviewId) {
        Review findReview = reviewRepository.findById(reviewId)
                                    .orElseThrow(() -> new ReviewNotFoundException());
        reviewRepository.delete(findReview);
    }

    public ReviewListResponseDto findReviewRating(int rating, Long itemId) {
        List<Review> findReviewList = reviewRepository.findByRatingAndItemId(rating, itemId);
        return new ReviewListResponseDto(findReviewList);
    }

    public ReviewListResponseDto findItemReviewList(Long itemId, String sort) {
        if (sort.equals("DESC")) {
            return new ReviewListResponseDto(reviewRepository.findByItemIdOrderByRatingDesc(itemId));
        } else if (sort.equals("ASC")) {
            return new ReviewListResponseDto(reviewRepository.findByItemIdOrderByRatingAsc(itemId));
        } else {
            throw new ReviewNotFoundException();
        }
    }

    public ReviewListResponseDto findMemberReviewList(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                                    .orElseThrow(() -> new UserNotFoundException());
        return new ReviewListResponseDto(reviewRepository.findByWriter(findMember));
    }

    public void makeReviewSummary() {
        
    }
}
