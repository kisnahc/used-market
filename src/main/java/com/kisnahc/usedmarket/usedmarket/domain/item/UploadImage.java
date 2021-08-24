package com.kisnahc.usedmarket.usedmarket.domain.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UploadImage {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false)
    private String uploadImageName;

    @Column(nullable = false)
    private String saveImageName;

    @Builder
    public UploadImage(String uploadImageName, String saveImageName) {
        this.uploadImageName = uploadImageName;
        this.saveImageName = saveImageName;
    }

    public void setItem(Item item) {
        this.item = item;
        if (!item.getImages().contains(this))
            item.getImages().add(this);
    }
}
