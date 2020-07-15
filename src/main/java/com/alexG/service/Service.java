package com.alexG.service;

import java.util.List;

import com.alexG.model.AnswerModel;
import com.alexG.model.CategoryModel;
import com.alexG.model.TechnologyModel;
import com.alexG.model.TopicModel;
import com.alexG.security.model.UserModel;

public interface Service {
	List<CategoryModel> getAllCategories();

	List<TechnologyModel> getAllTechnologies(Long categId);

	List<AnswerModel> getAnswers(Long categoryId, Long technologyId, Long topicId);

	void addTopic(TopicModel topic, String username, Long categoryId, Long technologyId);

	void addTechnology(TechnologyModel technology, Long categoryId);

	void changeTopicFromTechnolgy(Long categoryId, Long topicId, Long actualTechnologyId, Long newTechnologyId);

	void changeCategTitle(Long categId, String newTitle);

	void deleteTechnology(Long categId, Long technologyId);

	void deleteTopic(Long categoryId, Long technologyId, Long topicId);

	void changeTechnologyTitle(Long categoryId, Long technologyId, String newTitle);

	void editTopicQuestion(String username, Long categoryId, Long technologyId, Long topicId, String newQuestion)
			throws Exception;

	void addAnswer(AnswerModel answer, Long categoryId, Long technologyId, Long topicId);

	void rateAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int rating)
			throws Exception;

	void pointAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int points)
			throws Exception;

	void editAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, String newText)
			throws Exception;

	void deleteAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId) throws Exception;

}
