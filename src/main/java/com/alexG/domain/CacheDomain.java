package com.alexG.domain;

import java.util.List;

import com.alexG.domain.entities.Answer;
import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.Technology;
import com.alexG.domain.entities.Topic;
import com.alexG.domain.entities.User;

public interface CacheDomain {

	List<Category> getCategories();

	Topic addTopic(Topic topic, String username, Long categoryId, Long technologyId);

	Technology addTechnology(Technology technology, Long categoryId);

	void changeTopicFromTechnolgy(Long categoryId, Long topicId, Long actualTechnologyId, Long newTechnologyId);

	void changeCategTitle(Long categId, String newTitle);

	void deleteTechnology(Long categId, Long technologyId);

	void deleteTopic(Long categoryId, Long technologyId, Long topicId);

	void changeTechnologyTitle(Long categoryId, Long technologyId, String newTitle);

	void editTopicQuestion(String username, Long categoryId, Long technologyId, Long topicId, String newQuestion)
			throws Exception;

	Answer addAnswer(Answer answer, Long categoryId, Long technologyId, Long topicId);

	void rateAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int rating)
			throws Exception;

	void pointAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, int points)
			throws Exception;

	void editAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId, String newText)
			throws Exception;

	void deleteAnswer(String username, Long categoryId, Long technologyId, Long topicId, Long answerId) throws Exception;

}
