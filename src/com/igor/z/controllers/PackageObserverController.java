package com.igor.z.controllers;

import com.igor.z.daos.JdbcFeedDao;
import com.igor.z.daos.JdbcPackageDao;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.modelAttributes.SearchExpression;
import com.igor.z.models.PackageObserverModel;
import com.igor.z.springutils.NuGetPackageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PackageObserverController {
    private List<String> sourceList;

    @Autowired
    private JdbcFeedDao feedDao;

    @Autowired
    private JdbcPackageDao packageDao;


    @RequestMapping(value = "/user/packageobserver", method = RequestMethod.GET)
    public ModelAndView showForm(Model model, @ModelAttribute("searchExp") SearchExpression searchExp) {
        List<FeedItem> feeds = feedDao.getAll();
        sourceList = feeds.stream().map(feedItem -> feedItem.getFeedName()).collect(Collectors.toList());
        model.addAttribute("sourceList", sourceList);
        return new ModelAndView("user/packageobserver", "searchExp", new SearchExpression());
    }

    @RequestMapping(value = "/user/packageobserver", method = RequestMethod.POST)
    public ModelAndView searchForPackages(@ModelAttribute("searchExp") SearchExpression searchExp,
                                          Model model) {
        PackageObserverModel packageObserverModel = new PackageObserverModel(packageDao);
        List<NuGetPackageInfo> packages = packageObserverModel.search(searchExp);
        model.addAttribute("packages", packages);
        model.addAttribute("sourceList", sourceList);
        return new ModelAndView("user/packageobserver", "searchExp", searchExp);
    }

}
