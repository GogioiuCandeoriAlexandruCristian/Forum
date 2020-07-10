package com.alexG.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alexG.domain.CacheDomain;
import com.alexG.domain.entities.Category;

@Component
public class CacheModelImpl implements CacheModel {

	@Autowired
	private CacheDomain cacheDomain;

	@Override
	public List<Category> findAllCategories() {
		cacheDomain.findAllCategories();
		return null;
	}
}
