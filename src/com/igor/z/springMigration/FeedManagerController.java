package com.igor.z.springMigration;

import com.igor.z.daos.JdbcFeedDao;
import com.igor.z.modelAttributes.FeedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class FeedManagerController {

    @Autowired
    private JdbcFeedDao feedDao;

    @RequestMapping(value = "/user/feedmanager")
    public ModelAndView showForm(Model model) {
        model.addAttribute("feeds", feedDao.getAll());
        return new ModelAndView("user/feedmanager", "feedItem", new FeedItem());
    }

    @RequestMapping(value = "/user/feedmanager/{id}/delete", method = RequestMethod.POST)
    public String deleteUser(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
        feedDao.deleteById(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Feed is deleted!");
        return "redirect:/user/feedmanager";
    }

    @RequestMapping(value = "/user/feedmanager/{id}/update", method = RequestMethod.POST)
    public ModelAndView updateUser(@PathVariable("id") int id, Model model) {
        FeedItem item = feedDao.findByFeedItemId(id);
        return new ModelAndView("user/feed", "feedItem", item);
    }
}
