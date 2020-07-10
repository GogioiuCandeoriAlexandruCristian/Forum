package com.alexG.model;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alexG.domain.CacheDomain;

import io.vavr.control.Option;

@Component
public class CacheModelImpl implements CacheModel {

	private CacheDomain cacheDomain;

	private List<CategoryModel> categoriesCache;

	private ModelMapperCategoryToModel mapper = Mappers.getMapper(ModelMapperCategoryToModel.class);

	@Autowired
	public CacheModelImpl(CacheDomain cacheDomain) {
		this.cacheDomain = cacheDomain;
		categoriesCache = mapper.categoriesToCategoryModel(cacheDomain.findAllCategories());
	}

	@Override
	public List<CategoryModel> findAllCategories() {
		return categoriesCache;
	}

	@Override
	public List<TechnologyModel> findTechnologies(Long id) {
		Option<CategoryModel> optionCategModel = io.vavr.collection.List.ofAll(categoriesCache).find(categ -> categ.id == id);
		if(optionCategModel.isDefined())
			return optionCategModel.get().getTechnologies();
		return null;
	}
}
