package com.alexG.domain.entities;

public class Answer {
	public Long id;
	public String text;
	public User userCreator;
	private int rating;
	private int points;

	public void setRating(int rating) {
		if (isPointingOk(rating))
			this.rating = rating;
	}

	public int getRating() {
		return rating;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		if (isPointingOk(points))
			this.points = points;
	}

	public void editText(String textAnswer) {
		text = textAnswer;
	}

	public boolean isCreatorUser(User user) {
		return user.getUsername() == userCreator.getUsername();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = this.id == null ? id : this.id;
	}

	public boolean isRatingOk(int rating) {
		if (1 <= rating && rating <= 5)
			return true;
		return false;
	}

	public boolean isPointingOk(int points) {
		if (1 <= points && points <= 5)
			return true;
		return false;
	}
}
