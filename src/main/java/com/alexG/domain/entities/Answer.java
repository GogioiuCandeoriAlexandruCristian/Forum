package com.alexG.domain.entities;

public class Answer {
	public Long id;
	public String text;
	public User userCreator;
	private int rating;

	public void setRating(int rating) {
		if (isRatingOk(rating))
			this.rating = rating;
	}

	public int getRating() {
		return rating;
	}

	public void editText(String textAnswer) {
		text = textAnswer;
	}

	public boolean isUserCreator(User user) {
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

}
