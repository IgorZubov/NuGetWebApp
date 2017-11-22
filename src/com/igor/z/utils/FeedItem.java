package com.igor.z.utils;

public class FeedItem {
    private String name;
    private String source;

    public FeedItem(String name, String source){
        this.name = name;
        this.source = source;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!FeedItem.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final FeedItem other = (FeedItem) obj;
        return other.name.equals(this.name) && other.source.equals(this.source);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
}
