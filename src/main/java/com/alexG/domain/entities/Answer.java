package com.alexG.domain.entities;

public class Answer {
	public Long id;
	public String text;
	public User creatorUser;
	public int rating;
	public int points;

	public void setRating(int rating) {
		if (0 <= rating && rating <= 5)
			this.rating = rating;
	}

	public void setPoints(int points) {
		if (1 <= points && points <= 5)
			this.points = points;
	}

	public void editText(String textAnswer) {
		text = textAnswer;
	}

	public boolean isCreatorUser(User user) {
		return user.getUsername() != creatorUser.getUsername();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = this.id == null ? id : this.id;
	}
}
