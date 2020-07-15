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
import com.alexG.repository.AnswerRepository;
import com.alexG.repository.CategoryRepository;
import com.alexG.repository.TechnologyRepository;
import com.alexG.repository.TopicRepository;
import com.alexG.repository.UserRepository;

@Component
public class CacheDomainImpl implements CacheDomain {

	private List<Category> categoriesCache;

	private CategoryRepository categRepo;

	private TechnologyRepository techRepo;

	private TopicRepository topicRepo;

	private AnswerRepository answerRepo;

	private UserRepository userRepo;

	private ModelMapperCategoryEntityToCategory mapper = Mappers.getMapper(ModelMapperCategoryEntityToCategory.class);

	@Autowired
	public CacheDomainImpl(CategoryRepository categRepository, TechnologyRepository technologyRepository,
			UserRepository userRepository, TopicRepository topicRepository, AnswerRepository answerRepoository) {
		categRepo = categRepository;
		techRepo = technologyRepository;
		userRepo = userRepository;
		topicRepo = topicRepository;
		answerRepo = answerRepoository;
		categoriesCache = mapper.categoryEntitiesToCategory(categRepo.findAll());
	}

	public List<Category> getCategories() {
		return categoriesCache;
	}

	public Topic addTopic(Topic topic, Long categoryId, Long technologyId) {
		TopicEntity topicEntity = new TopicEntity(topic.title, topic.question, findTechnologyEntity(technologyId),
				findUserEntity(topic.userCreator.getId()));
		topicRepo.save(topicEntity);
		topic.setId(topicEntity.getId());
		Technology technlogy = findTechnology(categoryId, technologyId);
		technlogy.addTopic(topic);
		return topic;
	}

	public Technology addTechnology(Technology technology, Long categoryId, String title) {
		Category categ = findCategory(categoryId);
		TechnologyEntity techEntity = new TechnologyEntity(title, findCategoryEntity(categ.id));
		techRepo.save(techEntity);
		technology.setId(techEntity.getId());
		categ.addTechnology(technology);
		return technology;
	}

	public void changeTopicFromTechnolgy(Long categoryId, Long topicId, Long actualTechnologyId, Long newTechnologyId) {
		TopicEntity topicEntity = findTopicEntity(topicId);
		topicEntity.setTechnology(findTechnologyEntity(newTechnologyId));
		topicRepo.save(topicEntity);
		Category categ = findCategory(categoryId);
		categ.changeTopicFromTechnolgy(topicId, actualTechnologyId, newTechnologyId);
	}

	public void changeCategTitle(Long categId, String newTitle) {
		CategoryEntity categEntity = findCategoryEntity(categId);
		categEntity.setTitle(newTitle);
		categRepo.save(categEntity);
		Category categ = findCategory(categId);
		categ.changeTitle(newTitle);
	}

	public void deleteTechnology(Long categId, Long technologyId) {
		Category categ = findCategory(categId);
		techRepo.deleteById(technologyId);
		categ.deleteTechnology(technologyId);
	}

	public void deleteTopic(Long categoryId, Long technologyId, Long topicId) {
		Technology technlogy = findTechnology(categoryId, technologyId);
		topicRepo.deleteById(topicId);
		technlogy.removeTopic(topicId);
	}

	public void changeTechnologyTitle(Long categoryId, Long technologyId, String newTitle) {
		TechnologyEntity techEntity = findTechnologyEntity(technologyId);
		techEntity.setTitle(newTitle);
		techRepo.save(techEntity);
		Technology technlogy = findTechnology(categoryId, technologyId);
		technlogy.changeTitle(newTitle);
	}

	public void editTopicQuestion(User user, Long categoryId, Long technologyId, Long topicId, String newQuestion)
			throws Exception {
		Topic topic = findTopic(categoryId, technologyId, topicId);
		if (topic.isUserCreator(user)) {
			TopicEntity topicEntity = findTopicEntity(topicId);
			topicEntity.setQuestion(newQuestion);
			topicRepo.save(topicEntity);
			topic.editQuestion(newQuestion);
		} else {
			throw new Exception("This user can't edit this question");
		}
	}

