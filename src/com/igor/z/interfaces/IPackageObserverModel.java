package com.igor.z.interfaces;

import com.igor.z.utils.PackageInfo;

import java.util.List;

public interface IPackageObserverModel {
    List<PackageInfo> search(String searchExp);
}
