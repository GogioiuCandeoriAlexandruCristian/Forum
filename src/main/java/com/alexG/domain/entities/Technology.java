package com.alexG.domain.entities;

import java.util.List;
import java.util.NoSuchElementException;

public class Technology {
	private Long id;
	public String title;
	public List<Topic> topics;

	public Topic removeTopic(Long topicId) {
		Topic topic = findTopic(topicId);
		topics.remove(topic);
		return topic;
	}

	public void changeTitle(String newTitle) {
		title = newTitle;
	}

	public void addTopic(Topic topic) {
		topics.add(topic);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = this.id == null ? id : this.id;
	}

	public Topic findTopic(Long topicId) {
		try {
			Topic topic = io.vavr.collection.List.ofAll(topics).find(t -> t.getId() == topicId).get();
			return topic;
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException("Not found -> topic with id: " + topicId);
			throw exception;
		}
	}

}
