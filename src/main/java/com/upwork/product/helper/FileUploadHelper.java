package com.upwork.product.helper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadHelper {

//    private static final String UPLOAD_DIR;
//
//    static {
//        try {
//            UPLOAD_DIR = new ClassPathResource("static/").getFile().getAbsolutePath();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public String uploadFile(MultipartFile file) {
        try {
            URL aStatic = getClass().getClassLoader().getResource("static");

            String filePath = aStatic.getPath() + File.separator + "images" + File.separator + file.getOriginalFilename();
            Path fileName = getFileName(filePath);
            Files.copy(file.getInputStream(), fileName, StandardCopyOption.REPLACE_EXISTING);
            return "/images" + File.separator + file.getOriginalFilename();
        } catch (Exception e) {
            throw new RuntimeException("Error while upload image");
        }
    }

    public boolean deleteFile(String path) {
        try {
            URL aStatic = getClass().getClassLoader().getResource("static");
            String filePath = aStatic.getPath() + File.separator + path;
            Files.delete(getFileName(filePath));
        } catch (Exception ex) {
            throw new RuntimeException("Error while deleting image");
        }
        return false;
    }

    private Path getFileName(String filePath) {
        return Paths.get(filePath);
    }
}
