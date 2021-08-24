package com.kisnahc.usedmarket.usedmarket.controller;

import com.kisnahc.usedmarket.usedmarket.domain.item.ImageHandler;
import com.kisnahc.usedmarket.usedmarket.domain.item.ItemService;
import com.kisnahc.usedmarket.usedmarket.domain.item.UploadImage;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("item")
@Controller
public class ItemController {

    private final ItemService itemService;
    private final ImageHandler imageHandler;

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


        itemService.createItem(form, member);
        return "redirect:/create-item";
    }
}
