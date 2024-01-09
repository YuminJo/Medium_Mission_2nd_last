package com.ll.medium.domain.member.member.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ll.medium.domain.member.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

	Page<Member> findByUsernameContainsIgnoreCase(String kw, Pageable pageable);
}
