package com.alexG.domain.entities;

import java.util.List;
import java.util.NoSuchElementException;

public class Topic {
	public Long id;
	public String title;
	public String question;
	public User userCreator;
	public List<Answer> answers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = this.id == null ? id : this.id;
	}

	public void editQuestion(String question) {
		this.question = question;
	}

	public void addAnswer(Answer answer) {
		answers.add(answer);
	}

	public void rateAnswer(Long answerId, int rating) {
		Answer answer = findAnswer(answerId);
		answer.setRating(rating);
	}

	public void deleteAnswer(Long answerId) {
		Answer answer = findAnswer(answerId);
		answers.remove(answer);
	}

	public boolean isUserCreator(User user) {
		return user.getUsername() == userCreator.getUsername();
	}

	public boolean isUserCreatorForAnswer(User user, Long answerId) {
		return findAnswer(answerId).isUserCreator(user);
	}

	public Answer findAnswer(Long answerId) {
		try {
			Answer answer = io.vavr.collection.List.ofAll(answers).find(a -> a.getId() == answerId).get();
			return answer;
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException("Not found -> Answer with id: " + answerId);
			throw exception;
		}
	}

}
