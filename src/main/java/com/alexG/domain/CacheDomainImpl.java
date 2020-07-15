package com.alexG.domain;

import java.util.List;
import java.util.NoSuchElementException;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alexG.domain.entities.Answer;
import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.Technology;
import com.alexG.domain.entities.Topic;
import com.alexG.domain.entities.User;
import com.alexG.entities.AnswerEntity;
import com.alexG.entities.CategoryEntity;
import com.alexG.entities.TechnologyEntity;
import com.alexG.entities.TopicEntity;
import com.alexG.entities.UserEntity;

@Component
public class CacheDomainImpl implements CacheDomain {

	private List<Category> categoriesCache;

	private Repository repo;

	private ModelMapperCategoryEntityToCategory mapper = Mappers.getMapper(ModelMapperCategoryEntityToCategory.class);

	@Autowired
	public CacheDomainImpl(Repository repo) {
		this.repo = repo;
		categoriesCache = mapper.categoryEntitiesToCategory(repo.findAllCategories());
	}

	public List<Category> getCategories() {
		return categoriesCache;
	}

	public Topic addTopic(Topic topic, String username, Long categoryId, Long technologyId) {
		Technology technology = findTechnology(categoryId, technologyId);
		technology.addTopic(topic);
		topic.userCreator = getUserByUsername(username);
		TopicEntity topicEntity = new TopicEntity(topic.title, topic.question, findTechnologyEntity(technologyId),
				findUserEntity(topic.userCreator.getId()));
		repo.saveTopic(topicEntity);
		topic.setId(topicEntity.getId());
		return topic;
	}

	public Technology addTechnology(Technology technology, Long categoryId) {
		Category categ = findCategory(categoryId);
		categ.addTechnology(technology);
		TechnologyEntity techEntity = new TechnologyEntity(technology.title, findCategoryEntity(categ.id));
		repo.saveTechnology(techEntity);
		technology.setId(techEntity.getId());
		return technology;
	}

	public void changeTopicFromTechnolgy(Long categoryId, Long topicId, Long actualTechnologyId, Long newTechnologyId) {
		Category categ = null;
		categ = findCategory(categoryId);
		categ.changeTopicFromTechnolgy(topicId, actualTechnologyId, newTechnologyId);
		TopicEntity topicEntity = findTopicEntity(topicId);
		topicEntity.setTechnology(findTechnologyEntity(newTechnologyId));
		repo.saveTopic(topicEntity);
	}

	public void changeCategTitle(Long categoryId, String newTitle) {
		Category categ = findCategory(categoryId);
		categ.changeTitle(newTitle);
		CategoryEntity categEntity = findCategoryEntity(categoryId);
		categEntity.setTitle(newTitle);
		repo.saveCategory(categEntity);
	}

	public void deleteTechnology(Long categoryId, Long technologyId) {
		Category categ = findCategory(categoryId);
		categ.deleteTechnology(technologyId);
		repo.deleteTechnologyById(technologyId);
	}

	public void deleteTopic(Long categoryId, Long technologyId, Long topicId) {
		Technology technlogy = findTechnology(categoryId, technologyId);
		technlogy.removeTopic(topicId);
		repo.deleteTopicById(topicId);
	}

	public void changeTechnologyTitle(Long categoryId, Long technologyId, String newTitle) {
		Technology technlogy = findTechnology(categoryId, technologyId);
		technlogy.changeTitle(newTitle);
		TechnologyEntity techEntity = findTechnologyEntity(technologyId);
		techEntity.setTitle(newTitle);
		repo.saveTechnology(techEntity);
	}

	public void editTopicQuestion(String username, Long categoryId, Long technologyId, Long topicId, String newQuestion)
			throws Exception {
		Topic topic = findTopic(categoryId, technologyId, topicId);
		if (topic.isUserCreator(getUserByUsername(username))) {
			TopicEntity topicEntity = findTopicEntity(topicId);
			topicEntity.setQuestion(newQuestion);
			repo.saveTopic(topicEntity);
			topic.editQuestion(newQuestion);
		} else {
			throw new Exception("This user can't edit this question");
		}
	}

