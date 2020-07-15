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
	public Long id;
	@NotBlank
	public String text;
	public int rating;
	public int points;

	@ManyToOne
	@JoinColumn(name = "user_id")
	public UserEntity userCreator;

	@ManyToOne
	@JoinColumn(name = "topic_id")
	public TopicEntity topic;

	public AnswerEntity(String text, UserEntity userCreator) {
		this.text = text;
		this.userCreator = userCreator;
	}
}
