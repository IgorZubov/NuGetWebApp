package com.igor.z.daos;

import com.igor.z.modelAttributes.FeedItem;

import java.util.List;

public interface FeedDao {
    String insert(FeedItem feed);
    FeedItem findByFeedItemId(int feedId);
    String update(FeedItem feed, int feedId);
    String deleteById(int id);
    List<FeedItem> getAll();
}
