package com.igor.z.nugetImplementations;

import com.igor.z.daos.PackageDao;
import com.igor.z.springutils.NuGetPackageInfo;

import java.util.List;

public interface Nuget {
    String addFeedSource();
    String modifyFeedSource();
    String deleteFeedSource();
    String addPackageToSource(String packagePath, String selectedFeed, PackageDao packageDao);
    List<NuGetPackageInfo> searchForPackages(String searchExp, PackageDao packageDao);
    List<NuGetPackageInfo> getAll(PackageDao packageDao);
}
