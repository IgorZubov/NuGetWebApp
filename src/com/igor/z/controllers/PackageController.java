package com.igor.z.controllers;

import com.igor.z.daos.JdbcFeedDao;
import com.igor.z.daos.JdbcPackageDao;
import com.igor.z.interfaces.IPackageUploaderModel;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.modelAttributes.PackageItem;
import com.igor.z.models.PackageUploaderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PackageController {
    private List<String> sourceList;
    private List<FeedItem> feedList;

    @Autowired
    private JdbcFeedDao feedDao;

    @Autowired
    private JdbcPackageDao packageDao;

    @Autowired
    @Qualifier("fileValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/user/addPackage", method = RequestMethod.GET)
    public ModelAndView showForm(@ModelAttribute("packageItem") PackageItem packageItem, Model model) {
        feedList = feedDao.getAll();
        sourceList = feedList.stream().map(feedItem -> feedItem.getFeedName()).collect(Collectors.toList());
        model.addAttribute("sourceList", sourceList);
        return new ModelAndView("user/package", "packageItem", new PackageItem());
    }

    @RequestMapping(value = "/user/addPackage", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addPackage(@RequestParam MultipartFile file,
                             @ModelAttribute("packageItem") PackageItem packageItem,
                             final RedirectAttributes redirectAttributes, BindingResult result, Model model) {
        packageItem.setFile(file);
        validator.validate(packageItem, result);
        if (result.hasErrors()) {
            model.addAttribute("sourceList", sourceList);
            return "user/package";
        }
        IPackageUploaderModel packageUploader = new PackageUploaderModel(packageDao);
        String successMessage;
        try {
            String uploadedPath = packageUploader.uploadPackageToTempFolder((MultipartFile)packageItem.getFile());
            FeedItem feed = feedList.stream().filter(f -> f.getFeedSource().equals(packageItem.getSource()))
                    .findAny().orElse(null);
            successMessage = packageUploader.addPackageToFeed(uploadedPath, feed);
            packageUploader.deleteTempFile(uploadedPath);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("css", "error");
            redirectAttributes.addFlashAttribute("msg", "Please select a file to upload");
            return "redirect:/user/feedmanager";
        }
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", successMessage);
        return "redirect:/user/feedmanager";
    }
}
