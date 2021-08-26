package com.kisnahc.usedmarket.usedmarket.controller;

import com.kisnahc.usedmarket.usedmarket.domain.item.*;
import com.kisnahc.usedmarket.usedmarket.domain.member.CurrentMember;
import com.kisnahc.usedmarket.usedmarket.domain.member.Member;
import com.kisnahc.usedmarket.usedmarket.web.form.CreateItemForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("item")
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/create-item")
    public String createItemForm(Model model) {
        model.addAttribute(new CreateItemForm());
        return "item/create-item";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-item")
    public String createItem(@CurrentMember Member member,
                             @Validated @ModelAttribute CreateItemForm form, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "item/create-item";
        }

        Item savedItem = itemService.createItem(form, member);
        return "redirect:/item/{itemId}";
    }
}
