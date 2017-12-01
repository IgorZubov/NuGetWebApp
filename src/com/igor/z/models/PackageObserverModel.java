package com.igor.z.models;

import com.igor.z.daos.PackageDao;
import com.igor.z.interfaces.IPackageObserverModel;
import com.igor.z.interfaces.ISettingsReader;
import com.igor.z.modelAttributes.SearchExpression;
import com.igor.z.nugetImplementations.Nuget;
import com.igor.z.nugetImplementations.NugetFactory;
import com.igor.z.nugetImplementations.NugetImplementation;
import com.igor.z.springutils.NuGetPackageInfo;
import com.igor.z.utils.SettingsReader;

import java.util.ArrayList;
import java.util.List;

public class PackageObserverModel implements IPackageObserverModel {

    private final PackageDao packageDao;
    private Nuget nuget;

    public PackageObserverModel(PackageDao packageDao) {
        this.packageDao = packageDao;
        ISettingsReader reader = new SettingsReader();
        NugetImplementation impl = reader.getNuGetImplementation();
        nuget = new NugetFactory().getNugetImplementation(impl);
    }

    @Override
    public List<NuGetPackageInfo> search(SearchExpression searchExp) {
        if (searchExp.getSearchExpression().isEmpty() && searchExp.getFeedSource().isEmpty())
            return nuget.getAllPackages(packageDao);
        if (!searchExp.getSearchExpression().isEmpty() && searchExp.getFeedSource().isEmpty())
            return nuget.searchForPackagesInEveryFeed(searchExp.getSearchExpression(), packageDao);
        if (searchExp.getSearchExpression().isEmpty() && !searchExp.getFeedSource().isEmpty())
            return nuget.getAllPackagesFromFeed(searchExp.getFeedSource(), packageDao);
        if (!searchExp.getSearchExpression().isEmpty() && !searchExp.getFeedSource().isEmpty())
            return nuget.searchForPackagesInExactFeed(searchExp.getFeedSource(), searchExp.getSearchExpression(), packageDao);
        return new ArrayList<>();
    }
}
