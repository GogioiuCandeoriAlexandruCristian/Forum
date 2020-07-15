package com.alexG.model;

import java.util.List;

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
		io.vavr.collection.List.ofAll(answers).removeFirst(a -> a.getId() == answerId);
	}
}
