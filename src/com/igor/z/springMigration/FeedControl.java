package com.igor.z.springMigration;


import com.igor.z.daos.JdbcFeedDao;
import com.igor.z.modelAttributes.FeedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FeedControl {

    @Autowired
    private JdbcFeedDao feedDao;

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
    public String submit(@ModelAttribute("feedItem") FeedItem feed,
                         BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "error";
        }
        feed.setApiKey("");//TODO
        if(feed.isNew()){
            feedDao.insert(feed);
        } else {
            feedDao.update(feed);
        }
        redirectAttributes.addFlashAttribute("css", "success");
        if(feed.isNew()){
            redirectAttributes.addFlashAttribute("msg", "Feed added successfully!");
        }else{
            redirectAttributes.addFlashAttribute("msg", "Feed updated successfully!");
        }
        return "redirect:/user/feedmanager";
    }
}
