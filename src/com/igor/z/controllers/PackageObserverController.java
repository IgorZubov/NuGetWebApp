package com.igor.z.controllers;

import com.igor.z.interfaces.IPackageObserverModel;
import com.igor.z.models.PackageObserverModel;
import com.igor.z.utils.Consts;
import com.igor.z.utils.NuGetCommandsWrapper;
import com.igor.z.utils.PackageInfo;
import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.utils.SettingsReader;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
@ViewScoped
public class PackageObserverController {
    private List<PackageInfo> packages = new ArrayList<>();
    private String searchExp;
    private IPackageObserverModel model;


    public PackageObserverController(){
        this(new PackageObserverModel(new NuGetCommandsWrapper()));
    }

    public PackageObserverController(IPackageObserverModel model){
        this.model = model;
        List<PackageInfo> result = (List<PackageInfo>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(Consts.RESULT);
        String search = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(Consts.SEARCH);
        if (search != null)
            searchExp = search;
        if (result != null){
            packages.addAll(result);
        }
    }

    public String goBack(){
        return "/user/feedmanager?faces-redirect=true";
    }

    public String search(){
        List<PackageInfo> result = model.search(searchExp);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.RESULT, result);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(Consts.SEARCH, searchExp);
        return "/user/packageobserver?faces-redirect=true";
    }

    //GETTERS AND SETTERS
    public String getSearchExp() {
        return searchExp;
    }
    public void setSearchExp(String searchExp) {
        this.searchExp = searchExp;
    }
    public List<PackageInfo> getPackages() {
        return packages;
    }
    public void setPackages(List<PackageInfo> packages) {
        this.packages = packages;
    }

}
