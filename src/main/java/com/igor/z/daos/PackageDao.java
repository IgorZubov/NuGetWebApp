package com.igor.z.daos;

import com.igor.z.springutils.NuGetPackageInfo;

import java.util.List;

public interface PackageDao {
    String insert(NuGetPackageInfo packageInfo, String feed);
    List<NuGetPackageInfo> findByAny(String searchExp);
    List<NuGetPackageInfo> getAll();
    List<NuGetPackageInfo> getAllPackagesFromFeed(String feedSource);
    List<NuGetPackageInfo> findByAnyFromFeed(String feedSource, String searchExpression);
    void deleteAll();
}
