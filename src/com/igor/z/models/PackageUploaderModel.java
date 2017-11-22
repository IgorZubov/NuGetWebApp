package com.igor.z.models;

import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.interfaces.IPackageUploaderModel;
import com.igor.z.utils.SettingsReader;

import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PackageUploaderModel implements IPackageUploaderModel {

    private INuGetCommandsWrapper wrapper;

    public PackageUploaderModel(INuGetCommandsWrapper nuGetCommandsWrapper){
        wrapper = nuGetCommandsWrapper;
    }

    @Override
    public String addPackageToFeed(String packagePath, String selectedFeed) {
        List<String> result = new ArrayList<>();
        wrapper.addPackageToFeed(packagePath, selectedFeed, result);
        StringBuilder builder = new StringBuilder();
        result.stream().forEach(line -> builder.append(line).append(" "));
        return builder.toString();
    }

    @Override
    public void deleteTempFile(String packagePath) {
        File delFile = new File(packagePath);
        delFile.delete();
    }

    @Override
    public String uploadPackageToTempFolder(Part file) throws IOException {
        return WriteFileToServer(file);
    }

    private String WriteFileToServer(Part file) throws IOException {

        File outputFile = new File(getUploadDirectory() + File.separator + file.getSubmittedFileName());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException exception){
            throw exception;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return outputFile.getAbsolutePath();
    }

    private String getUploadDirectory()
    {
        SettingsReader reader = new SettingsReader();
        String tmpFolder = reader.getTmpUploadPath();
        File fileSaveDir = new File(tmpFolder);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        return tmpFolder;
    }
}
