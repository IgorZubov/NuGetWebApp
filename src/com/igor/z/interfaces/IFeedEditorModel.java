package com.igor.z.interfaces;

import com.igor.z.utils.FeedItem;

public interface IFeedEditorModel {
    String modifyFeed(FeedItem existedFeed, FeedItem editedFeed);
}
