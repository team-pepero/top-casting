package com.ll.topcastingbe.domain.notice.notice.dto.response;

import com.ll.topcastingbe.domain.notice.notice.entity.Notice;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record FindNoticeResponse(Long id, String title, String content, LocalDateTime createDate,
                                 LocalDateTime modifyDate) {

    public static FindNoticeResponse of(final Notice notice) {
        final FindNoticeResponse findNoticeResponse = FindNoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createDate(notice.getCreateDate())
                .modifyDate(notice.getModifyDate())
                .build();

        return findNoticeResponse;
    }

    public static List<FindNoticeResponse> ofList(final List<Notice> noticeList) {
        final List<FindNoticeResponse> findNoticeResponseList = noticeList.stream()
                .map(FindNoticeResponse::of)
                .toList();

        return findNoticeResponseList;
    }
}
