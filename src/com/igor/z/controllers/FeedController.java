package com.igor.z.controllers;

import com.igor.z.daos.JdbcFeedDao;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.models.FeedEditorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FeedController {

    @Autowired
    private JdbcFeedDao feedDao;

    @Autowired
    @Qualifier("feedFormValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/user/addFeed", method = RequestMethod.GET)
    public ModelAndView showForm(@ModelAttribute("feedItem") FeedItem feed) {
        return new ModelAndView("user/feed", "feedItem", new FeedItem());
    }

    @RequestMapping(value = "/user/editFeed", method = RequestMethod.GET)
    public ModelAndView showEditForm(@ModelAttribute("feedItem") FeedItem feed,
                                     ModelMap model) {
        FeedItem item  = (FeedItem)model.get("feedItem");
        return new ModelAndView("user/feed", "feedItem", item);
    }

    @RequestMapping(value = "/user/addFeed", method = RequestMethod.POST)
    public String submit(@ModelAttribute("feedItem") @Validated FeedItem feed,
                         BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "user/feed";
        }
        feed.setApiKey("");//TODO
        FeedEditorModel feedEditorModel = new FeedEditorModel(feedDao);
        String msg;
        if(feed.isNew()){
            msg = feedEditorModel.addFeed(feed);
        } else {
            msg = feedEditorModel.modifyFeed(feed, feed.getId());
        }
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/user/feedmanager";
    }
}
