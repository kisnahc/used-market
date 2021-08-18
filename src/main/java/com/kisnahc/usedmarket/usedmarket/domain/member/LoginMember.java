package com.kisnahc.usedmarket.usedmarket.domain.member;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class LoginMember extends User {

    private Member member;

    public LoginMember(Member member) {
        super(member.getNickname(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_MEMBER")));
        this.member = member;
    }
}
