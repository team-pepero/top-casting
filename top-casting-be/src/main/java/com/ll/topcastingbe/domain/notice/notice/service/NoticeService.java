package com.ll.topcastingbe.domain.notice.notice.service;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.notice.notice.dto.request.CreateNoticeRequest;
import com.ll.topcastingbe.domain.notice.notice.dto.request.ModifyNoticeRequest;
import com.ll.topcastingbe.domain.notice.notice.dto.response.FindNoticeResponse;
import java.util.List;

public interface NoticeService {
    Long createNotice(final CreateNoticeRequest createNoticeRequest, final Member member);

    FindNoticeResponse findNotice(final Long noticeId);

    void modifyNotice(final Long noticeId, final ModifyNoticeRequest modifyNoticeRequest, final Member member);

    void removeNotice(final Long noticeId, final Member member);

    List<FindNoticeResponse> findAllNotice();
}
