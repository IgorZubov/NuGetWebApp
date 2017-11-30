package com.igor.z.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPackageUploaderModel {

    String uploadPackageToTempFolder(MultipartFile file) throws IOException;
    String addPackageToFeed(String packagePath, String selectedFee);
    void deleteTempFile(String packagePath);
}
