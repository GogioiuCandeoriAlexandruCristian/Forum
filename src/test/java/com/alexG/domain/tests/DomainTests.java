package com.alexG.domain.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.alexG.domain.CacheDomainImpl;
import com.alexG.domain.entities.Answer;
import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.Technology;
import com.alexG.domain.entities.Topic;
import com.alexG.domain.entities.User;
import com.alexG.entities.AnswerEntity;
import com.alexG.entities.TechnologyEntity;
import com.alexG.entities.TopicEntity;

public class DomainTests {

	public RepositoryTestImpl repo = new RepositoryTestImpl();
	public CacheDomainImpl cacheDomain = new CacheDomainImpl(repo);

	@Test
	public void rateAnswerTest() throws Exception {
		Long categoryId = 1L;
		Long technologyId = 1L;
		Long topicId = 1L;
		Long answerId = 1L;
		List<Category> categories = cacheDomain.getCategories();

		User user = repo.findTopic(cacheDomain.getCategories(), categoryId, technologyId, topicId).userCreator;
		Answer answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getRating(), 0);
		assertEquals(repo.findAnswerById(answerId).get().getRating(), 0);

		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.rateAnswer(user.getUsername(), 2L, technologyId, topicId, answerId, 3));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.rateAnswer(user.getUsername(), categoryId, 2L, topicId, answerId, 3));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.rateAnswer(user.getUsername(), categoryId, technologyId, 2L, answerId, 3));

		cacheDomain.rateAnswer(user.getUsername(), categoryId, technologyId, topicId, answerId, 3);

		answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getRating(), 3);
		assertEquals(repo.findAnswerById(answerId).get().getRating(), 3);

		org.junit.jupiter.api.Assertions.assertThrows(Exception.class,
				() -> cacheDomain.rateAnswer(user.getUsername(), categoryId, technologyId, topicId, answerId, 6));
		answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getRating(), 3);
		assertEquals(repo.findAnswerById(answerId).get().getRating(), 3);

		org.junit.jupiter.api.Assertions.assertThrows(Exception.class,
				() -> cacheDomain.rateAnswer(user.getUsername(), categoryId, technologyId, topicId, answerId, -1));
		answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getRating(), 3);
		assertEquals(repo.findAnswerById(answerId).get().getRating(), 3);

		User user1 = new User();
		user1.setUsername("wrongUser");
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class,
				() -> cacheDomain.rateAnswer(user1.getUsername(), categoryId, technologyId, topicId, answerId, 3));
		answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getRating(), 3);
		assertEquals(repo.findAnswerById(answerId).get().getRating(), 3);
	}

	@Test
	public void pointAnswerTest() throws Exception {
		Long categoryId = 1L;
		Long technologyId = 1L;
		Long topicId = 1L;
		Long answerId = 1L;
		List<Category> categories = cacheDomain.getCategories();

		User user = repo.findTopic(cacheDomain.getCategories(), categoryId, technologyId, topicId).userCreator;
		Answer answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.userCreator.getPoints(), 0);
		assertEquals(repo.findAnswerById(answerId).get().userCreator.getPoints(), 0);

		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.pointAnswer(user.getUsername(), 2L, technologyId, topicId, answerId, 3));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.pointAnswer(user.getUsername(), categoryId, 2L, topicId, answerId, 3));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.pointAnswer(user.getUsername(), categoryId, technologyId, 2L, answerId, 3));

		cacheDomain.pointAnswer(user.getUsername(), categoryId, technologyId, topicId, answerId, 3);

		answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.userCreator.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().userCreator.getPoints(), 3);

		org.junit.jupiter.api.Assertions.assertThrows(Exception.class,
				() -> cacheDomain.rateAnswer(user.getUsername(), categoryId, technologyId, topicId, answerId, 6));
		answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.userCreator.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().userCreator.getPoints(), 3);

		org.junit.jupiter.api.Assertions.assertThrows(Exception.class,
				() -> cacheDomain.rateAnswer(user.getUsername(), categoryId, technologyId, topicId, answerId, -1));
		answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.userCreator.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().userCreator.getPoints(), 3);

		User user1 = new User();
		user1.setUsername("wrongUser");
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class,
				() -> cacheDomain.pointAnswer(user1.getUsername(), categoryId, technologyId, topicId, answerId, 3));
		answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.userCreator.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().userCreator.getPoints(), 3);
	}

	@Test
	public void addAnswerTest() {
		Long categoryId = 1L;
		Long technologyId = 1L;
		Long topicId = 1L;
		List<Category> categories = cacheDomain.getCategories();
		Answer answer = new Answer();
		answer.id = 2L;
		answer.text = "#2";

		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.addAnswer(answer, "alex", 2L, technologyId, topicId));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.addAnswer(answer, "alex", categoryId, 2L, topicId));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.addAnswer(answer, "alex", categoryId, technologyId, 2L));

		cacheDomain.addAnswer(answer, "alex", categoryId, technologyId, topicId);

		Answer answerAdded = repo.findAnswer(categories, categoryId, technologyId, topicId, 2L);
		assertEquals(answer.id, answerAdded.id);
		assertEquals(answer.text, answerAdded.text);
		assertEquals("alex", answerAdded.userCreator.getUsername());

		AnswerEntity answerEntity = repo.findAnswerById(answer.id).get();
		assertEquals(answer.id, answerEntity.getId());
		assertEquals(answer.text, answerEntity.getText());
		assertEquals("alex", answerEntity.userCreator.getUsername());
	}

	@Test
	public void addTechnologyTest() {
		Long categoryId = 1L;
		List<Category> categories = cacheDomain.getCategories();
		Technology technology = new Technology();
		technology.setId(2L);
		technology.title = "2";

		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.addTechnology(technology, 2L));

		cacheDomain.addTechnology(technology, categoryId);

		Technology techAdded = repo.findTechnology(categories, categoryId, 2L);
		assertEquals(technology.getId(), techAdded.getId());
		assertEquals(technology.title, techAdded.title);

		TechnologyEntity techEntity = repo.findTechnologyById(technology.getId()).get();
		assertEquals(technology.getId(), techEntity.getId());
		assertEquals(technology.title, techEntity.getTitle());
	}

	@Test
	public void addTopicTest() {
		Long categoryId = 1L;
		Long technologyId = 1L;
		Long topicId = 2L;
		List<Category> categories = cacheDomain.getCategories();
		Topic topic = new Topic();
		topic.title = "#2";
		topic.question = "question";

		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.addTopic(topic, "alex", 2L, technologyId));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.addTopic(topic, "alex", categoryId, 2L));

		cacheDomain.addTopic(topic, "alex", categoryId, technologyId);

		Topic topicAdded = repo.findTopic(categories, categoryId, technologyId, topicId);
		assertEquals(topic.id, topicAdded.id);
		assertEquals(topic.title, topicAdded.title);
		assertEquals(topic.question, topicAdded.question);
		assertEquals("alex", topic.userCreator.getUsername());

		TopicEntity topicEntity = repo.findTopicById(topicId).get();
		assertEquals(topic.id, topicEntity.getId());
		assertEquals(topic.title, topicEntity.getTitle());
		assertEquals(topic.question, topicEntity.getQuestion());
		assertEquals("alex", topicEntity.getUserCreator().getUsername());
	}

	@Test
	public void editAnswerTest() throws Exception {
		Long categoryId = 1L;
		Long technologyId = 1L;
		Long topicId = 1L;
		Long answerId = 1L;
		List<Category> categories = cacheDomain.getCategories();
		Answer answer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.text, null);
		assertEquals(repo.findAnswerById(answerId).get().getText(), null);

		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.editAnswer("alex", 2L, technologyId, topicId, answerId, "text"));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.editAnswer("alex", categoryId, 2L, topicId, answerId, "text"));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.editAnswer("alex", categoryId, technologyId, 2L, answerId, "text"));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.editAnswer("alex", categoryId, technologyId, topicId, 2L, "text"));
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class,
				() -> cacheDomain.editAnswer("wrongUserName", categoryId, technologyId, topicId, answerId, "text"));

		cacheDomain.editAnswer("alex", categoryId, technologyId, topicId, answerId, "text");

		Answer editedAnswer = repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(editedAnswer.text, "text");

		AnswerEntity answerEntity = repo.findAnswerById(answer.id).get();
		assertEquals(answerEntity.getText(), "text");
	}

	@Test
	public void editTopicQuestionTest() throws Exception {
		Long categoryId = 1L;
		Long technologyId = 1L;
		Long topicId = 1L;
		List<Category> categories = cacheDomain.getCategories();
		Topic topic = repo.findTopic(categories, categoryId, technologyId, topicId);
		assertEquals(topic.question, null);
		assertEquals(repo.findTopicById(topicId).get().getQuestion(), null);

		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.editTopicQuestion("alex", 2L, technologyId, topicId, "newQuestion"));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.editTopicQuestion("alex", categoryId, 2L, topicId, "newQuestion"));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.editTopicQuestion("alex", categoryId, technologyId, 2L, "newQuestion"));
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class,
				() -> cacheDomain.editTopicQuestion("wrongUserName", categoryId, technologyId, topicId, "newQuestion"));
		cacheDomain.editTopicQuestion("alex", categoryId, technologyId, topicId, "newQuestion");

		Topic editedTopic = repo.findTopic(categories, categoryId, technologyId, topicId);
		assertEquals(topic.question, editedTopic.question);

		TopicEntity topicEntity = repo.findTopicById(topicId).get();
		assertEquals(topic.question, topicEntity.getQuestion());
	}

	@Test
	public void changeTopicFromTechnolgyTest() {
		Long categoryId = 1L;
		Long actualTechnologyId = 1L;
		Long newTechnologyId = 3L;
		Long topicId = 1L;
		List<Category> categories = cacheDomain.getCategories();
		assertNotNull(repo.findTopic(categories, categoryId, actualTechnologyId, topicId));

		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.changeTopicFromTechnolgy(2L, actualTechnologyId, newTechnologyId));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.changeTopicFromTechnolgy(topicId, 2L, newTechnologyId));
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> cacheDomain.changeTopicFromTechnolgy(topicId, actualTechnologyId, 2L));

		cacheDomain.changeTopicFromTechnolgy(topicId, actualTechnologyId, newTechnologyId);

		Technology actualTechnology = repo.findTechnology(categories, categoryId, actualTechnologyId);
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,
				() -> actualTechnology.findTopic(topicId));
		Technology newTechnology = repo.findTechnology(categories, categoryId, newTechnologyId);
		assertNotNull(newTechnology.findTopic(topicId));

		TechnologyEntity newTechnologyEntity = repo.findTechnologyById(newTechnologyId).get();
		assertNotNull(io.vavr.collection.List.ofAll(newTechnologyEntity.getTopics()).find(t -> t.getId() == topicId).get());
	}
}
