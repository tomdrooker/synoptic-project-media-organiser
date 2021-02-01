package com.synopticprojectmediaorganiser.synopticproject.services;

import com.synopticprojectmediaorganiser.synopticproject.models.Category;
import com.synopticprojectmediaorganiser.synopticproject.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Category> listAll() {return categoriesRepository.findAll();}

    public void save(Category category) {
        categoriesRepository.save(category);
    }

    public Category get(Long id) {
        return categoriesRepository.findById(id).get();
    }

    public void delete(Long id) {
        categoriesRepository.deleteById(id);
    }
}

