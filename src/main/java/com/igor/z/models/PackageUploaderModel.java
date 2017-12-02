package com.igor.z.models;

import com.igor.z.daos.PackageDao;
import com.igor.z.interfaces.IPackageUploaderModel;
import com.igor.z.interfaces.ISettingsReader;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.nugetImplementations.Nuget;
import com.igor.z.nugetImplementations.NugetFactory;
import com.igor.z.nugetImplementations.NugetImplementation;
import com.igor.z.utils.SettingsReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class PackageUploaderModel implements IPackageUploaderModel {

    private Nuget nuget;
    private PackageDao packageDao;

    public PackageUploaderModel(PackageDao packageDao){
        this.packageDao = packageDao;
        ISettingsReader reader = new SettingsReader();
        NugetImplementation impl = reader.getNuGetImplementation();
        nuget = new NugetFactory().getNugetImplementation(impl);
    }

    @Override
    public String addPackageToFeed(String packagePath, FeedItem selectedFeed) {
        return nuget.addPackageToSource(packagePath, selectedFeed, packageDao);
    }

    @Override
    public void deleteTempFile(String packagePath) {
        File delFile = new File(packagePath);
        delFile.delete();
    }

    @Override
    public String uploadPackageToTempFolder(MultipartFile file) throws IOException {
        return WriteFileToServer(file);
    }

    private String WriteFileToServer(MultipartFile file) throws IOException {
        File outputFile = new File(getUploadDirectory() + File.separator + file.getOriginalFilename());
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
