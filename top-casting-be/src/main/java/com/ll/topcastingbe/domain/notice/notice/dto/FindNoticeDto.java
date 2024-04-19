package com.ll.topcastingbe.domain.notice.notice.dto;

import com.ll.topcastingbe.domain.notice.notice.dto.response.FindNoticeResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record FindNoticeDto(Long id,
                            String title,
                            String content,
                            LocalDateTime createDate,
                            LocalDateTime modifyDate) {

    public static FindNoticeDto of(final FindNoticeResponse findNoticeResponse) {
        final FindNoticeDto findNoticeDto = FindNoticeDto.builder()
                .id(findNoticeResponse.id())
                .title(findNoticeResponse.title())
                .content(findNoticeResponse.content())
                .createDate(findNoticeResponse.createDate())
                .modifyDate(findNoticeResponse.modifyDate())
                .build();

        return findNoticeDto;
    }

    public static List<FindNoticeDto> ofList(final List<FindNoticeResponse> findNoticeResponseList) {
        final List<FindNoticeDto> findNoticeDtoList = findNoticeResponseList.stream()
                .map(FindNoticeDto::of)
                .toList();

        return findNoticeDtoList;
    }
}
