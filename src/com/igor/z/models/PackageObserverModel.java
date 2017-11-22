package com.igor.z.models;

import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.interfaces.IPackageObserverModel;
import com.igor.z.utils.PackageInfo;

import java.util.ArrayList;
import java.util.List;

public class PackageObserverModel implements IPackageObserverModel {

    private final INuGetCommandsWrapper wrapper;

    public PackageObserverModel(INuGetCommandsWrapper nuGetWrapper){
        wrapper = nuGetWrapper;
    }


    @Override
    public List<PackageInfo> search(String searchExp) {
        List<String> errorMessage = new ArrayList<>();
        List<PackageInfo> res = new ArrayList<>();
        wrapper.searchPackage(searchExp != null ? searchExp : "",res , errorMessage);
        return res;
    }
}
