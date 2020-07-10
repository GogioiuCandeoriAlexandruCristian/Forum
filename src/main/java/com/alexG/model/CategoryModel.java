package com.alexG.model;

import java.util.List;

public class CategoryModel {
    public Long id;
    public String title;
    public List<TechnologyModel> technologies;
	public List<TechnologyModel> getTechnologies() {
		return technologies;
	}
}
