package com.ll.topcastingbe.domain.qna.post.entity;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.exception.AuthException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.qna.post.dto.request.ModifyPostRequest;
import com.ll.topcastingbe.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void modifyPost(final ModifyPostRequest modifyPostRequest) {
        this.title = modifyPostRequest.title();
        this.content = modifyPostRequest.content();
    }

    public void checkAuthorizedMember(final Member member) {
        if (this.member != member) {
            throw new AuthException(ErrorMessage.UNAUTHORIZED_USER);
        }
    }
}
