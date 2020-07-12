package com.alexG.security.model;

public class RoleModel {
	private Integer id;

	private String name;

	public RoleModel() {

	}

	public RoleModel(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}