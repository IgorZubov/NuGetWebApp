package com.igor.z.modelAttributes;

public class FeedItem {

    private Integer id;
    private String feedName;
    private String feedSource;
    private String apiKey;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!FeedItem.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final FeedItem other = (FeedItem) obj;
        return other.feedName.equals(this.feedName) && other.feedSource.equals(this.feedSource);
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getFeedSource() {
        return feedSource;
    }

    public void setFeedSource(String feedSource) {
        this.feedSource = feedSource;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isNew() {
        return (this.id == null);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
