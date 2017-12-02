package com.igor.z.controllers;

import com.igor.z.daos.JdbcFeedDao;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.models.FeedManagerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class FeedManagerController {

    @Autowired
    private JdbcFeedDao feedDao;

    @RequestMapping(value = "/user/feedmanager")
    public ModelAndView showForm(Model model) {
        FeedManagerModel feedManagerModel = new FeedManagerModel(feedDao);
        List<FeedItem> list = feedManagerModel.getFeedList();
        model.addAttribute("feeds", list);
        return new ModelAndView("user/feedmanager", "feedItem", new FeedItem());
    }

    @RequestMapping(value = "/user/feedmanager/{id}/delete", method = RequestMethod.POST)
    public String deleteFeed(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
        FeedManagerModel feedManagerModel = new FeedManagerModel(feedDao);
        String msg = feedManagerModel.removeFeed(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/user/feedmanager";
    }

    @RequestMapping(value = "/user/feedmanager/{id}/update", method = RequestMethod.POST)
    public ModelAndView updateFeed(@PathVariable("id") int id) {
        FeedManagerModel feedManagerModel = new FeedManagerModel(feedDao);
        FeedItem item = feedManagerModel.getFeedById(id);
        return new ModelAndView("user/feed", "feedItem", item);
    }

    @RequestMapping(value = "/user/feedmanager/{id}/sync", method = RequestMethod.POST)
    public String syncFeed(@PathVariable("id") int id, ModelMap model,
                                 final RedirectAttributes redirectAttributes) {
        FeedManagerModel feedManagerModel = new FeedManagerModel(feedDao);
        FeedItem item = feedManagerModel.getFeedById(id);
        redirectAttributes.addFlashAttribute("feedItem", item);
        return "redirect:/user/sync";
//        return new ModelAndView("user/sync", "feedItem", item);
    }
}
