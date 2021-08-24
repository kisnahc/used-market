package com.kisnahc.usedmarket.usedmarket.domain.item;

import com.kisnahc.usedmarket.usedmarket.domain.BaseTimeEntity;
import com.kisnahc.usedmarket.usedmarket.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String title;

    private Integer price;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UploadImage> images = new ArrayList<>();

    @Builder
    public Item(String title, Integer price, String description, Member member, List<UploadImage> images) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.member = member;
        this.images = images;
    }

    public void addImage(UploadImage image) {
        this.images.add(image);

        if (image.getItem() != this)
            image.setItem(this);
    }


}
