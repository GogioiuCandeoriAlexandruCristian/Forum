package com.alexG.model;

import java.util.List;

public interface CacheModel {

	List<CategoryModel> findAllCategories();

	List<TechnologyModel> findTechnologies(Long categoryId);

	List<AnswerModel> getAnswers(Long categoryId, Long technologyId, Long topicId);

	TopicModel addTopic(TopicModel topic, String username, Long categoryId, Long technologyId);

	TechnologyModel addTechnology(TechnologyModel technology, Long categoryId);

	TechnologyModel changeTopicFromTechnolgy(Long topicId, Long actualTechnologyId, Long newTechnologyId);

	CategoryModel changeCategTitle(Long categId, String newTitle);

	void deleteTechnology(Long categId, Long technologyId);

	void deleteTopic(Long categoryId, Long technologyId, Long topicId);

	TechnologyModel changeTechnologyTitle(Long categoryId, Long technologyId, String newTitle);

	TopicModel editTopicQuestion(String username, Long categoryId, Long technologyId, Long topicId, String newQuestion)
			throws Exception;

	TopicModel addAnswer(AnswerModel answer, String username, Long categoryId, Long technologyId, Long topicId);

	AnswerModel rateAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int rating)
			throws Exception;

	AnswerModel pointAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int points)
			throws Exception;

	AnswerModel editAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, String newText)
			throws Exception;

	void deleteAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId) throws Exception;

}
