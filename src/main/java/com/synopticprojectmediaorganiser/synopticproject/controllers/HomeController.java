package com.synopticprojectmediaorganiser.synopticproject.controllers;

import com.synopticprojectmediaorganiser.synopticproject.models.Category;
import com.synopticprojectmediaorganiser.synopticproject.models.MediaFile;
import com.synopticprojectmediaorganiser.synopticproject.services.AmazonService;
import com.synopticprojectmediaorganiser.synopticproject.services.CategoriesService;
import com.synopticprojectmediaorganiser.synopticproject.services.MediaFileService;
import com.synopticprojectmediaorganiser.synopticproject.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private MediaFileService mediaFileService;

    private AmazonService amazonService;

    @Autowired
    HomeController(AmazonService amazonClient) {
        this.amazonService = amazonClient;
    }

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/home")
    public String displayHomePage(Model model) {
        List<MediaFile> mediaFileList = mediaFileService.listAll();
        List<Category> storedCategories = categoriesService.listAll();
        List<String> categoriesList = new ArrayList<>();

        for (int i = 0; i < storedCategories.size(); i++) {
            Category category = storedCategories.get(i);
            String categoryName = category.getCategoryName();
            if (! categoriesList.contains(categoryName)) {
                categoriesList.add(categoryName);
            }
        }

        model.addAttribute("categoriesList", categoriesList);
        model.addAttribute("mediaFileList", mediaFileList);
        model.addAttribute("mediaFile", new MediaFile());
        return "home";
    }

    @GetMapping("/delete-file/{id}")
    public String deleteFile(@PathVariable("id") Long id) {
        MediaFile fileToDelete = mediaFileService.get(id);
        String urlOfFileToDelete = fileToDelete.getPath();
        amazonService.deleteFileFromS3Bucket(urlOfFileToDelete);
        mediaFileService.delete(id);
        return "redirect:/home";
    }
}
