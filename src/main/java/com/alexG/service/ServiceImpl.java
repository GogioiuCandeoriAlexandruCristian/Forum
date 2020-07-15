package com.alexG.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alexG.model.AnswerModel;
import com.alexG.model.CacheModel;
import com.alexG.model.CategoryModel;
import com.alexG.model.TechnologyModel;
import com.alexG.model.TopicModel;
import com.alexG.security.model.UserModel;

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

	public void addTopic(TopicModel topic, Long categoryId, Long technologyId) {
		cacheModel.addTopic(topic, categoryId, technologyId);
	}

	public void changeTopicFromTechnolgy(Long categoryId, Long topicId, Long actualTechnologyId, Long newTechnologyId) {
		cacheModel.changeTopicFromTechnolgy(categoryId, topicId, actualTechnologyId, newTechnologyId);
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

	public void editTopicQuestion(UserModel user, Long categoryId, Long technologyId, Long topicId, String newQuestion)
			throws Exception {
		cacheModel.editTopicQuestion(user, categoryId, technologyId, topicId, newQuestion);
	}

	public void addAnswer(AnswerModel answer, Long categoryId, Long technologyId, Long topicId) {
		cacheModel.addAnswer(answer, categoryId, technologyId, topicId);
	}

	public void rateAnswer(UserModel user, Long categoryId, Long technologyId, Long topicId, Long answerId, int rating)
			throws Exception {
		cacheModel.rateAnswer(user, categoryId, technologyId, topicId, answerId, rating);
	}

	public void pointAnswer(UserModel user, Long categoryId, Long technologyId, Long topicId, Long answerId, int points)
			throws Exception {
		cacheModel.pointAnswer(user, categoryId, technologyId, topicId, answerId, points);
	}

	public void editAnswer(UserModel user, Long categoryId, Long technologyId, Long topicId, Long answerId,
			String newText) throws Exception {
		cacheModel.editAnswer(user, categoryId, technologyId, topicId, answerId, newText);
	}

	public void deleteAnswer(UserModel user, Long categoryId, Long technologyId, Long topicId, Long answerId)
			throws Exception {
		cacheModel.deleteAnswer(user, categoryId, technologyId, topicId, answerId);
	}

	public void addTechnology(TechnologyModel technology, Long categoryId, String title) {
		cacheModel.addTechnology(technology, categoryId, title);
	}

}
