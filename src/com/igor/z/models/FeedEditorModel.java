package com.igor.z.models;

import com.igor.z.daos.FeedDao;
import com.igor.z.interfaces.IFeedEditorModel;
import com.igor.z.interfaces.ISettingsReader;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.nugetImplementations.Nuget;
import com.igor.z.nugetImplementations.NugetFactory;
import com.igor.z.nugetImplementations.NugetImplementation;
import com.igor.z.utils.SettingsReader;

public class FeedEditorModel implements IFeedEditorModel {

    private final FeedDao feedDao;
    private Nuget nuget;

    public FeedEditorModel(FeedDao feedDao) {
        this.feedDao = feedDao;
        ISettingsReader reader = new SettingsReader();
        NugetImplementation impl = reader.getNuGetImplementation();
        nuget = new NugetFactory().getNugetImplementation(impl);
    }

    @Override
    public String modifyFeed(FeedItem feed, Integer id) {
        return nuget.modifyFeedSource(feedDao, feed, id);
    }

    public String addFeed(FeedItem feed) {
        return nuget.addFeedSource(feedDao, feed);
    }
}
