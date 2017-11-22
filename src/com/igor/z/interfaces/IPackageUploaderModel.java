package com.igor.z.interfaces;

import javax.servlet.http.Part;
import java.io.IOException;

public interface IPackageUploaderModel {

    String uploadPackageToTempFolder(Part file) throws IOException;
    String addPackageToFeed(String packagePath, String selectedFee);
    void deleteTempFile(String packagePath);
}
