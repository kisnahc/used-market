package com.kisnahc.usedmarket.usedmarket.domain.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class ImageHandler {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String imageName) {
        return fileDir + imageName;
    }

    public List<UploadImage> saveFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadImage> saveFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                saveFileResult.add(saveFile(multipartFile));
            }
        }
        return saveFileResult;
    }

    public UploadImage saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String uploadImageName = multipartFile.getOriginalFilename();
        String saveImageName = createSaveFilename(uploadImageName);
        multipartFile.transferTo(new File(getFullPath(saveImageName)));


        return new UploadImage(uploadImageName, saveImageName);
    }

    private String createSaveFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
