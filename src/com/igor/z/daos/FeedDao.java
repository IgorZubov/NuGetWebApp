package com.igor.z.daos;

import com.igor.z.modelAttributes.FeedItem;

import java.util.List;

public interface FeedDao {
    void insert(FeedItem feed);
    FeedItem findByFeedItemId(int feedId);
    void update(FeedItem feed);
    void deleteById(int id);
    List<FeedItem> getAll();
}
