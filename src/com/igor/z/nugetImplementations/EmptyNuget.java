package com.igor.z.nugetImplementations;

import com.igor.z.daos.PackageDao;
import com.igor.z.springutils.NuGetPackageInfo;
import com.igor.z.springutils.PackageInfoReader;

import java.util.List;

public class EmptyNuget implements Nuget {
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
        PackageInfoReader reader = new PackageInfoReader();
        NuGetPackageInfo info = reader.readPackage(packagePath);
        return packageDao.insert(info, selectedFeed);
    }

    @Override
    public List<NuGetPackageInfo> searchForPackages(String searchExp, PackageDao packageDao) {
        return packageDao.findByAny(searchExp);
    }

    @Override
    public List<NuGetPackageInfo> getAll(PackageDao packageDao) {
        return packageDao.getAll();
    }
}
