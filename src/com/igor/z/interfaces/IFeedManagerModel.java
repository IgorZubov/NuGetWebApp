package com.igor.z.interfaces;

import com.igor.z.utils.FeedItem;

import java.util.List;

public interface IFeedManagerModel {
    String addFeed(FeedItem feed);
    String removeFeed(FeedItem feed);
    List<FeedItem> getFeedList();
}
