package com.kisnahc.usedmarket.usedmarket.domain.item;

import com.kisnahc.usedmarket.usedmarket.domain.member.CurrentMember;
import com.kisnahc.usedmarket.usedmarket.domain.member.Member;
import com.kisnahc.usedmarket.usedmarket.web.form.CreateItemForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UploadImageRepository uploadImageRepository;
    private final ImageHandler imageHandler;

    public Item createItem(CreateItemForm form, @CurrentMember Member member) throws IOException {

        Item item = Item.builder()
                .title(form.getTitle())
                .price(form.getPrice())
                .description(form.getDescription())
                .member(member)
                .build();

        List<UploadImage> saveImages = imageHandler.saveFiles(form.getImages());

        for (UploadImage saveImage : saveImages) {
            UploadImage uploadImage = uploadImageRepository.save(saveImage);
            uploadImage.setItem(item);
        }
        return itemRepository.save(item);
    }
}
