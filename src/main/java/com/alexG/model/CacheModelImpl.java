package com.alexG.model;

import java.util.List;
import java.util.NoSuchElementException;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alexG.domain.CacheDomain;
import com.alexG.domain.entities.Answer;
import com.alexG.domain.entities.Technology;
import com.alexG.domain.entities.Topic;

import io.vavr.Tuple2;

@Component
public class CacheModelImpl implements CacheModel {

	private CacheDomain cacheDomain;

	private List<CategoryModel> categoriesCache;

	private ModelMapperDomainToModel mapperDomnainToModel = Mappers.getMapper(ModelMapperDomainToModel.class);

	private ModelMapperModelToDomain mapperModelToDomain = Mappers.getMapper(ModelMapperModelToDomain.class);

	@Autowired
	public CacheModelImpl(CacheDomain cacheDomain) {
		this.cacheDomain = cacheDomain;
		categoriesCache = mapperDomnainToModel.categoriesToCategoryModel(this.cacheDomain.getCategories());
	}

	public List<CategoryModel> findAllCategories() {
		return categoriesCache;
	}

	public List<TechnologyModel> findTechnologies(Long categoryId) {
		CategoryModel categModel = findCategory(categoryId);
		return categModel.getTechnologies();
	}

	public List<AnswerModel> getAnswers(Long categoryId, Long technologyId, Long topicId) {
		return findTopic(categoryId, technologyId, topicId).answers;
	}

	public void addTopic(TopicModel topicModel, String username, Long categoryId, Long technologyId) {
		Topic topic = cacheDomain.addTopic(mapperModelToDomain.topicModelToTopic(topicModel), username, categoryId,
				technologyId);
		topicModel.setId(topic.getId());
		topicModel.userCreator = mapperDomnainToModel.userToUserModel(topic.userCreator);
		findTechnology(categoryId, technologyId).addTopic(topicModel);
	}

	public void addTechnology(TechnologyModel technologyModel, Long categoryId) {
		Technology technology = cacheDomain
				.addTechnology(mapperModelToDomain.technologyModelToTechnology(technologyModel), categoryId);
		technologyModel.setId(technology.getId());
		findCategory(categoryId).addTechnology(technologyModel);
	}

	public void changeTopicFromTechnolgy(Long topicId, Long actualTechnologyId, Long newTechnologyId) {
		cacheDomain.changeTopicFromTechnolgy(topicId, actualTechnologyId, newTechnologyId);
		Tuple2<TechnologyModel, TechnologyModel> tupleTechnologies = findTechnologies(actualTechnologyId,
				newTechnologyId);
		TopicModel topicModel = null;
		for (TopicModel topic : tupleTechnologies._1.topics) {
			if (topic.getId() == topicId) {
				topicModel = topic;
				break;
			}
		}
		if(topicModel == null)
			throw new NoSuchElementException("Not found -> topic  model with id: " + topicId + " in technology with id: " + actualTechnologyId);
		tupleTechnologies._1.removeTopic(topicId);
		tupleTechnologies._2.addTopic(topicModel);
	}

	private Tuple2<TechnologyModel, TechnologyModel> findTechnologies(Long actualTechnologyId, Long newTechnologyId) {
		TechnologyModel actualTechnology = null;
		TechnologyModel newTechnology = null;
		for (CategoryModel category : categoriesCache) {
			for (TechnologyModel tech : category.technologies) {
				if (tech.getId() == actualTechnologyId) {
					actualTechnology = tech;
				}
				if (tech.getId() == newTechnologyId) {
					newTechnology = tech;
				}
			}
		}
		if (actualTechnology == null || newTechnology == null)
			throw new NoSuchElementException("Tehnologies Model not found by this ids");
		return new Tuple2<TechnologyModel, TechnologyModel>(actualTechnology, newTechnology);
	}

	public void changeCategTitle(Long categoryId, String newTitle) {
		cacheDomain.changeCategTitle(categoryId, newTitle);
		findCategory(categoryId).title = newTitle;
	}

