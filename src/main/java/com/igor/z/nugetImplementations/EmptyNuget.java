package com.igor.z.nugetImplementations;

import com.igor.z.daos.FeedDao;
import com.igor.z.daos.PackageDao;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.springutils.NuGetPackageInfo;
import com.igor.z.springutils.PackageInfoReader;

import java.util.List;

public class EmptyNuget implements Nuget {
    @Override
    public String addFeedSource(FeedDao feedDao, FeedItem feedItem) {
        return feedDao.insert(feedItem);
    }

    @Override
    public String modifyFeedSource(FeedDao feedDao, FeedItem feedItem, Integer id) {
        return feedDao.update(feedItem, id);
    }

    @Override
    public String deleteFeedSource(FeedDao feedDao, int id) {
        return feedDao.deleteById(id);
    }

    @Override
    public String addPackageToSource(String packagePath, FeedItem feed, PackageDao packageDao) {
        PackageInfoReader reader = new PackageInfoReader();
        NuGetPackageInfo info = reader.readPackage(packagePath);
        return packageDao.insert(info, feed.getFeedSource());
    }

    @Override
    public List<NuGetPackageInfo> searchForPackagesInEveryFeed(String searchExp, PackageDao packageDao) {
        return packageDao.findByAny(searchExp);
    }

    @Override
    public List<NuGetPackageInfo> getAllPackages(PackageDao packageDao) {
        return packageDao.getAll();
    }

    @Override
    public List<NuGetPackageInfo> getAllPackagesFromFeed(String feedSource, PackageDao packageDao) {
        return packageDao.getAllPackagesFromFeed(feedSource);
    }

    @Override
    public List<NuGetPackageInfo> searchForPackagesInExactFeed(String feedSource, String searchExpression, PackageDao packageDao) {
        return packageDao.findByAnyFromFeed(feedSource, searchExpression);
    }

    @Override
    public String syncFeed(PackageDao packageDao, FeedItem feed) {
        return "Synchronisation for EMPTY_NUGET is unnecessary!";
    }

    @Override
    public List<FeedItem> getAllFeeds(FeedDao feedDao) {
        return feedDao.getAll();
    }

    @Override
    public FeedItem getFeedById(FeedDao feedDao, int feedId) {
        return feedDao.findByFeedItemId(feedId);
    }
}
