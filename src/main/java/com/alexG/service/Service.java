package com.alexG.service;

import java.util.List;

import com.alexG.model.CategoryModel;
import com.alexG.model.TechnologyModel;

public interface Service {
	List<CategoryModel> findAllCategories();

	List<TechnologyModel> findTechnologies(Long categId);
}
