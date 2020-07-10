package com.alexG.domain;
import java.util.List;

import org.mapstruct.Mapper;

import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.Technology;
import com.alexG.entities.CategoryEntity;
import com.alexG.entities.TechnologyEntity;

@Mapper
public interface ModelMapperCategoryEntityToCategory {
	List<Category> categoriesToCategoryModel(List<CategoryEntity> categories);
	
	List<Technology> technologiesToTechnologyModel(List<TechnologyEntity> technologies);
	
	Technology categoryToTechnologyModel(TechnologyEntity technology);
}