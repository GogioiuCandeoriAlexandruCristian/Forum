package com.alexG.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alexG.entities.AnswerEntity;
import com.alexG.entities.CategoryEntity;
import com.alexG.entities.TechnologyEntity;
import com.alexG.entities.TopicEntity;
import com.alexG.entities.UserEntity;
import com.alexG.repository.AnswerRepository;
import com.alexG.repository.CategoryRepository;
import com.alexG.repository.TechnologyRepository;
import com.alexG.repository.TopicRepository;
import com.alexG.repository.UserRepository;

@Component
public class Repository {
	@Autowired
	private CategoryRepository categRepo;
	@Autowired
	private TechnologyRepository techRepo;
	@Autowired
	private TopicRepository topicRepo;
	@Autowired
	private AnswerRepository answerRepo;
	@Autowired
	private UserRepository userRepo;

	public void saveCategory(CategoryEntity categoryEntity) {
		categRepo.save(categoryEntity);
	}

	public Optional<CategoryEntity> findCategoryById(Long categoryId) {
		return categRepo.findById(categoryId);
	}

	public List<CategoryEntity> findAllCategories() {
		return categRepo.findAll();
	}

	public void saveTopic(TopicEntity topicEntity) {
		topicRepo.save(topicEntity);
	}

	public void saveTechnology(TechnologyEntity techEntity) {
		techRepo.save(techEntity);
	}

	public void deleteTechnologyById(Long technologyId) {
		techRepo.deleteById(technologyId);
	}

	public void deleteTopicById(Long topicId) {
		topicRepo.deleteById(topicId);
	}

	public void saveAnswer(AnswerEntity answerEntity) {
		answerRepo.save(answerEntity);
	}

	public void deleteAnswerById(Long answerId) {
		answerRepo.deleteById(answerId);
	}

	public Optional<UserEntity> findUserById(Long userId) {
		return userRepo.findById(userId);
	}

	public Optional<TechnologyEntity> findTechnologyById(Long technologyId) {
		return techRepo.findById(technologyId);
	}

	public Optional<TopicEntity> findTopicById(Long topicId) {
		return topicRepo.findById(topicId);
	}

	public Optional<AnswerEntity> findAnswerById(Long answerId) {
		return answerRepo.findById(answerId);
	}
}
