package com.alexG.domain.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.alexG.domain.CacheDomainImpl;
import com.alexG.domain.entities.Answer;
import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.User;



public class DomainTests {

	public CacheDomainImpl cacheDomain;
	public RepositoryTestImpl repo = new RepositoryTestImpl();

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void rateAnswerTest() throws Exception {
		cacheDomain = new CacheDomainImpl(repo);
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
	}

	@Test
	public void pointAnswerTest() throws Exception {
		cacheDomain = new CacheDomainImpl(repo);
		Long categoryId = 1L;
		Long technologyId = 1L;
		Long topicId = 1L;
		Long answerId = 1L;
		List<Category> categories = cacheDomain.getCategories();
		
		User user = repo.findTopic(cacheDomain.getCategories(), categoryId, technologyId, topicId).userCreator;
		Answer answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getPoints(), 0);
		assertEquals(repo.findAnswerById(answerId).get().getPoints(), 0);

		cacheDomain.pointAnswer(user, categoryId, technologyId, topicId, answerId, 3);
	
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().getPoints(), 3);
	
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> cacheDomain.rateAnswer(user, categoryId, technologyId, topicId, answerId, 6));
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().getPoints(), 3);
	
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> cacheDomain.rateAnswer(user, categoryId, technologyId, topicId, answerId, -1));
		answer =  repo.findAnswer(categories, categoryId, technologyId, topicId, answerId);
		assertEquals(answer.getPoints(), 3);
		assertEquals(repo.findAnswerById(answerId).get().getPoints(), 3);
	}

}
