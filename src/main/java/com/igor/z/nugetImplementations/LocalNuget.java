package com.igor.z.nugetImplementations;

import com.igor.z.daos.FeedDao;
import com.igor.z.daos.PackageDao;
import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.springutils.NuGetPackageInfo;
import com.igor.z.springutils.PackageInfoReader;
import com.igor.z.utils.NuGetCommandsWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocalNuget implements Nuget {

    private INuGetCommandsWrapper wrapper;

    public LocalNuget() {
        wrapper = new NuGetCommandsWrapper();
    }

    @Override
    public String addFeedSource(FeedDao feedDao, FeedItem feedItem) {
        List<String> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        if (wrapper.createNewFeed(feedItem, result) == 0){
            builder.append("Feed ").append(feedItem.getFeedName()).append(" added successfully!");
            List<FeedItem> feeds = new ArrayList<>();
            if (wrapper.getFeedList(feeds, new ArrayList<>()) == 0){
                List<FeedItem> added = feeds.stream().filter(item -> item.getFeedName().equals(feedItem.getFeedName()))
                        .collect(Collectors.toList());
                File sourceFolder = new File(added.get(0).getFeedSource());
                if (!sourceFolder.exists())
                    if (!sourceFolder.exists())
                        sourceFolder.mkdirs();
            }
            return feedDao.insert(feedItem);
        } else {
            result.forEach(line -> builder.append(line).append(" "));
        }
        return "Smth went wrong!";
    }

    @Override
    public String modifyFeedSource(FeedDao feedDao, FeedItem feedItem, Integer id) {
        List<String> result = new ArrayList<>();
        FeedItem currentFeed = feedDao.findByFeedItemId(id);
        int exitCode = wrapper.modifyFeed(currentFeed, feedItem, result);
        if (exitCode != 0) {
            StringBuilder builder = new StringBuilder();
            for (String message : result) {
                builder.append(message);
            }
            return builder.toString();
        }
        return feedDao.update(feedItem, id);
    }

    @Override
    public String deleteFeedSource(FeedDao feedDao, int id) {
        List<String> result = new ArrayList<>();
        FeedItem currentFeed = feedDao.findByFeedItemId(id);
        int exitCode = wrapper.removeFeed(currentFeed, result);
        if (exitCode == 0){
            return feedDao.deleteById(id);
        }
        StringBuilder builder = new StringBuilder();
        result.forEach(line -> builder.append(line).append(" "));
        return "Smth went wrong!";
    }

    @Override
    public List<FeedItem> getAllFeeds(FeedDao feedDao) {
        return feedDao.getAll();
    }

    @Override
    public FeedItem getFeedById(FeedDao feedDao, int feedId) {
        return feedDao.findByFeedItemId(feedId);
    }

    @Override
    public String addPackageToSource(String packagePath, FeedItem feed, PackageDao packageDao) {
        List<String> result = new ArrayList<>();
        List<FeedItem> feeds = new ArrayList<>();
        wrapper.getFeedList(feeds, result);
        FeedItem realFeed = feeds.stream().filter(f -> f.getFeedName().equals(feed.getFeedName())).findAny().orElse(null);
        if (realFeed != null){
            if ( wrapper.addPackageToFeed(packagePath, realFeed.getFeedSource(), result) == 0){
                PackageInfoReader reader = new PackageInfoReader();
                NuGetPackageInfo info = reader.readPackage(packagePath);
                return packageDao.insert(info, feed.getFeedSource());
            }
        }
        return "Smth went wrong!";
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
        return "Synchronisation for LOCAL_NUGET has not implemented yet!";
    }
}
