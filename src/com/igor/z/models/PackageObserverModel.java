package com.igor.z.models;

import com.igor.z.daos.PackageDao;
import com.igor.z.interfaces.IPackageObserverModel;
import com.igor.z.interfaces.ISettingsReader;
import com.igor.z.nugetImplementations.Nuget;
import com.igor.z.nugetImplementations.NugetFactory;
import com.igor.z.nugetImplementations.NugetImplementation;
import com.igor.z.springutils.NuGetPackageInfo;
import com.igor.z.utils.SettingsReader;

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
    public List<NuGetPackageInfo> search(String searchExp) {
        if (searchExp.isEmpty())
            return nuget.getAll(packageDao);
        return nuget.searchForPackages(searchExp, packageDao);
    }
}
