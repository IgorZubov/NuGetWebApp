package com.igor.z.nugetImplementations;

import com.igor.z.daos.PackageDao;
import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.springutils.NuGetPackageInfo;
import com.igor.z.utils.NuGetCommandsWrapper;

import java.util.ArrayList;
import java.util.List;

public class LocalNuget implements Nuget {

    private INuGetCommandsWrapper wrapper;

    public LocalNuget() {
        wrapper = new NuGetCommandsWrapper();
    }

    @Override
    public String addFeedSource() {
        return null;
    }

    @Override
    public String modifyFeedSource() {
        return null;
    }

    @Override
    public String deleteFeedSource() {
        return null;
    }

    @Override
    public String addPackageToSource(String packagePath, String selectedFeed, PackageDao packageDao) {
        List<String> result = new ArrayList<>();
        wrapper.addPackageToFeed(packagePath, selectedFeed, result);
        StringBuilder builder = new StringBuilder();
        result.forEach(line -> builder.append(line).append(" "));
        return builder.toString();
    }

    @Override
    public List<NuGetPackageInfo> searchForPackages(String searchExp, PackageDao packageDao) {
        List<String> errorMessage = new ArrayList<>();
        List<NuGetPackageInfo> res = new ArrayList<>();
        //wrapper.searchPackage(searchExp != null ? searchExp : "",res , errorMessage);
        return res;
    }

    @Override
    public List<NuGetPackageInfo> getAll(PackageDao packageDao) {
        List<String> errorMessage = new ArrayList<>();
        List<NuGetPackageInfo> res = new ArrayList<>();
        //wrapper.searchPackage(searchExp != null ? searchExp : "",res , errorMessage);
        return res;
    }
}
