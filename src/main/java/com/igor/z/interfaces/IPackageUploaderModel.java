package com.igor.z.interfaces;

import com.igor.z.modelAttributes.FeedItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPackageUploaderModel {

    String uploadPackageToTempFolder(MultipartFile file) throws IOException;
    String addPackageToFeed(String packagePath, FeedItem feed);
    void deleteTempFile(String packagePath);
}
