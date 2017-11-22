package com.igor.z.models;

import com.igor.z.interfaces.IFeedEditorModel;
import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.utils.FeedItem;

import java.util.ArrayList;
import java.util.List;

public class FeedEditorModel implements IFeedEditorModel {

    private final INuGetCommandsWrapper wrapper;

    public FeedEditorModel(INuGetCommandsWrapper nuGetWrapper){
        wrapper = nuGetWrapper;
    }

    @Override
    public String modifyFeed(FeedItem existedFeed, FeedItem editedFeed) {
        List<String> result = new ArrayList<>();
        int exitCode = wrapper.modifyFeed(existedFeed, editedFeed, result);
        if (exitCode != 0) {
            StringBuilder builder = new StringBuilder();
            for (String message : result) {
                builder.append(message);
            }
            return builder.toString();
        }
        return "Feed successfully modified.";
    }
}
