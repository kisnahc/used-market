package com.kisnahc.usedmarket.usedmarket.web.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import java.util.List;

@Data
public class CreateItemForm {

    private Long itemId;

    @Length(max = 80)
    private String title;

    private Integer price;

    @Lob
    private String description;

    private List<MultipartFile> images;

}
