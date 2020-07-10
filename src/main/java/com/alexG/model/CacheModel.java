package com.alexG.model;

import java.util.List;

import com.alexG.domain.entities.Category;

public interface CacheModel {

	List<Category> findAllCategories();

	
}
