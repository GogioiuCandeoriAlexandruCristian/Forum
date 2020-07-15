package com.alexG.model;

import com.alexG.security.model.UserModel;

public class AnswerModel {
	public Long id;
	public String text;
	public UserModel creatorUser;
	public int rating;
	public int points;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = this.id == null ? id : this.id;
	}
}
