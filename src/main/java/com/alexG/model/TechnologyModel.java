package com.alexG.model;

import java.util.List;

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
		io.vavr.collection.List.ofAll(topics).removeFirst(t -> t.getId() == topicId);
	}
}
