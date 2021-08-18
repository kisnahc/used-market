package com.kisnahc.usedmarket.usedmarket.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Member findByNickname(String nickname);

}
