package com.alexG.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexG.model.AnswerModel;
import com.alexG.model.CategoryModel;
import com.alexG.model.TechnologyModel;
import com.alexG.model.TopicModel;
import com.alexG.payload.response.Response;
import com.alexG.security.model.UserModel;
import com.alexG.service.Service;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ForumController {

	@Autowired
	private Service service;

	@GetMapping("/categories")
	public Response<List<CategoryModel>> getAllCategories() {
		return new Response(service.getAllCategories(), null, false);
	}

	@GetMapping("/technologies")
	public Response<List<TechnologyModel>> getAllTechnologies(
			@RequestParam(name = "categId", required = true) Long categId) {
		try {
			return new Response(service.getAllTechnologies(categId), null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
		
	}


	@PostMapping("/answer/delete")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response getAnswers(Long categoryId, Long technologyId, Long topicId) {
		try {
			service.getAnswers(categoryId, technologyId, topicId);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/topic")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response addTopic(TopicModel topic, Long categoryId, Long technologyId) {
		try {
			service.addTopic(topic, categoryId, technologyId);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/technology/add")
	@PreAuthorize("hasRole('ADMIN')")
	public Response addTechnology(TechnologyModel technology, Long categoryId, String title) {
		try {
			service.addTechnology(technology, categoryId, title);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/topic/change")
	@PreAuthorize("hasRole('ADMIN')")
	public Response changeTopicFromTechnolgy(Long categoryId, Long topicId, Long actualTechnologyId,
			Long newTechnologyId) {
		try {
			service.changeTopicFromTechnolgy(categoryId, topicId, actualTechnologyId, newTechnologyId);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/categ")
	@PreAuthorize("hasRole('ADMIN')")
	public Response changeCategTitle(Long categId, String newTitle) {
		try {
			service.changeCategTitle(categId, newTitle);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/technology/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public Response deleteTechnology(Long categId, Long technologyId) {
		try {
			service.deleteTechnology(categId, technologyId);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/topic/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public Response deleteTopic(Long categoryId, Long technologyId, Long topicId) {
		try {
			service.deleteTopic(categoryId, technologyId, topicId);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/technology/change")
	@PreAuthorize("hasRole('ADMIN')")
	public Response changeTechnologyTitle(Long categoryId, Long technologyId, String newTitle) {
		try {
			service.changeTechnologyTitle(categoryId, technologyId, newTitle);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/topic/edit")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response editTopicQuestion(UserModel user, Long categoryId, Long technologyId, Long topicId,
			String newQuestion) {
		try {
			service.editTopicQuestion(user, categoryId, technologyId, topicId, newQuestion);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/answer/add")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response addAnswer(AnswerModel answer, Long categoryId, Long technologyId, Long topicId) {
		try {
			service.addAnswer(answer, categoryId, technologyId, topicId);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/answer/edit/rating")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response rateAnswer(UserModel user, Long categoryId, Long technologyId, Long topicId, Long answerId,
			int rating) {
		try {
			service.rateAnswer(user, categoryId, technologyId, topicId, answerId, rating);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/answer/edit/points")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response pointAnswer(UserModel user, Long categoryId, Long technologyId, Long topicId, Long answerId,
			int points) {
		try {
			service.pointAnswer(user, categoryId, technologyId, topicId, answerId, points);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/answer/edit/text")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response editAnswer(UserModel user, Long categoryId, Long technologyId, Long topicId, Long answerId,
			String newText) {
		try {
			service.editAnswer(user, categoryId, technologyId, topicId, answerId, newText);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/answer/delete")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response deleteAnswer(UserModel user, Long categoryId, Long technologyId, Long topicId, Long answerId) {
		try {
			service.deleteAnswer(user, categoryId, technologyId, topicId, answerId);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}
}
