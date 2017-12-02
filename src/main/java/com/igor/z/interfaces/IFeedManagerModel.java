package com.igor.z.interfaces;

import com.igor.z.modelAttributes.FeedItem;

import java.util.List;

public interface IFeedManagerModel {
    String removeFeed(int feed);
    List<FeedItem> getFeedList();
    FeedItem getFeedById(int feed);
}
