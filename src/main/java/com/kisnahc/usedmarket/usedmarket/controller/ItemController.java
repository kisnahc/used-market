package com.kisnahc.usedmarket.usedmarket.controller;

import com.kisnahc.usedmarket.usedmarket.web.form.CreateItemForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("item")
@Controller
public class ItemController {

    @GetMapping("/create-item")
    public String createItemForm(Model model) {
        model.addAttribute(new CreateItemForm());
        return "item/create-item";
    }

}