	public void deleteTechnology(Long categoryId, Long technologyId) {
		cacheDomain.deleteTechnology(categoryId, technologyId);
		findCategory(categoryId).removeTechnology(technologyId);
	}

	public void deleteTopic(Long categoryId, Long technologyId, Long topicId) {
		cacheDomain.deleteTopic(categoryId, technologyId, topicId);
		findTechnology(categoryId, technologyId).removeTopic(topicId);
	}

	public void changeTechnologyTitle(Long categoryId, Long technologyId, String newTitle) {
		cacheDomain.changeTechnologyTitle(categoryId, technologyId, newTitle);
		findTechnology(categoryId, technologyId).title = newTitle;
	}

	public void editTopicQuestion(String username, Long categoryId, Long technologyId, Long topicId, String newQuestion)
			throws Exception {
		cacheDomain.editTopicQuestion(username, categoryId, technologyId, topicId, newQuestion);
		findTopic(categoryId, technologyId, topicId).question = newQuestion;
	}

	public void addAnswer(AnswerModel answerModel, Long categoryId, Long technologyId, Long topicId) {
		Answer answer = cacheDomain.addAnswer(mapperModelToDomain.answerModelToAnswer(answerModel), categoryId,
				technologyId, topicId);
		answerModel.setId(answer.getId());
		findTopic(categoryId, technologyId, topicId).addAnswer(answerModel);
	}

	public void rateAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int rating)
			throws Exception {
		cacheDomain.rateAnswer(username, categoryId, technologyId, topicId, answerId, rating);
		findAnswer(categoryId, technologyId, topicId, answerId).rating = rating;
	}

	public void pointAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId,
			int points) throws Exception {
		cacheDomain.pointAnswer(username, categoryId, technologyId, topicId, answerId, points);
		findAnswer(categoryId, technologyId, topicId, answerId).points = points;
	}

	public void editAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId,
			String newText) throws Exception {
		cacheDomain.editAnswer(username, categoryId, technologyId, topicId, answerId, newText);
		findAnswer(categoryId, technologyId, topicId, answerId).text = newText;
	}

	public void deleteAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId)
			throws Exception {
		cacheDomain.deleteAnswer(username, categoryId, technologyId, topicId, answerId);
		findTopic(categoryId, technologyId, topicId).removeAnswer(answerId);
	}

	private CategoryModel findCategory(Long categoryId) {
		try {
			CategoryModel category = io.vavr.collection.List.ofAll(categoriesCache)
					.find(categ -> categ.id == categoryId).get();
			return category;
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Category with id: " + categoryId);
			throw exception;
		}
	}

	private TechnologyModel findTechnology(Long categoryId, Long technologyId) {
		CategoryModel categ = findCategory(categoryId);
		try {
			return io.vavr.collection.List.ofAll(categ.getTechnologies()).find(t -> t.getId() == technologyId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Technology Model with id: " + technologyId + " in category with id: " + categoryId);
			throw exception;
		}
	}

	private TopicModel findTopic(Long categoryId, Long technologyId, Long topicId) {
		TechnologyModel technology = findTechnology(categoryId, technologyId);
		try {
			return io.vavr.collection.List.ofAll(technology.topics).find(t -> t.getId() == topicId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Topic Model with id: " + topicId + " in technology with id: " + technologyId);
			throw exception;
		}
	}

	private AnswerModel findAnswer(Long categoryId, Long technologyId, Long topicId, Long answerId) {
		TopicModel topic = findTopic(categoryId, technologyId, topicId);
		try {
			return io.vavr.collection.List.ofAll(topic.answers).find(a -> a.id == answerId).get();
		} catch (NoSuchElementException ex) {
			NoSuchElementException exception = new NoSuchElementException(
					"Not found -> Answer Model with id: " + answerId + " in topic with id: " + topicId);
			throw exception;
		}
	}
}