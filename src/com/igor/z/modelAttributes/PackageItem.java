package com.igor.z.modelAttributes;

import org.springframework.web.multipart.MultipartFile;

public class PackageItem {

    private MultipartFile file;
    private String source;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
