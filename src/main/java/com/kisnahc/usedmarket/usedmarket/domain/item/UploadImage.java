package com.kisnahc.usedmarket.usedmarket.domain.item;

import com.kisnahc.usedmarket.usedmarket.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UploadImage extends BaseTimeEntity {

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

    public UploadImage(String uploadImageName, String saveImageName) {
        this.uploadImageName = uploadImageName;
        this.saveImageName = saveImageName;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
