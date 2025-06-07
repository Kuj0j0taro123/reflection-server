package ov3rdr1ve.reflection_server.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Objects;

@Service
public class StorageServiceImpl implements StorageService{

    private final Path uploadDir;

    public StorageServiceImpl(@Value("${reflection.media.storage}") String uploadDirPath){
        this.uploadDir = Paths.get(uploadDirPath);
    }

    @PostConstruct
    public void init() throws IOException {
        if(Files.notExists(uploadDir))
            Files.createDirectories(uploadDir);
    }

    /*
    * Saves an image uploaded by the user and returns the image URL as a String
    */
    @Override
    public String storeImage(MultipartFile image) {
        // check if there is an image
        if (image == null || image.isEmpty())
            return null;

        String imageFilename = null;
        String imageUrl = null;

        String cleaned = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));

        // todo: check file extension and maybe size here

        // making the name unique
        String uniqueName = Instant.now().toEpochMilli() + "_" + cleaned;
        Path target = uploadDir.resolve(uniqueName);

        try {
            Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            imageFilename = uniqueName;

            // Build a URL for the front end to fetch the image:
            imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/images/")
                    .path(uniqueName)
                    .toUriString();

        } catch(Exception ex){ //todo: throw exceptions instead
            ex.printStackTrace();
        }

        return imageUrl;
    }
}
