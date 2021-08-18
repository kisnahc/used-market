package com.kisnahc.usedmarket.usedmarket.controller;

import com.kisnahc.usedmarket.usedmarket.domain.member.MemberRepository;
import com.kisnahc.usedmarket.usedmarket.domain.member.MemberService;
import com.kisnahc.usedmarket.usedmarket.web.form.SignUpForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void createMember() {
        SignUpForm form = new SignUpForm();
        form.setEmail("memberA@naver.com");
        form.setNickname("memberA");
        form.setPassword("12341234");
        memberService.processNewMember(form);
    }

    @AfterEach
    void deleteMember() {
        memberRepository.deleteAll();
    }

    @DisplayName("이메일 로그인 - 실패")
    @Test
    public void login_by_email_fail() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "memberB@gmail.com")
                .param("password", "1234")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());

    }

    @DisplayName("이메일 로그인 - 성공")
    @Test
    public void login_by_email_success() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "memberA@naver.com")
                .param("password", "12341234")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated());
    }

    @DisplayName("닉네임 로그인 - 실패")
    @Test
    public void login_by_nickname_fail() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "memberB")
                .param("password", "1234")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @DisplayName("닉네임 로그인 - 성공")
    @Test
    public void login_by_nickname_success() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "memberA")
                .param("password", "12341234")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("memberA"));
    }

}