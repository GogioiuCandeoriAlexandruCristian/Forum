package com.alexG.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alexG.domain.CacheDomain;

import io.vavr.control.Option;

@Component
public class CacheModelImpl implements CacheModel {

	@Autowired
	private CacheDomain cacheDomain;

	private List<CategoryModel> categoriesCache;

	@Override
	public List<CategoryModel> findAllCategories() {
		cacheDomain.findAllCategories();
		return null;
	}

	@Override
	public List<TechnologyModel> findTechnologies(Long id) {
		Option<CategoryModel> optionCategModel = io.vavr.collection.List.ofAll(categoriesCache).find(categ -> categ.id == id);
		if(optionCategModel.isDefined())
			return optionCategModel.get().getTechnologies();
		return null;
	}
}
