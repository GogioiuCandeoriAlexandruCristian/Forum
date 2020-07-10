package com.alexG.model;
import java.util.List;

import org.mapstruct.Mapper;

import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.Technology;

@Mapper
public interface ModelMapperCategoryToModel {
	List<CategoryModel> categoriesToCategoryModel(List<Category> categories);
	
	List<TechnologyModel> technologiesToTechnologyModel(List<Technology> technologies);
	
	TechnologyModel categoryToTechnologyModel(Technology technology);
}