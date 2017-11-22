package com.igor.z.controllers;

import com.igor.z.interfaces.IPackageUploaderModel;
import com.igor.z.models.PackageUploaderModel;
import com.igor.z.utils.Consts;
import com.igor.z.utils.FeedItem;
import com.igor.z.utils.NuGetCommandsWrapper;
import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.utils.SettingsReader;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
@ViewScoped
public class PackageUploaderController {
    private Part file;
    private String resultMessage;
    private List<FeedItem> feeds = new ArrayList<>();
    private String selectedFeed;
    private IPackageUploaderModel model;

    public PackageUploaderController(){
        this(new PackageUploaderModel(new NuGetCommandsWrapper()));
    }

    public PackageUploaderController(IPackageUploaderModel model){
        this.model = model;
        String resultMessage = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash()
                .get(Consts.RESULT);
        List<FeedItem> tmpFeeds = (List<FeedItem>) FacesContext.getCurrentInstance().getExternalContext().getFlash()
                .get(Consts.FEEDS);
        String tmpSelectedFeed = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash()
                .get(Consts.FEED);
        if (resultMessage != null)
            this.resultMessage = resultMessage;
        if (tmpSelectedFeed != null)
            selectedFeed = tmpSelectedFeed;
        if (tmpFeeds != null)
            feeds.addAll(tmpFeeds);
    }

    public String addPackage() {
        String packagePath;
        try {
            packagePath = model.uploadPackageToTempFolder(file);
        } catch (IOException e) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.RESULT, "Error occurred in uploading file.");
            return "/user/packageuploader?faces-redirect=true";
        }
        String result = model.addPackageToFeed(packagePath, selectedFeed);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.RESULT, result);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.FEEDS, feeds);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.FEED, selectedFeed);
        model.deleteTempFile(packagePath);
        return "/user/packageuploader?faces-redirect=true";
    }

    public String cancel(){
        return "/user/feedmanager?faces-redirect=true";
    }

    //GETTERS AND SETTERS
    public String getSelectedFeed() {
        return selectedFeed;
    }
    public void setSelectedFeed(String selectedFeed) {
        this.selectedFeed = selectedFeed;
    }
    public Part getFile() {
        return file;
    }
    public void setFile(Part file) {
        this.file = file;
    }
    public String getResultMessage() {
        return resultMessage;
    }
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    public List<FeedItem> getFeeds() {
        return feeds;
    }
    public void setFeeds(List<FeedItem> feeds) {
        this.feeds = feeds;
    }
}
