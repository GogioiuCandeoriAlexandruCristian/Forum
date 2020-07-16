package com.alexG.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CategoryModel {
	public Long id;
	public String title;
	public List<TechnologyModel> technologies = new ArrayList<>();

	public List<TechnologyModel> getTechnologies() {
		return technologies;
	}

	public void addTechnology(TechnologyModel technologyModel) {
		technologies.add(technologyModel);
	}

	public void removeTechnology(Long technologyId) {
		technologies.remove(findTechnology(technologyId));
	}

	public TechnologyModel findTechnology(Long technologyId) {
		try {
			return io.vavr.collection.List.ofAll(technologies).find(t -> t.getId() == technologyId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Technology Model with id: " + technologyId + " in category with id: " + id);
			throw exception;
		}
	}
}
