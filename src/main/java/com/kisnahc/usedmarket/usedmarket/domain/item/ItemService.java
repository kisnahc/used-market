package com.kisnahc.usedmarket.usedmarket.domain.item;

import com.kisnahc.usedmarket.usedmarket.domain.member.CurrentMember;
import com.kisnahc.usedmarket.usedmarket.domain.member.Member;
import com.kisnahc.usedmarket.usedmarket.web.form.CreateItemForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ImageHandler imageHandler;

    public Item createItem(CreateItemForm form, @CurrentMember Member member) throws IOException {


        List<UploadImage> saveImages = imageHandler.saveFiles(form.getImages());

        Item item = Item.builder()
                .title(form.getTitle())
                .price(form.getPrice())
                .description(form.getDescription())
                .images(saveImages)
                .member(member)
                .build();

        return itemRepository.save(item);
    }
}
