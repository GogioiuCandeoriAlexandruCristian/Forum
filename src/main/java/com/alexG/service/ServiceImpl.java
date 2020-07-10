package com.alexG.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alexG.domain.Category;
import com.alexG.model.CacheModel;
import com.alexG.model.CategoryModel;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

	@Autowired
	private CacheModel cacheModel;

	@Override
	public List<CategoryModel> findAllCategories() {
		List<Category> categories = cacheModel.findAllCategories();
		return null;
	}

}
