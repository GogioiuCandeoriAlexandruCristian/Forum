package com.alexG.domain.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.alexG.domain.Repository;
import com.alexG.domain.entities.Answer;
import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.Technology;
import com.alexG.domain.entities.Topic;
import com.alexG.entities.AnswerEntity;
import com.alexG.entities.CategoryEntity;
import com.alexG.entities.TechnologyEntity;
import com.alexG.entities.TopicEntity;
import com.alexG.entities.UserEntity;

public class RepositoryTestImpl extends Repository {

	private List<CategoryEntity> categs = new ArrayList<>();

	public RepositoryTestImpl() {
		CategoryEntity categ1 = new CategoryEntity();
		TechnologyEntity technology1 = new TechnologyEntity();
		TechnologyEntity technology2 = new TechnologyEntity();
		TopicEntity topic1 = new TopicEntity();
		TopicEntity topic2 = new TopicEntity();
		AnswerEntity answer1 = new AnswerEntity();
		AnswerEntity answer2 = new AnswerEntity();
		AnswerEntity answer3 = new AnswerEntity();

		TopicEntity topic3 = new TopicEntity();
		TopicEntity topic4 = new TopicEntity();
		AnswerEntity answer4 = new AnswerEntity();
		AnswerEntity answer5 = new AnswerEntity();
		AnswerEntity answer6 = new AnswerEntity();

		topic1.addAnswer(answer1);
		topic1.addAnswer(answer2);
		topic2.addAnswer(answer3);

		technology1.addTopic(topic1);
		technology1.addTopic(topic2);

		categ1.addTechnology(technology1);

		topic4.addAnswer(answer4);
		topic3.addAnswer(answer5);
		topic3.addAnswer(answer6);

		technology2.addTopic(topic3);
		technology2.addTopic(topic4);

		UserEntity user = new UserEntity();
		user.setId(1L);
		user.setUsername("alex");
		topic1.setUserCreator(user);
		answer1.setUserCreator(user);
		technology1.setId(1L);
		categ1.setId(1L);
		topic1.setId(1L);
		answer1.setId(1L);
		
		categs.add(categ1);
	}

	@Override
	public List<CategoryEntity> findAllCategories() {
		return categs;
	}

	@Override
	public void saveUser(UserEntity userEnt) {
		for (CategoryEntity categoryEntity : categs) {
			for (TechnologyEntity tech : categoryEntity.getTechnologies()) {
				for(TopicEntity topic : tech.getTopics()) {
					for(AnswerEntity answer : topic.getAnswers()) {
						if(answer.userCreator!= null && answer.userCreator.getId() == userEnt.getId())
							answer.userCreator = userEnt;
					}
				}
			}
		}
	}

	@Override
	public UserEntity findUserByUsername(String username) {
		for (CategoryEntity categoryEntity : categs) {
			for (TechnologyEntity tech : categoryEntity.getTechnologies()) {
				for(TopicEntity topic : tech.getTopics()) {
					for(AnswerEntity answer : topic.getAnswers()) {
						if(answer.userCreator.getUsername().equals(username))
							return answer.userCreator;
					}
				}
			}
		}
		return null;
	}

	@Override
	public Optional<UserEntity> findUserById(Long userId) {
		for (CategoryEntity categoryEntity : categs) {
			for (TechnologyEntity tech : categoryEntity.getTechnologies()) {
				for(TopicEntity topic : tech.getTopics()) {
					for(AnswerEntity answer : topic.getAnswers()) {
						if(answer.userCreator.getId() == userId)
							return Optional.of(answer.userCreator);
					}
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<AnswerEntity> findAnswerById(Long answerId) {
		for (CategoryEntity categoryEntity : categs) {
			for (TechnologyEntity tech : categoryEntity.getTechnologies()) {
				for(TopicEntity topic : tech.getTopics()) {
					for(AnswerEntity answer : topic.getAnswers()) {
						if(answer.getId() == answerId)
							return Optional.of(answer);
					}
				}
			}
		}
		return Optional.empty();
	}

	public Technology findTechnology(List<Category> categoriesCache, Long categoryId, Long technologyId) {
		Category categ = findCategory(categoriesCache, categoryId);
		return categ.findTechnology(technologyId);
	}

	public Topic findTopic(List<Category> categoriesCache, Long categoryId, Long technologyId, Long topicId) {
		Category categ = findCategory(categoriesCache, categoryId);
		Technology technology = categ.findTechnology(technologyId);
		return technology.findTopic(topicId);
	}

	public Answer findAnswer(List<Category> categoriesCache, Long categoryId, Long technologyId, Long topicId, Long answerId) {
		Category categ = findCategory(categoriesCache, categoryId);
		Technology technology = categ.findTechnology(technologyId);
		Topic topic = technology.findTopic(topicId);
		return topic.findAnswer(answerId);
	}

	public Category findCategory(List<Category> categoriesCache, Long categoryId) {
		try {
			Category category = io.vavr.collection.List.ofAll(categoriesCache).find(categ -> categ.id == categoryId)
					.get();
			return category;
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Category with id: " + categoryId);
			throw exception;
		}
	}
}
