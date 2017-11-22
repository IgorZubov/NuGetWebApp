package com.igor.z.models;

import com.igor.z.interfaces.IFeedManagerModel;
import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.utils.FeedItem;
import com.igor.z.utils.NuGetCommandsWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedManagerModel implements IFeedManagerModel {

    private INuGetCommandsWrapper wrapper;

    public FeedManagerModel(NuGetCommandsWrapper nuGetCommandsWrapper) {
        wrapper = nuGetCommandsWrapper;
    }

    @Override
    public String addFeed(FeedItem feed) {
        List<String> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        if (wrapper.createNewFeed(feed, result) == 0){
            builder.append("Feed ").append(feed.getName()).append(" added successfully!");
            List<FeedItem> feeds = new ArrayList<>();
            if (wrapper.getFeedList(feeds, new ArrayList<>()) == 0){
                List<FeedItem> added = feeds.stream().filter(item -> item.getName().equals(feed.getName()))
                        .collect(Collectors.toList());
                File sourceFolder = new File(added.get(0).getSource());
                if (!sourceFolder.exists())
                    if (!sourceFolder.exists())
                        sourceFolder.mkdirs();
            }
        } else {
            result.stream().forEach(line -> builder.append(line).append(" "));
        }
        return builder.toString().trim();
    }

    @Override
    public String removeFeed(FeedItem feed) {
        List<String> result = new ArrayList<>();
        wrapper.removeFeed(feed, result);
        StringBuilder builder = new StringBuilder();
        result.stream().forEach(line -> builder.append(line).append(" "));
        return builder.toString().trim();
    }

    @Override
    public List<FeedItem> getFeedList() {
        List<FeedItem> res = new ArrayList<>();
        wrapper.getFeedList(res, new ArrayList<>());
        return res;
    }
}
