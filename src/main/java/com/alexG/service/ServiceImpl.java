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

	public void addTopic(TopicModel topic, String username,  Long categoryId, Long technologyId) {
		cacheModel.addTopic(topic, username, categoryId, technologyId);
	}

	public void changeTopicFromTechnolgy(Long topicId, Long actualTechnologyId, Long newTechnologyId) {
		cacheModel.changeTopicFromTechnolgy(topicId, actualTechnologyId, newTechnologyId);
	}

	public void changeCategTitle(Long categId, String newTitle) {
		cacheModel.changeCategTitle(categId, newTitle);
	}

	public void deleteTechnology(Long categId, Long technologyId) {
		cacheModel.deleteTechnology(categId, technologyId);
	}

	public void deleteTopic(Long categoryId, Long technologyId, Long topicId) {
		cacheModel.deleteTopic(categoryId, technologyId, topicId);
	}

	public void changeTechnologyTitle(Long categoryId, Long technologyId, String newTitle) {
		cacheModel.changeTechnologyTitle(categoryId, technologyId, newTitle);
	}

	public void editTopicQuestion(String username, Long categoryId, Long technologyId, Long topicId, String newQuestion)
			throws Exception {
		cacheModel.editTopicQuestion(username, categoryId, technologyId, topicId, newQuestion);
	}

	public void addAnswer(AnswerModel answer, Long categoryId, Long technologyId, Long topicId) {
		cacheModel.addAnswer(answer, categoryId, technologyId, topicId);
	}

	public void rateAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int rating)
			throws Exception {
		cacheModel.rateAnswer(username, categoryId, technologyId, topicId, answerId, rating);
	}

	public void pointAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int points)
			throws Exception {
		cacheModel.pointAnswer(username, categoryId, technologyId, topicId, answerId, points);
	}

	public void editAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId,
			String newText) throws Exception {
		cacheModel.editAnswer(username, categoryId, technologyId, topicId, answerId, newText);
	}

	public void deleteAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId)
			throws Exception {
		cacheModel.deleteAnswer(username, categoryId, technologyId, topicId, answerId);
	}

	public void addTechnology(TechnologyModel technology, Long categoryId) {
		cacheModel.addTechnology(technology, categoryId);
	}

}
