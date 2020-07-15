package com.alexG.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "answers")
public class AnswerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String text;
	private int rating;

	@ManyToOne
	@JoinColumn(name = "user_id")
	public UserEntity userCreator;

	@ManyToOne
	@JoinColumn(name = "topic_id")
	public TopicEntity topic;

	public AnswerEntity() {}

	public AnswerEntity(String text, TopicEntity topic, UserEntity userCreator) {
		this.text = text;
		this.userCreator = userCreator;
		this.topic = topic;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public UserEntity getUserCreator() {
		return userCreator;
	}

	public void setUserCreator(UserEntity userCreator) {
		this.userCreator = userCreator;
	}

	public TopicEntity getTopic() {
		return topic;
	}

	public void setTopic(TopicEntity topic) {
		this.topic = topic;
	}
}
