package com.kisnahc.usedmarket.usedmarket.controller;

import com.kisnahc.usedmarket.usedmarket.domain.member.Member;
import com.kisnahc.usedmarket.usedmarket.domain.member.MemberRepository;
import com.kisnahc.usedmarket.usedmarket.domain.member.MemberService;
import com.kisnahc.usedmarket.usedmarket.web.form.SignUpForm;
import com.kisnahc.usedmarket.usedmarket.web.validation.SignUpFormValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberRepository memberRepository;
    private final SignUpFormValidator signUpFormValidator;
    private final MemberService memberService;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute(new SignUpForm());
        return "members/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated @ModelAttribute SignUpForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult.hasErrors());
            return "members/sign-up";
        }
        memberService.processNewMember(form);
        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String email, String token, Model model) {
        Member findMember = memberRepository.findByEmail(email);
        String view = "mail/checked-email";
        if (findMember == null) {
            model.addAttribute("error", "wrong.email");
            return view;
        }
        if (!findMember.isValidToken(token)) {
            model.addAttribute("error", "wrong.token");
            return view;
        }
        memberService.completeSignUp(findMember);
        model.addAttribute("nickname", findMember.getNickname());
        return view;
    }

}
