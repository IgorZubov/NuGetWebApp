package com.igor.z.models;

import com.igor.z.daos.PackageDao;
import com.igor.z.interfaces.ISyncModel;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.nugetImplementations.Nuget;
import com.igor.z.nugetImplementations.NugetFactory;
import com.igor.z.nugetImplementations.NugetImplementation;
import com.igor.z.utils.SettingsReader;

public class SyncModel implements ISyncModel {
    private PackageDao packageDao;
    private FeedItem item;
    private Nuget nuget;

    public SyncModel(PackageDao packageDao, FeedItem item) {
        this.packageDao = packageDao;
        this.item = item;
        NugetFactory factory = new NugetFactory();
        SettingsReader reader = new SettingsReader();
        NugetImplementation impl = reader.getNuGetImplementation();
        nuget = factory.getNugetImplementation(impl);
    }

    @Override
    public String Sync() {
        return nuget.syncFeed(packageDao, item);
    }
}
