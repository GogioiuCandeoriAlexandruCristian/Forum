package com.alexG.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "topics")
public class TopicEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	@NotBlank
	public String title;
	@NotBlank
	public String question;

	@ManyToOne
	@JoinColumn(name = "tech_id")
	public TechnologyEntity technology;

	@ManyToOne
	@JoinColumn(name = "user_id")
	public UserEntity userCreator;

	@OneToMany(mappedBy = "topic", cascade= { CascadeType.REMOVE })
	public List<AnswerEntity> answers;

	public TopicEntity(String title, String question, UserEntity userCreator) {
		this.question = question;
		this.title = title;
		this.userCreator = userCreator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public UserEntity getUserCreator() {
		return userCreator;
	}

	public void setUserCreator(UserEntity userCreator) {
		this.userCreator = userCreator;
	}

	public List<AnswerEntity> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerEntity> answers) {
		this.answers = answers;
	}
}
