package com.alexG.domain.entities;

import java.util.List;
import java.util.NoSuchElementException;

public class Category {
	public Long id;
	public String title;
	public List<Technology> technologies;

	public void deleteTechnology(Long technologyId) {
		Technology tech = findTechnology(technologyId);
		technologies.remove(tech);
	}

	public void changeTitle(String newTitle) {
		title = newTitle;
	}

	public Technology findTechnology(Long technologyId) {
		try {
			Technology technology = io.vavr.collection.List.ofAll(technologies)
					.find(tech -> tech.getId() == technologyId).get();
			return technology;
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Technology with id: " + technologyId + " not found in category with id: " + id);
			throw exception;
		}
	}

	public void addTechnology(Technology technology) {
		technologies.add(technology);
	}
}
