package com.ll.topcastingbe.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ll.topcastingbe.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
