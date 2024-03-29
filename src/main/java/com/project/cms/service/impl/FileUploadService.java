package com.project.cms.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@PropertySource("classpath:project_file.properties")
public class FileUploadService {
    @Value("${server.path}")
    private String SERVER_PATH;

    public String singleFileUpload(MultipartFile file, String folder) {

        File path = new File(SERVER_PATH + folder);
        if (folder == null)
            folder = "";


        if (!path.exists())
            path.mkdirs();

        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf('.') + 1);
        System.out.println(extension);

        filename = UUID.randomUUID() + "." + extension;
        System.out.println(filename);

        try {
            Files.copy(file.getInputStream(), Paths.get(SERVER_PATH + folder, filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return folder + filename;
    }

    public List<String> multipleFileUpload(List<MultipartFile> files, String folder) {
        List<String> filenames = new ArrayList<>();
       /* for (int i = 0; i < files.size(); i++) {
            String fname = singleFileUpload(files.get(i), folder);
            filenames.add(fname);
        }


        for (MultipartFile file : files) {
            String fname = singleFileUpload(file, folder);
            filenames.add(fname);
        }
*/
        files.forEach(file -> {
            filenames.add(singleFileUpload(file, folder));
        });
        return filenames;
    }
    public String upload(MultipartFile file,String folder){
        return singleFileUpload(file,folder);
    }

    public String upload(MultipartFile file){
        return singleFileUpload(file,null);
    }

    public List<String> upload(List<MultipartFile> files){
        return multipleFileUpload(files,null);
    }
}
