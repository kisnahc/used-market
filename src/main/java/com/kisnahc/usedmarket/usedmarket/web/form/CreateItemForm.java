package com.kisnahc.usedmarket.usedmarket.web.form;

import com.kisnahc.usedmarket.usedmarket.domain.item.UploadImage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateItemForm {

    private String title;
    private String price;
    private String description;
    private List<UploadImage> images = new ArrayList<>();

}
