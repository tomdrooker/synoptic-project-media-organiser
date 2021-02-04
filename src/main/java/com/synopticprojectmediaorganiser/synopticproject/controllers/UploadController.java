package com.synopticprojectmediaorganiser.synopticproject.controllers;

import com.synopticprojectmediaorganiser.synopticproject.models.Category;
import com.synopticprojectmediaorganiser.synopticproject.models.ErrorMessage;
import com.synopticprojectmediaorganiser.synopticproject.models.Image;
import com.synopticprojectmediaorganiser.synopticproject.models.MediaFile;
import com.synopticprojectmediaorganiser.synopticproject.services.AmazonService;
import com.synopticprojectmediaorganiser.synopticproject.services.CategoriesService;
import com.synopticprojectmediaorganiser.synopticproject.services.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UploadController<ImageService> {

    private AmazonService amazonService;

    @Autowired
    UploadController(AmazonService amazonClient) {
        this.amazonService = amazonClient;
    }

    @Autowired
    private MediaFileService mediaFileService;

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/upload")
    public String displayUploadPage(Model model) {
        List<Category> categories = categoriesService.listAll();
        List<String> categoriesList = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            String categoryName = category.getCategoryName();
            if (! categoriesList.contains(categoryName)) {
                categoriesList.add(categoryName);
            }
        }

        model.addAttribute("newFile", new MediaFile());
        model.addAttribute("categories", categoriesList);

        return "upload";
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("filename") String filename,
                             @RequestParam("category") String category,
                             @RequestParam("comment") String comment,
                             @RequestParam("image") MultipartFile image,
                             RedirectAttributes redirectAttributes) {

        String pageRedirect = "";

        if (file.getContentType().toLowerCase().equals("audio/wav") || file.getContentType().toLowerCase().equals("audio/mp3") ||
                file.getContentType().toLowerCase().equals("video/mp4") || file.getContentType().toLowerCase().equals("audio/mpeg")) {

            MediaFile fileToUpload = new MediaFile();
            Image imageToUpload = new Image();

            List<String> categoryListArray = Arrays.asList(category.split(","));
            List<Category> categoryObjectsList = new ArrayList<>();

            for (int i = 0; i < categoryListArray.size(); i++) {
                Category newCategory = new Category();
                newCategory.setCategoryName(categoryListArray.get(i));
                categoryObjectsList.add(newCategory);
            }

            String fileS3Url = amazonService.uploadFile(file);
            String imageS3Url = amazonService.uploadFile(image);
            imageToUpload.setImage_path(imageS3Url.replace("media-organiser-bucket.", ""));

            fileToUpload.setName(filename);
            fileToUpload.setPath(fileS3Url.replace("media-organiser-bucket.", ""));
            fileToUpload.setComment(comment);
            fileToUpload.setImage(imageToUpload);
            imageToUpload.setMediaFile(fileToUpload);

            mediaFileService.save(fileToUpload);
            MediaFile uploadedFile = mediaFileService.get(fileToUpload.getId());

            for (int i = 0; i < categoryObjectsList.size(); i++) {
                Category singleCategory = categoryObjectsList.get(i);
                singleCategory.setMediaFile(uploadedFile);
                categoriesService.save(singleCategory);
            }
            pageRedirect = "redirect:/home";
        }
        else {
            ErrorMessage error = new ErrorMessage("Upload a valid file" ,"Please upload only wav, mp3 or mp4 files");
            redirectAttributes.addFlashAttribute("error", error);
            pageRedirect = "redirect:/upload";
        }

        return pageRedirect;

    }

    @GetMapping("/change-details/{fileId}")
    public String displayChangeDetailsPage(Model model,
                                           @PathVariable Long fileId) {
        MediaFile file = mediaFileService.get(fileId);

        List<Category> storedCategories = categoriesService.listAll();
        List<String> categoriesList = new ArrayList<>();

        for (int i = 0; i < storedCategories.size(); i++) {
            Category category = storedCategories.get(i);
            String categoryName = category.getCategoryName();
            if (! categoriesList.contains(categoryName)) {
                categoriesList.add(categoryName);
            }
        }

        List<Category> fileCategories = file.getCategories();
        List<String> categoryStrings = new ArrayList<>();
        for (Category cat : fileCategories) {
            categoryStrings.add(cat.getCategoryName());
        }

        model.addAttribute("file", file);
        model.addAttribute("categories", categoriesList);
        model.addAttribute("categoryStrings", categoryStrings);
        return "change-details";
    }



    @PostMapping("/change-details/{fileId}")
    public RedirectView renameCategory(@PathVariable Long fileId,
                                       @RequestParam("filename") String filename,
                                       @RequestParam("category") String category,
                                       @RequestParam("comment") String comment,
                                       @RequestParam("image") MultipartFile image) {

        MediaFile fileToChange = mediaFileService.get(fileId);

        if (image.getOriginalFilename().length() > 0) {
            String imageS3Url = amazonService.uploadFile(image);
            Image newImage = new Image();
            newImage.setImage_path(imageS3Url.replace("media-organiser-bucket.", ""));
            fileToChange.setImage(newImage);
        }

        List<String> categoryListArray = Arrays.asList(category.split(","));
        List<Category> categoryObjectsList = new ArrayList<>();

        for (int i = 0; i < categoryListArray.size(); i++) {
            Category newCategory = new Category();
            newCategory.setCategoryName(categoryListArray.get(i));
            if (fileToChange.getCategories().contains(categoryListArray.get(i))) {
                categoryObjectsList.add(newCategory);
            }
        }

        fileToChange.setName(filename);
        fileToChange.setComment(comment);


        for (int i = 0; i < categoryObjectsList.size(); i++) {
            Category singleCategory = categoryObjectsList.get(i);
            singleCategory.setMediaFile(fileToChange);
            categoriesService.save(singleCategory);
        }

        mediaFileService.save(fileToChange);

        return new RedirectView("/home");
    }

}
