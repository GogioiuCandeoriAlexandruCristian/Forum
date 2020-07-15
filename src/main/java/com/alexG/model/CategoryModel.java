package com.alexG.model;

import java.util.List;

public class CategoryModel {
	public Long id;
	public String title;
	public List<TechnologyModel> technologies;

	public List<TechnologyModel> getTechnologies() {
		return technologies;
	}

	public void addTechnology(TechnologyModel technologyModel) {
		technologies.add(technologyModel);
	}

	public void removeTechnology(Long technologyId) {
		io.vavr.collection.List.ofAll(technologies).removeFirst(t -> t.getId() == technologyId);
	}
}
