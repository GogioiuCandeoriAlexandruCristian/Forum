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

	public TopicModel addTopic(TopicModel topicModel, String username, Long categoryId, Long technologyId) {
		Topic topic = cacheDomain.addTopic(mapperModelToDomain.topicModelToTopic(topicModel), username, categoryId,
				technologyId);
		topicModel.setId(topic.getId());
		topicModel.userCreator = mapperDomnainToModel.userToUserModel(topic.userCreator);
		findTechnology(categoryId, technologyId).addTopic(topicModel);
		return topicModel;
	}

	public TechnologyModel addTechnology(TechnologyModel technologyModel, Long categoryId) {
		Technology technology = cacheDomain
				.addTechnology(mapperModelToDomain.technologyModelToTechnology(technologyModel), categoryId);
		technologyModel.setId(technology.getId());
		findCategory(categoryId).addTechnology(technologyModel);
		return technologyModel;
	}

	public TechnologyModel changeTopicFromTechnolgy(Long topicId, Long actualTechnologyId, Long newTechnologyId) {
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
		return tupleTechnologies._2;
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

	public CategoryModel changeCategTitle(Long categoryId, String newTitle) {
		cacheDomain.changeCategTitle(categoryId, newTitle);
		CategoryModel categ = findCategory(categoryId);
		categ.title = newTitle;
		return categ;
	}

	public void deleteTechnology(Long categoryId, Long technologyId) {
		cacheDomain.deleteTechnology(categoryId, technologyId);
		findCategory(categoryId).removeTechnology(technologyId);
	}

	public void deleteTopic(Long categoryId, Long technologyId, Long topicId) {
		cacheDomain.deleteTopic(categoryId, technologyId, topicId);
		findTechnology(categoryId, technologyId).removeTopic(topicId);
	}

	public TechnologyModel changeTechnologyTitle(Long categoryId, Long technologyId, String newTitle) {
		cacheDomain.changeTechnologyTitle(categoryId, technologyId, newTitle);
		TechnologyModel tech = findTechnology(categoryId, technologyId);
		tech.title = newTitle;
		return tech;
	}

	public TopicModel editTopicQuestion(String username, Long categoryId, Long technologyId, Long topicId, String newQuestion)
			throws Exception {
		cacheDomain.editTopicQuestion(username, categoryId, technologyId, topicId, newQuestion);
		TopicModel topic = findTopic(categoryId, technologyId, topicId);
		topic.question = newQuestion;
		return topic;
	}

	public TopicModel addAnswer(AnswerModel answerModel, String username, Long categoryId, Long technologyId, Long topicId) {
		Answer answer = cacheDomain.addAnswer(mapperModelToDomain.answerModelToAnswer(answerModel), username, categoryId,
				technologyId, topicId);
		answerModel.setId(answer.getId());
		answerModel.userCreator = mapperDomnainToModel.userToUserModel(answer.userCreator);
		TopicModel topic = findTopic(categoryId, technologyId, topicId);
		topic.addAnswer(answerModel);
		return topic;
	}

	public AnswerModel rateAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int rating)
			throws Exception {
		cacheDomain.rateAnswer(username, categoryId, technologyId, topicId, answerId, rating);
		AnswerModel answer = findAnswer(categoryId, technologyId, topicId, answerId);
		answer.rating = rating;
		return answer;
	}

	public AnswerModel pointAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId,
			int points) throws Exception {
		cacheDomain.pointAnswer(username, categoryId, technologyId, topicId, answerId, points);
		AnswerModel answer = findAnswer(categoryId, technologyId, topicId, answerId);
		answer.userCreator.addPoints(points);
		return answer;
	}

	public AnswerModel editAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId,
			String newText) throws Exception {
		cacheDomain.editAnswer(username, categoryId, technologyId, topicId, answerId, newText);
		AnswerModel answer = findAnswer(categoryId, technologyId, topicId, answerId);
		answer.text = newText;
		return answer;
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
		return categ.findTechnology(technologyId);
	}

	private TopicModel findTopic(Long categoryId, Long technologyId, Long topicId) {
		TechnologyModel technology = findTechnology(categoryId, technologyId);
		return technology.findTopic(topicId);
	}

	private AnswerModel findAnswer(Long categoryId, Long technologyId, Long topicId, Long answerId) {
		TopicModel topic = findTopic(categoryId, technologyId, topicId);
		return topic.findAnswer(answerId);
	}
}