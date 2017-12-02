package com.igor.z.controllers;

import com.igor.z.daos.JdbcPackageDao;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.models.SyncModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SyncController {
    private FeedItem item;

    @Autowired
    private JdbcPackageDao packageDao;

    @RequestMapping(value = "/user/sync", method = RequestMethod.GET)
    public ModelAndView showForm(@ModelAttribute("feedItem") FeedItem feed) {
        item  = feed;
        return new ModelAndView("user/sync", "feedItem", item);
    }

    @RequestMapping(value = "/user/sync", method = RequestMethod.POST)
    public String syncFeed(@ModelAttribute("feedItem") FeedItem feed,
                           final RedirectAttributes redirectAttributes) {
        SyncModel syncModel = new SyncModel(packageDao, item);
        String msg = syncModel.Sync();
        redirectAttributes.addFlashAttribute("feedItem", item);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/user/sync";
    }
}
