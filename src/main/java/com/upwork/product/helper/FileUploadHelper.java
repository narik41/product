package com.upwork.product.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    public final String UPLOAD_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();

    public FileUploadHelper() throws IOException {
    }

    public String uploadFile(MultipartFile file) {

        String filePath ="";
        try {
            filePath = UPLOAD_DIR +File.separator + file.getOriginalFilename();
            Path fileName = getFileName(filePath);
            Files.copy(file.getInputStream(), fileName, StandardCopyOption.REPLACE_EXISTING);
            return "images" +File.separator + file.getOriginalFilename();
        } catch (Exception e) {

        }
        return filePath;
    }

    public boolean deleteFile(String path){
        try{
            String filePath = UPLOAD_DIR +File.separator + path;
            Files.delete(getFileName(filePath));
        }catch (Exception ex){

        }
        return false;
    }

    private Path getFileName(String filePath) {
        return Paths.get(filePath);
    }
}
