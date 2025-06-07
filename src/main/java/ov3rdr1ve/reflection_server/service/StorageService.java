package ov3rdr1ve.reflection_server.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    /*
     * Saves an image uploaded by the user and returns the image URL as a String
     */
    public String storeImage(MultipartFile image);

}
