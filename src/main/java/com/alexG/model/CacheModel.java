package com.alexG.model;

import java.util.List;

import com.alexG.domain.entities.Category;

public interface CacheModel {

	List<CategoryModel> findAllCategories();

	List<TechnologyModel> findTechnologies(Long id);

	
}
