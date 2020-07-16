package com.alexG.model;

import java.util.List;
import java.util.NoSuchElementException;

public class TechnologyModel {
	private Long id;
	public String title;
	public List<TopicModel> topics;

	public void addTopic(TopicModel topicModel) {
		topics.add(topicModel);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = this.id == null ? id : this.id;
	}

	public void removeTopic(Long topicId) {
		topics.remove(findTopic(topicId));
	}

	public TopicModel findTopic(Long topicId) {
		try {
			return io.vavr.collection.List.ofAll(topics).find(t -> t.getId() == topicId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Topic Model with id: " + topicId + " in technology with id: " + id);
			throw exception;
		}
	}

}
