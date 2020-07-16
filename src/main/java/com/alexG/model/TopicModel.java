package com.alexG.model;

import java.util.List;
import java.util.NoSuchElementException;

import com.alexG.security.model.UserModel;

public class TopicModel {
	private Long id;
	public String title;
	public String question;
	public UserModel userCreator;
	public List<AnswerModel> answers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = this.id == null ? id : this.id;
	}

	public void addAnswer(AnswerModel answerModel) {
		answers.add(answerModel);
	}

	public void removeAnswer(Long answerId) {
		answers.remove(findAnswer(answerId));
	}


	public AnswerModel findAnswer(Long answerId) {
		try {
			return io.vavr.collection.List.ofAll(answers).find(a -> a.id == answerId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Answer Model with id: " + answerId + " in topic with id: " + id);
			throw exception;
		}
	}
}
