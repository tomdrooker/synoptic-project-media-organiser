package com.synopticprojectmediaorganiser.synopticproject.repositories;


import com.synopticprojectmediaorganiser.synopticproject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
}
