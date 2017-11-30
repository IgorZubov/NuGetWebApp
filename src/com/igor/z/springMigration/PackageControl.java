package com.igor.z.springMigration;

import com.igor.z.daos.JdbcFeedDao;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.modelAttributes.PackageItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PackageControl {

    private static String UPLOADED_FOLDER = "F://temp//";

    @Autowired
    private JdbcFeedDao feedDao;

    @Autowired
    @Qualifier("fileValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/user/addPackage", method = RequestMethod.GET)
    public ModelAndView showForm(@ModelAttribute("packageItem") PackageItem packageItem, Model model) {
        List<FeedItem> feeds = feedDao.getAll();
        List<String> sourceList = feeds.stream().map(feedItem -> feedItem.getFeedName()).collect(Collectors.toList());
        model.addAttribute("sourceList", sourceList);
        return new ModelAndView("user/package", "packageItem", new PackageItem());
    }

    @RequestMapping(value = "/user/addPackage", method = RequestMethod.POST)
    public String addPackage(@RequestParam MultipartFile file,
                             @ModelAttribute("packageItem") PackageItem packageItem,
                             final RedirectAttributes redirectAttributes, BindingResult result) {
        packageItem.setFile(file);
        validator.validate(packageItem, result);
        if (packageItem.getFile().isEmpty()) {
            redirectAttributes.addFlashAttribute("css", "error");
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/user/feedmanager";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = packageItem.getFile().getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + packageItem.getFile().getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + packageItem.getFile().getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("message", "Package added successfully");
        return "redirect:/user/feedmanager";
    }
}
