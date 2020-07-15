package com.alexG.domain.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.alexG.domain.CacheDomainImpl;
import com.alexG.domain.entities.Answer;
import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.User;



public class DomainTests {

	public RepositoryTestImpl repo = new RepositoryTestImpl();
	public CacheDomainImpl cacheDomain = new CacheDomainImpl(repo);

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	//@Test
	public void rateAnswerTest() throws Exception {
		Long categoryId = 1L;
		Long technologyId = 1L;
		Long topicId = 1L;
		Long answerId = 1L;
		List<Category> categories = cacheDomain.getCategories();
		
		User user = repo.findTopic(cacheDomain.getCategories(), categoryId, technologyId, topicId).userCreator;
		Answer answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getRating(), 0);
		assertEquals(repo.findAnswerById(answerId).get().getRating(), 0);

		cacheDomain.rateAnswer(user, categoryId, technologyId, topicId, answerId, 3);
	
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getRating(), 3);
		assertEquals(repo.findAnswerById(answerId).get().getRating(), 3);
	
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> cacheDomain.rateAnswer(user, categoryId, technologyId, topicId, answerId, 6));
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getRating(), 3);
		assertEquals(repo.findAnswerById(answerId).get().getRating(), 3);
	
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> cacheDomain.rateAnswer(user, categoryId, technologyId, topicId, answerId, -1));
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getRating(), 3);
		assertEquals(repo.findAnswerById(answerId).get().getRating(), 3);

		User user1 = new User();
		user1.setUsername("wrongUser");
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> cacheDomain.rateAnswer(user1, categoryId, technologyId, topicId, answerId, 3));
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
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
		Answer answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.userCreator.getPoints(), 0);
		assertEquals(repo.findAnswerById(answerId).get().userCreator.getPoints(), 0);

		cacheDomain.pointAnswer(user, categoryId, technologyId, topicId, answerId, 3);
	
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.userCreator.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().userCreator.getPoints(), 3);
	
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> cacheDomain.rateAnswer(user, categoryId, technologyId, topicId, answerId, 6));
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.userCreator.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().userCreator.getPoints(), 3);
	
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> cacheDomain.rateAnswer(user, categoryId, technologyId, topicId, answerId, -1));
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.userCreator.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().userCreator.getPoints(), 3);
	
		User user1 = new User();
		user1.setUsername("wrongUser");
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> cacheDomain.pointAnswer(user1, categoryId, technologyId, topicId, answerId, 3));
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.userCreator.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().userCreator.getPoints(), 3);
	}
}
