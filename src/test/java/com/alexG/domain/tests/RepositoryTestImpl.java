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
		categ1.addTechnology(technology2);

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
		topic1.setTechnology(technology1);
		answer1.setId(1L);


		UserEntity user2 = new UserEntity();
		user2.setId(3L);
		user2.setUsername("alex3");
		topic2.setUserCreator(user2);
		answer2.setUserCreator(user2);
		technology2.setId(3L);
		topic2.setId(3L);
		topic2.setTechnology(technology2);
		answer2.setId(3L);
		categs.add(categ1);
	}

	@Override
	public List<CategoryEntity> findAllCategories() {
		return categs;
	}

	@Override
	public void saveTechnology(TechnologyEntity techEntity) {
		for (CategoryEntity categoryEntity : categs) {
			if (categoryEntity.getId() == techEntity.getCategory().getId()) {
				techEntity.setId(Long.parseLong(techEntity.getTitle()));
				categoryEntity.addTechnology(techEntity);
				return;
			}
		}
	}

	@Override
	public void saveAnswer(AnswerEntity answerEntity) {
		for (CategoryEntity categoryEntity : categs) {
			for (TechnologyEntity tech : categoryEntity.getTechnologies()) {
				for (TopicEntity topic : tech.getTopics()) {
					if (topic.getId() == answerEntity.topic.getId()) {
						if(answerEntity.getText() != null && answerEntity.getText().contains("#"))
							answerEntity.setId(Long.parseLong(answerEntity.getText().substring(1)));
						topic.addAnswer(answerEntity);
						return;
					}
				}
			}
		}
	}

	@Override
	public void saveTopic(TopicEntity topicEntity) {
		for (CategoryEntity categoryEntity : categs) {
			for (TechnologyEntity tech : categoryEntity.getTechnologies()) {
					if (tech.getId() == topicEntity.getTechnology().getId()) {
						if(topicEntity.getTitle() != null && topicEntity.getTitle().contains("#"))
							topicEntity.setId(Long.parseLong(topicEntity.getTitle().substring(1)));
						tech.addTopic(topicEntity);
						return;
					}
			}
		}
	}

	@Override
	public void saveUser(UserEntity userEnt) {
		for (CategoryEntity categoryEntity : categs) {
			for (TechnologyEntity tech : categoryEntity.getTechnologies()) {
				for (TopicEntity topic : tech.getTopics()) {
					for (AnswerEntity answer : topic.getAnswers()) {
						if (answer.userCreator != null && answer.userCreator.getId() == userEnt.getId())
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
				for (TopicEntity topic : tech.getTopics()) {
					for (AnswerEntity answer : topic.getAnswers()) {
						if (answer.userCreator != null && answer.userCreator.getUsername().equals(username))
							return answer.userCreator;
					}
				}
			}
		}
		return new UserEntity(username, "", "");
	}

	@Override
	public Optional<CategoryEntity> findCategoryById(Long categoryId) {
		for (CategoryEntity categoryEntity : categs) {
			if (categoryEntity.getId() == categoryId) {
				return Optional.of(categoryEntity);
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<TechnologyEntity> findTechnologyById(Long technologyId) {
		for (CategoryEntity categoryEntity : categs) {
			for (TechnologyEntity tech : categoryEntity.getTechnologies()) {
				if (tech.getId() == technologyId) {
					return Optional.of(tech);
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<UserEntity> findUserById(Long userId) {
		for (CategoryEntity categoryEntity : categs) {
			for (TechnologyEntity tech : categoryEntity.getTechnologies()) {
				for (TopicEntity topic : tech.getTopics()) {
					for (AnswerEntity answer : topic.getAnswers()) {
						if (answer.userCreator.getId() == userId)
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
				for (TopicEntity topic : tech.getTopics()) {
					for (AnswerEntity answer : topic.getAnswers()) {
						if (answer.getId() == answerId) {
							answer.setTopic(topic);
							return Optional.of(answer);
						}
					}
				}
			}
		}
		return Optional.empty();
	}

	public Optional<TopicEntity> findTopicById(Long topicId) {
		for (CategoryEntity categoryEntity : categs) {
			for (TechnologyEntity tech : categoryEntity.getTechnologies()) {
				for (TopicEntity topic : tech.getTopics()) {
					if (topic.getId() == topicId)
						return Optional.of(topic);
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

	public Answer findAnswer(List<Category> categoriesCache, Long categoryId, Long technologyId, Long topicId,
			Long answerId) {
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
