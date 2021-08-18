package com.kisnahc.usedmarket.usedmarket.controller;

import com.kisnahc.usedmarket.usedmarket.domain.member.Member;
import com.kisnahc.usedmarket.usedmarket.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;


    @DisplayName("회원가입 뷰 테스트")
    @Test
    public void signUpForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/sign-up"))
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원가입 - 성공")
    @Test
    public void testSignUpMember() throws Exception {
        mockMvc.perform(post("/sign-up")
                .param("email", "memberA@naver.com")
                .param("nickname", "kisnahc")
                .param("password", "1234qwer")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
        .andExpect(authenticated());

        Member findMember = memberRepository.findByEmail("memberA@naver.com");
        Assertions.assertThat(findMember.getEmail()).isEqualTo("memberA@naver.com");
        Assertions.assertThat(findMember.getEmailCheckToken()).isNotNull();
        Assertions.assertThat(findMember.getPassword()).isNotEqualTo("1234qwer");
    }

    @DisplayName("회원가입 인증 이메일 - 실패")
    @Test
    public void sendEmailCheckTokenFail() throws Exception {
        mockMvc.perform(get("/check-email-token")
                .param("token", "123123")
                .param("email", "email"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("mail/checked-email"))
                .andExpect(unauthenticated());


    }

    @DisplayName("회원가입 인증 이메일 - 성공")
    @Test
    public void sendEmailCheckToken() throws Exception {
        Member member = Member.builder()
                .email("mameberA@naver.com")
                .nickname("memberA")
                .password("12341234")
                .build();
        Member saveMember = memberRepository.save(member);
        saveMember.generateEmailToken();

        mockMvc.perform(get("/check-email-token")
                .param("token", saveMember.getEmailCheckToken())
                .param("email", saveMember.getEmail()))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeExists("nickname"))
                .andExpect(view().name("mail/checked-email"))
                .andExpect(authenticated());

    }
}