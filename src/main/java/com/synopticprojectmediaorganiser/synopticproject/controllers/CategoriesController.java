package com.synopticprojectmediaorganiser.synopticproject.controllers;

import com.synopticprojectmediaorganiser.synopticproject.models.Category;
import com.synopticprojectmediaorganiser.synopticproject.models.MediaFile;
import com.synopticprojectmediaorganiser.synopticproject.services.CategoriesService;
import com.synopticprojectmediaorganiser.synopticproject.services.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService = new CategoriesService();

    @Autowired
    MediaFileService mediaFileService = new MediaFileService();

    @GetMapping("/categories")
    public String displayCategoriesPage(Model model) {
        List<MediaFile> allMediaFiles = mediaFileService.listAll();
        List<Category> categoriesList = categoriesService.listAll();
        List<String> allCategoryNames = new ArrayList<>();

        for (int i = 0; i < categoriesList.size(); i++) {
            if (!allCategoryNames.contains(categoriesList.get(i).getCategoryName())) {
                allCategoryNames.add(categoriesList.get(i).getCategoryName());
            }
        }

        model.addAttribute("allCategoryNames", allCategoryNames);
        model.addAttribute("fileList", allMediaFiles);
        model.addAttribute("categoryObjects", categoriesList);
        return "categories";
    }

    @GetMapping("/delete-category/{categoryId}/{fileId}")
    public RedirectView deleteCategory(@PathVariable Long categoryId,
                                       @PathVariable Long fileId) {

        categoriesService.delete(categoryId);

        return new RedirectView("/categories");
    }

    @GetMapping("/rename-category/{categoryId}")
    public String displayRenameCategory(@PathVariable Long categoryId,
                                        Model model) {
        Category category = categoriesService.get(categoryId);
        model.addAttribute("category", category);
        return "rename-categories";
    }

    @PostMapping("/rename-category/{categoryId}")
    public RedirectView renameCategory(@PathVariable Long categoryId,
                                       WebRequest request) {
        Category category = categoriesService.get(categoryId);
        category.setCategoryName(request.getParameter("newName"));
        categoriesService.save(category);
        return new RedirectView("/categories");
    }
}
