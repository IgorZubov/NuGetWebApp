package com.igor.z.interfaces;


import com.igor.z.modelAttributes.FeedItem;

public interface IFeedEditorModel {
    String modifyFeed(FeedItem feed, Integer id);
    String addFeed(FeedItem feed);
}
