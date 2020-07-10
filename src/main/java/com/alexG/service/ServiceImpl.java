package com.alexG.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alexG.domain.entities.Category;
import com.alexG.model.CacheModel;
import com.alexG.model.CategoryModel;
import com.alexG.model.TechnologyModel;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

	@Autowired
	private CacheModel cacheModel;

	@Override
	public List<CategoryModel> findAllCategories() {
		return cacheModel.findAllCategories();
	}

	@Override
	public List<TechnologyModel> findTechnologies(Long categId) {
		return cacheModel.findTechnologies(categId);
	}

}
