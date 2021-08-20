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

    private String price;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Item(String title, String price, String description, Member member) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.member = member;
    }


    @OneToMany(mappedBy = "item")
    private List<UploadImage> images = new ArrayList<>();

}
