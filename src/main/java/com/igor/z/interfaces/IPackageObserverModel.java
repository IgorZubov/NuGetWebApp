package com.igor.z.interfaces;

import com.igor.z.modelAttributes.SearchExpression;
import com.igor.z.springutils.NuGetPackageInfo;

import java.util.List;

public interface IPackageObserverModel {
    List<NuGetPackageInfo> search(SearchExpression searchExp);
}
