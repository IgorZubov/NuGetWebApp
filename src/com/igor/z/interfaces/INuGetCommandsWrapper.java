package com.igor.z.interfaces;

import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.utils.PackageInfo;

import java.util.List;

public interface INuGetCommandsWrapper {

    int createNewFeed(FeedItem feed, List<String> returnMessage);
    int getFeedList(List<FeedItem> feedList, List<String> returnMessage);
    int removeFeed(FeedItem feed, List<String> returnMessage);
    int modifyFeed(FeedItem oldFeed, FeedItem newFeed, List<String> returnMessage);
    int addPackageToFeed(String packagePath, String selectedFeedSource, List<String> returnMessage);
    int searchPackage(String searchExp, List<PackageInfo> packageInfoList, List<String> returnMessage);
}