	public Answer addAnswer(Answer answer, Long categoryId, Long technologyId, Long topicId) {
		Topic topic = findTopic(categoryId, technologyId, topicId);
		topic.addAnswer(answer);
		AnswerEntity answerEntity = new AnswerEntity(answer.text, findTopicEntity(topicId),
				findUserEntity(answer.userCreator.getId()));
		repo.saveAnswer(answerEntity);
		answer.setId(answerEntity.getId());
		return answer;
	}

	public void rateAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int rating)
			throws Exception {
		Answer answer = findAnswer(categoryId, technologyId, topicId, answerId);
		if (answer.isUserCreator(getUserByUsername(username))) {
			AnswerEntity answerEntity = findAnswerEntity(answerId);
			if (answer.isRatingOk(rating)) {
				answerEntity.setRating(rating);
				repo.saveAnswer(answerEntity);
				answer.setRating(rating);
			} else {
				throw new Exception("This rating is invalid");
			}
		} else {
			throw new Exception("This user can't rate this answer");
		}
	}

	public void pointAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int points)
			throws Exception {
		Answer answer = findAnswer(categoryId, technologyId, topicId, answerId);
		User user = getUserByUsername(username);
		if (answer.isUserCreator(user)) {
			if (user.isPointingOk(points)) {
				UserEntity userEnt = findUserEntity(answer.userCreator.getId());
				userEnt.addPoints(points);
				repo.saveUser(userEnt);
				answer.userCreator.addPoints(points);
			} else {
				throw new Exception("This pointing is invalid");
			}
		} else {
			throw new Exception("This user can't point this answer");
		}
	}

	public void editAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, String newText)
			throws Exception {
		Answer answer = findAnswer(categoryId, technologyId, topicId, answerId);
		if (answer.isUserCreator(getUserByUsername(username))) {
			AnswerEntity answerEntity = findAnswerEntity(answerId);
			answerEntity.setText(newText);
			repo.saveAnswer(answerEntity);
			answer.editText(newText);
		} else {
			throw new Exception("This user can't edit this answer");
		}
	}

	public void deleteAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId)
			throws Exception {
		Topic topic = findTopic(categoryId, technologyId, topicId);
		topic.deleteAnswer(answerId);
		if (topic.isUserCreatorForAnswer(getUserByUsername(username), answerId)) {
			repo.deleteAnswerById(answerId);
		} else {
			throw new Exception("This user can't remove this answer");
		}
	}

	private UserEntity findUserEntity(Long userId) {
		try {
			return repo.findUserById(userId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> User Entity with id: " + userId);
			throw exception;
		}
	}

	private CategoryEntity findCategoryEntity(Long categroyId) {
		try {
			return repo.findCategoryById(categroyId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Category Entity with id: " + categroyId);
			throw exception;
		}
	}

	private TechnologyEntity findTechnologyEntity(Long technologyId) {
		try {
			return repo.findTechnologyById(technologyId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Technology Entity with id: " + technologyId);
			throw exception;
		}
	}

	private TopicEntity findTopicEntity(Long topicId) {
		try {
			return repo.findTopicById(topicId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Topic Entity with id: " + topicId);
			throw exception;
		}
	}

	private AnswerEntity findAnswerEntity(Long answerId) {
		try {
			return repo.findAnswerById(answerId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Answer Entity with id: " + answerId);
			throw exception;
		}
	}

	private Technology findTechnology(Long categoryId, Long technologyId) {
		Category categ = findCategory(categoryId);
		return categ.findTechnology(technologyId);
	}

	private Topic findTopic(Long categoryId, Long technologyId, Long topicId) {
		Technology technology = findTechnology(categoryId, technologyId);
		return technology.findTopic(topicId);
	}

	private Answer findAnswer(Long categoryId, Long technologyId, Long topicId, Long answerId) {
		Topic topic = findTopic(categoryId, technologyId, topicId);
		return topic.findAnswer(answerId);
	}

	private Category findCategory(Long categoryId) {
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

	private User getUserByUsername(String username) {
		return mapper.userEntityToUser(repo.findUserByUsername(username));
	}
}
