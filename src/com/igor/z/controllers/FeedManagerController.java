package com.igor.z.controllers;


import com.igor.z.interfaces.IFeedManagerModel;
import com.igor.z.models.FeedManagerModel;
import com.igor.z.utils.Consts;
import com.igor.z.utils.FeedItem;
import com.igor.z.utils.NuGetCommandsWrapper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@SessionScoped
@ViewScoped
public class FeedManagerController {
    private String feedName;
    private String feedSource;
    private String resultMessage;
    private List<FeedItem> feeds = new ArrayList<>();
    private IFeedManagerModel model;

    public FeedManagerController() {
        this(new FeedManagerModel(new NuGetCommandsWrapper()));
    }

    public FeedManagerController(IFeedManagerModel model) {
        this.model = model;
        String resultMessage = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(Consts.RESULT);
        if (resultMessage != null)
            this.resultMessage = resultMessage;
        feeds.addAll(model.getFeedList());
    }

    public String addFeed() {
        FeedItem f = new FeedItem(feedName.trim(), feedSource.trim());
        String result = model.addFeed(f);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.RESULT,
                result);
        return "/user/feedmanager?faces-redirect=true";
    }

    public String removeFeed(FeedItem feed) {
        String result = model.removeFeed(feed);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.RESULT,result);
        return "/user/feedmanager?faces-redirect=true";
    }

    public String editFeed(FeedItem feed){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.FEED, feed);
        return "/user/fideditor?faces-redirect=true";
    }

    public String uploadPackage(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.FEEDS, feeds);
        return "/user/packageuploader?faces-redirect=true";
    }

    public String searchPackages(){
        return "/user/packageobserver?faces-redirect=true";
    }

    //GETTERS AND SETTERS
    public String getResultMessage(){return resultMessage; }
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    public String getFeedName() { return feedName; }
    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }
    public List<FeedItem> getFeeds() {
        return feeds;
    }
    public void setFeeds(List<FeedItem> feeds) {
        this.feeds = feeds;
    }
    public String getFeedSource() {
        return feedSource;
    }
    public void setFeedSource(String feedSource) {
        this.feedSource = feedSource;
    }
}
