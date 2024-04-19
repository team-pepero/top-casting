package com.ll.topcastingbe.domain.notice.notice.dto.request;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.notice.notice.entity.Notice;
import lombok.Builder;

@Builder
public record CreateNoticeRequest(String title, String content) {

    public Notice toNotice(final Member member) {
        final Notice notice = Notice.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();

        return notice;
    }
}
