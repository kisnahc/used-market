package com.kisnahc.usedmarket.usedmarket.web.form;

import com.kisnahc.usedmarket.usedmarket.domain.item.UploadImage;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateItemForm {

    @Length(max = 80)
    private String title;

    private Integer price;

    @Lob
    private String description;

    private List<UploadImage> images = new ArrayList<>();

}
