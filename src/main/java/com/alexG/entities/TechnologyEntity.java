package com.alexG.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "TECHNOLOGIES")
@EntityListeners(AuditingEntityListener.class)
public class TechnologyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String title;

	@ManyToOne
	@JoinColumn(name = "categ_id")
	private CategoryEntity category;

	@OneToMany(mappedBy = "technology", cascade = { CascadeType.REMOVE }, fetch = FetchType.EAGER)
	private List<TopicEntity> topics = new ArrayList<>();

	public TechnologyEntity() {
	}

	public TechnologyEntity(String title, CategoryEntity category) {
		this.title = title;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public List<TopicEntity> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicEntity> topics) {
		this.topics = topics;
	}

	public void addTopic(TopicEntity topic) {
		topics.add(topic);
	}
}
