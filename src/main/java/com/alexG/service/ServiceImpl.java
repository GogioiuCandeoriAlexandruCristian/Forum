package com.alexG.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alexG.model.AnswerModel;
import com.alexG.model.CacheModel;
import com.alexG.model.CategoryModel;
import com.alexG.model.TechnologyModel;
import com.alexG.model.TopicModel;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

	@Autowired
	private CacheModel cacheModel;

	public List<CategoryModel> getAllCategories() {
		return cacheModel.findAllCategories();
	}

	public List<TechnologyModel> getAllTechnologies(Long categId) {
		return cacheModel.findTechnologies(categId);
	}

	public List<AnswerModel> getAnswers(Long categoryId, Long technologyId, Long topicId) {
		return cacheModel.getAnswers(categoryId, technologyId, topicId);
	}

	public TopicModel addTopic(TopicModel topic, String username,  Long categoryId, Long technologyId) {
		return cacheModel.addTopic(topic, username, categoryId, technologyId);
	}

	public TechnologyModel changeTopicFromTechnolgy(Long topicId, Long actualTechnologyId, Long newTechnologyId) {
		return cacheModel.changeTopicFromTechnolgy(topicId, actualTechnologyId, newTechnologyId);
	}

	public CategoryModel changeCategTitle(Long categId, String newTitle) {
		return cacheModel.changeCategTitle(categId, newTitle);
	}

	public void deleteTechnology(Long categId, Long technologyId) {
		cacheModel.deleteTechnology(categId, technologyId);
	}

	public void deleteTopic(Long categoryId, Long technologyId, Long topicId) {
		cacheModel.deleteTopic(categoryId, technologyId, topicId);
	}

	public TechnologyModel changeTechnologyTitle(Long categoryId, Long technologyId, String newTitle) {
		return cacheModel.changeTechnologyTitle(categoryId, technologyId, newTitle);
	}

	public TopicModel editTopicQuestion(String username, Long categoryId, Long technologyId, Long topicId, String newQuestion)
			throws Exception {
		return cacheModel.editTopicQuestion(username, categoryId, technologyId, topicId, newQuestion);
	}

	public TopicModel addAnswer(AnswerModel answer, String username, Long categoryId, Long technologyId, Long topicId) {
		return cacheModel.addAnswer(answer, username, categoryId, technologyId, topicId);
	}

	public AnswerModel rateAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int rating)
			throws Exception {
		return cacheModel.rateAnswer(username, categoryId, technologyId, topicId, answerId, rating);
	}

	public AnswerModel pointAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int points)
			throws Exception {
		return cacheModel.pointAnswer(username, categoryId, technologyId, topicId, answerId, points);
	}

	public AnswerModel editAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId,
			String newText) throws Exception {
		return cacheModel.editAnswer(username, categoryId, technologyId, topicId, answerId, newText);
	}

	public void deleteAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId)
			throws Exception {
		cacheModel.deleteAnswer(username, categoryId, technologyId, topicId, answerId);
	}

	public TechnologyModel addTechnology(TechnologyModel technology, Long categoryId) {
		return cacheModel.addTechnology(technology, categoryId);
	}

}
