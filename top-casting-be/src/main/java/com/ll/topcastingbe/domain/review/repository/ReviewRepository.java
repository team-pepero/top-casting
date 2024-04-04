package com.ll.topcastingbe.domain.review.repository;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRatingAndItemId(int rating, Long itemId);

    List<Review> findByItemId(Long itemId);

    List<Review> findByWriter(Member member);

    List<Review> findByItemIdOrderByRatingDesc(Long itemId);

    List<Review> findByItemIdOrderByRatingAsc(Long itemId);
}
