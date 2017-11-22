package com.igor.z.controllers;


import com.igor.z.interfaces.IFeedEditorModel;
import com.igor.z.models.FeedEditorModel;
import com.igor.z.utils.Consts;
import com.igor.z.utils.FeedItem;
import com.igor.z.utils.NuGetCommandsWrapper;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
@ViewScoped
public class FeedEditorController {
    private FeedItem feed;
    private String resultMessage;
    private String newName;
    private String newSource;
    private IFeedEditorModel model;

    public FeedEditorController(){
        this(new FeedEditorModel(new NuGetCommandsWrapper()));
    }

    public FeedEditorController(IFeedEditorModel model){
        this.model = model;
    }

    @PostConstruct
    public void FeedEditor() {
        FeedItem tmp = (FeedItem) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(Consts.FEED);
        if (tmp != null){
            feed = tmp;
            newName = feed.getName();
            newSource = feed.getSource();
        }
        String result = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(Consts.RESULT);
        if (result != null)
            resultMessage = result;
    }

    public String submit(String name, String source) {
        FeedItem editedFeed = new FeedItem(name, source);
        String result = model.modifyFeed(feed, editedFeed);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.RESULT, result);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.FEED, editedFeed);
        return "/user/fideditor?faces-redirect=true";
    }

    public String cancel(){
        return "/user/feedmanager?faces-redirect=true";
    }

    //GETTERS AND SETTERS
    public String getResultMessage(){return resultMessage; }
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    public String getNewName() { return newName; }
    public void setNewName(String newName) {
        this.newName = newName;
    }
    public String getNewSource() {
        return newSource;
    }
    public void setNewSource(String newSource) {
        this.newSource = newSource;
    }
}
