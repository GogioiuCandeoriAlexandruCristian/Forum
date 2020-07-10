package com.alexG.domain;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alexG.domain.entities.Category;
import com.alexG.repository.CategoryRepository;

@Component
public class CacheDomainImpl implements CacheDomain {

	private List<Category> categoriesCache;
	
	private CategoryRepository categRepo;

	private ModelMapperCategoryEntityToCategory mapper = Mappers.getMapper(ModelMapperCategoryEntityToCategory.class);

	@Autowired
	public CacheDomainImpl(CategoryRepository categRepository) {
		categRepo = categRepository;
		categoriesCache = mapper.categoryEntitiesToCategory(categRepo.findAll());
	}

	@Override
	public List<Category> findAllCategories() {
		return categoriesCache;
	}

}
