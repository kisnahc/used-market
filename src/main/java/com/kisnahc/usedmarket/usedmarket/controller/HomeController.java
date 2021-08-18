package com.kisnahc.usedmarket.usedmarket.controller;

import com.kisnahc.usedmarket.usedmarket.domain.member.CurrentMember;
import com.kisnahc.usedmarket.usedmarket.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@CurrentMember Member member, Model model) {
        if (member != null) {
            model.addAttribute(member);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