	public Answer addAnswer(Answer answer, Long categoryId, Long technologyId, Long topicId) {
		AnswerEntity answerEntity = new AnswerEntity(answer.text, findTopicEntity(topicId), findUserEntity(answer.creatorUser.getId()));
		answerRepo.save(answerEntity);
		answer.setId(answerEntity.getId());
		Topic topic = findTopic(categoryId, technologyId, topicId);
		topic.addAnswer(answer);
		return answer;
	}

	public void rateAnswer(User user, Long categoryId, Long technologyId, Long topicId, Long answerId, int rating)
			throws Exception {
		Answer answer = findAnswer(categoryId, technologyId, topicId, answerId);
		if (answer.isCreatorUser(user)) {
			AnswerEntity answerEntity = findAnswerEntity(answerId);
			answerEntity.setRating(rating);
			answerRepo.save(answerEntity);
			answer.rating = rating;
		} else {
			throw new Exception("This user can't rate this answer");
		}
	}

	public void pointAnswer(User user, Long categoryId, Long technologyId, Long topicId, Long answerId, int points)
			throws Exception {
		Answer answer = findAnswer(categoryId, technologyId, topicId, answerId);
		if (answer.isCreatorUser(user)) {
			AnswerEntity answerEntity = findAnswerEntity(answerId);
			answerEntity.setPoints(points);
			answerRepo.save(answerEntity);
			answer.points = points;
		} else {
			throw new Exception("This user can't point this answer");
		}
	}

	public void editAnswer(User user, Long categoryId, Long technologyId, Long topicId, Long answerId, String newText)
			throws Exception {
		Answer answer = findAnswer(categoryId, technologyId, topicId, answerId);
		if (answer.isCreatorUser(user)) {
			AnswerEntity answerEntity = findAnswerEntity(answerId);
			answerEntity.setText(newText);
			answerRepo.save(answerEntity);
			answer.editText(newText);
		} else {
			throw new Exception("This user can't edit this answer");
		}
	}

	public void deleteAnswer(User user, Long categoryId, Long technologyId, Long topicId, Long answerId)
			throws Exception {
		Topic topic = findTopic(categoryId, technologyId, topicId);
		if (topic.isUserCreatorForAnswer(user, answerId)) {
			answerRepo.deleteById(answerId);
			topic.deleteAnswer(answerId);
		} else {
			throw new Exception("This user can't remove this answer");
		}
	}

	private UserEntity findUserEntity(Long userId) {
		try {
			return userRepo.findById(userId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> User Entity with id: " + userId);
			throw exception;
		}
	}

	private CategoryEntity findCategoryEntity(Long categroyId) {
		try {
			return categRepo.findById(categroyId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Category Entity with id: " + categroyId);
			throw exception;
		}
	}

	private TechnologyEntity findTechnologyEntity(Long technologyId) {
		try {
			return techRepo.findById(technologyId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Technology Entity with id: " + technologyId);
			throw exception;
		}
	}

	private TopicEntity findTopicEntity(Long topicId) {
		try {
			return topicRepo.findById(topicId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Topic Entity with id: " + topicId);
			throw exception;
		}
	}

	private AnswerEntity findAnswerEntity(Long answerId) {
		try {
			return answerRepo.findById(answerId).get();
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
		Category categ = findCategory(categoryId);
		Technology technology = categ.findTechnology(technologyId);
		return technology.findTopic(topicId);
	}

	private Answer findAnswer(Long categoryId, Long technologyId, Long topicId, Long answerId) {
		Category categ = findCategory(categoryId);
		Technology technology = categ.findTechnology(technologyId);
		Topic topic = technology.findTopic(topicId);
		return topic.getAnswer(answerId);
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
}
