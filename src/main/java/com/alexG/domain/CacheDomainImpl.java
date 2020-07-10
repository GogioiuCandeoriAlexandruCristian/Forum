package com.alexG.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alexG.domain.entities.Category;
import com.alexG.entities.CategoryEntity;
import com.alexG.repository.CategoryRepository;

@Component
public class CacheDomainImpl implements CacheDomain {

	private List<Category> categoryCache = new ArrayList<>();
	
	@Autowired
	private CategoryRepository categRepo;

	@Override
	public List<Category> findAllCategories() {
		List<CategoryEntity> categEntities = categRepo.findAll();
		return categoryCache;
	}

}
