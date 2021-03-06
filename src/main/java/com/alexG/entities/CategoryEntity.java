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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "CATEGORIES")
@EntityListeners(AuditingEntityListener.class)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="category", cascade= { CascadeType.REMOVE }, fetch = FetchType.EAGER)
    private List<TechnologyEntity> technologies = new ArrayList<>();

    @NotBlank
    private String title;

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

	public List<TechnologyEntity> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<TechnologyEntity> technologies) {
		this.technologies = technologies;
	}

	public void addTechnology(TechnologyEntity technology) {
		technologies.add(technology);
	}
}
