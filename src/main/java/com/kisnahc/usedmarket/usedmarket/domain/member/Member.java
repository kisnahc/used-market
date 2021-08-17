package com.kisnahc.usedmarket.usedmarket.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGeneratedAt;

    @Builder
    public Member(String email, String nickname, String password, boolean emailVerified, String emailCheckToken) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.emailVerified = emailVerified;
        this.emailCheckToken = emailCheckToken;
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public void completeSignUp() {
        this.emailVerified = true;
    }

    public void generateEmailToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();

    }
}
