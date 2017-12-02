package com.igor.z.models;

import com.igor.z.daos.FeedDao;
import com.igor.z.interfaces.IFeedManagerModel;
import com.igor.z.interfaces.ISettingsReader;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.nugetImplementations.Nuget;
import com.igor.z.nugetImplementations.NugetFactory;
import com.igor.z.nugetImplementations.NugetImplementation;
import com.igor.z.utils.SettingsReader;

import java.util.List;

public class FeedManagerModel implements IFeedManagerModel {

    private final FeedDao feedDao;
    private Nuget nuget;

    public FeedManagerModel(FeedDao feedDao) {
        this.feedDao = feedDao;
        ISettingsReader reader = new SettingsReader();
        NugetImplementation impl = reader.getNuGetImplementation();
        nuget = new NugetFactory().getNugetImplementation(impl);
    }

    @Override
    public String removeFeed(int feed) {
        return nuget.deleteFeedSource(feedDao, feed);
    }

    @Override
    public List<FeedItem> getFeedList() {
        return nuget.getAllFeeds(feedDao);
    }

    @Override
    public FeedItem getFeedById(int feed) {
        return nuget.getFeedById(feedDao, feed);
    }
}
