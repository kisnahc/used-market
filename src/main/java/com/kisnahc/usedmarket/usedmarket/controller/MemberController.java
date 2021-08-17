package com.kisnahc.usedmarket.usedmarket.controller;

import com.kisnahc.usedmarket.usedmarket.domain.member.Member;
import com.kisnahc.usedmarket.usedmarket.domain.member.MemberRepository;
import com.kisnahc.usedmarket.usedmarket.web.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/sign-up")
    public String singUpForm(@ModelAttribute SignUpForm signUpForm) {
        return "members/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated @ModelAttribute SignUpForm signUpForm, BindingResult bindingResult, Model model) {

        String view = "members/sign-up";

        if (bindingResult.hasGlobalErrors()) {
            log.info("errors={}", bindingResult.hasGlobalErrors());
            return view;
        }

        Member saveMember = Member.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .emailVerified(false)
                .build();

        memberRepository.save(saveMember);
        return "redirect:/";
    }
}
