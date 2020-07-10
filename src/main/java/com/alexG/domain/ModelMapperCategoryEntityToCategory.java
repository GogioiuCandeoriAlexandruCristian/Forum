package com.alexG.domain;
import java.util.List;

import org.mapstruct.Mapper;

import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.Technology;
import com.alexG.entities.CategoryEntity;
import com.alexG.entities.TechnologyEntity;

@Mapper
public interface ModelMapperCategoryEntityToCategory {
	List<Category> categoryEntitiesToCategory(List<CategoryEntity> categories);
	
	List<Technology> technologyEntitiesToTechnology(List<TechnologyEntity> technologies);
	
	Technology TechnologyEntityToTechnology(TechnologyEntity technology);
}