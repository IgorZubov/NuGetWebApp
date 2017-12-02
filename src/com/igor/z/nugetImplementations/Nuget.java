package com.igor.z.nugetImplementations;

import com.igor.z.daos.FeedDao;
import com.igor.z.daos.PackageDao;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.springutils.NuGetPackageInfo;

import java.util.List;

public interface Nuget {
    String addFeedSource(FeedDao feedDao, FeedItem feedItem);
    String modifyFeedSource(FeedDao feedDao, FeedItem feedItem, Integer id);
    String deleteFeedSource(FeedDao feedDao, int id);
    List<FeedItem> getAllFeeds(FeedDao feedDao);
    FeedItem getFeedById(FeedDao feedDao, int feedId);
    String addPackageToSource(String packagePath, FeedItem feed, PackageDao packageDao);
    List<NuGetPackageInfo> searchForPackagesInEveryFeed(String searchExp, PackageDao packageDao);
    List<NuGetPackageInfo> getAllPackages(PackageDao packageDao);
    List<NuGetPackageInfo> getAllPackagesFromFeed(String feedSource, PackageDao packageDao);
    List<NuGetPackageInfo> searchForPackagesInExactFeed(String feedSource, String searchExpression, PackageDao packageDao);
    String syncFeed(PackageDao packageDao, FeedItem feed);
}
