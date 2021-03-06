package com.alexG.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexG.model.AnswerModel;
import com.alexG.model.CategoryModel;
import com.alexG.model.TechnologyModel;
import com.alexG.model.TopicModel;
import com.alexG.payload.response.Response;
import com.alexG.security.jwt.JwtUtils;
import com.alexG.service.Service;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ForumController {

	@Autowired
	private Service service;

	@Autowired
	private JwtUtils jwtUtils;

	@GetMapping("/categories")
	public Response<List<CategoryModel>> getAllCategories() {
		return new Response<List<CategoryModel>>(service.getAllCategories(), null, false);
	}

	@GetMapping("/technologies")
	public Response<List<TechnologyModel>> getAllTechnologies(
			@RequestParam(name = "categoryId", required = true) Long categoryId) {
		try {
			return new Response<List<TechnologyModel>>(service.getAllTechnologies(categoryId), null, false);
		} catch (Exception ex) {
			return new Response<List<TechnologyModel>>(null, ex.getMessage(), true);
		}
	}

	@GetMapping("/answer")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response<List<AnswerModel>> getAnswers(@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId,
			@RequestParam(name = "topicId", required = true) Long topicId) {
		try {
			List<AnswerModel> answers = service.getAnswers(categoryId, technologyId, topicId);
			return new Response<List<AnswerModel>>(answers, null, false);
		} catch (Exception ex) {
			return new Response<List<AnswerModel>>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/topic")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response<TopicModel> addTopic(HttpServletRequest request, @Valid @RequestBody TopicModel topic,
			@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId) {
		try {
			String username = getUserFromJwt(request);
			topic = service.addTopic(topic, username, categoryId, technologyId);
			return new Response<TopicModel>(topic, null, false);
		} catch (Exception ex) {
			return new Response<TopicModel>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/technology/add")
	@PreAuthorize("hasRole('ADMIN')")
	public Response<TechnologyModel> addTechnology(@Valid @RequestBody TechnologyModel technology,
			@RequestParam(name = "categoryId", required = true) Long categoryId) {
		try {
			technology = service.addTechnology(technology, categoryId);
			return new Response<TechnologyModel>(technology, null, false);
		} catch (Exception ex) {
			return new Response<TechnologyModel>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/topic/change")
	@PreAuthorize("hasRole('ADMIN')")
	public Response<TechnologyModel> changeTopicFromTechnolgy(@RequestParam(name = "topicId", required = true) Long topicId,
			@RequestParam(name = "actualTechnologyId", required = true) Long actualTechnologyId,
			@RequestParam(name = "newTechnologyId", required = true) Long newTechnologyId) {
		try {
			TechnologyModel technology = service.changeTopicFromTechnolgy(topicId, actualTechnologyId, newTechnologyId);
			return new Response<TechnologyModel>(technology, null, false);
		} catch (Exception ex) {
			return new Response<TechnologyModel>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/categ")
	@PreAuthorize("hasRole('ADMIN')")
	public Response<CategoryModel> changeCategTitle(@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "newTitle", required = true) String newTitle) {
		try {
			CategoryModel category = service.changeCategTitle(categoryId, newTitle);
			return new Response<CategoryModel>(category, null, false);
		} catch (Exception ex) {
			return new Response<CategoryModel>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/technology/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public Response deleteTechnology(@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId) {
		try {
			service.deleteTechnology(categoryId, technologyId);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/topic/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public Response deleteTopic(@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId,
			@RequestParam(name = "topicId", required = true) Long topicId) {
		try {
			service.deleteTopic(categoryId, technologyId, topicId);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/technology/change")
	@PreAuthorize("hasRole('ADMIN')")
	public Response<TechnologyModel> changeTechnologyTitle(@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId,
			@RequestParam(name = "newTitle", required = true) String newTitle) {
		try {
			TechnologyModel technology = service.changeTechnologyTitle(categoryId, technologyId, newTitle);
			return new Response<TechnologyModel>(technology, null, false);
		} catch (Exception ex) {
			return new Response<TechnologyModel>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/topic/edit")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response<TopicModel> editTopicQuestion(HttpServletRequest request,
			@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId,
			@RequestParam(name = "topicId", required = true) Long topicId,
			@RequestParam(name = "newQuestion", required = true) String newQuestion) {
		try {
			String username = getUserFromJwt(request);
			TopicModel topic = service.editTopicQuestion(username, categoryId, technologyId, topicId, newQuestion);
			return new Response<TopicModel>(topic, null, false);
		} catch (Exception ex) {
			return new Response<TopicModel>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/answer/add")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response<TopicModel> addAnswer(@Valid @RequestBody AnswerModel answer,
			@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId,
			@RequestParam(name = "topicId", required = true) Long topicId,
			HttpServletRequest request) {
		try {
			String username = getUserFromJwt(request);
			TopicModel topic = service.addAnswer(answer, username, categoryId, technologyId, topicId);
			return new Response<TopicModel>(topic, null, false);
		} catch (Exception ex) {
			return new Response<TopicModel>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/answer/edit/rating")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response<AnswerModel> rateAnswer(HttpServletRequest request,
			@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId,
			@RequestParam(name = "topicId", required = true) Long topicId,
			@RequestParam(name = "answerId", required = true) Long answerId, int rating) {
		try {
			String username = getUserFromJwt(request);
			AnswerModel answer = service.rateAnswer(username, categoryId, technologyId, topicId, answerId, rating);
			return new Response<AnswerModel>(answer, null, false);
		} catch (Exception ex) {
			return new Response<AnswerModel>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/answer/edit/points")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response<AnswerModel> pointAnswer(HttpServletRequest request,
			@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId,
			@RequestParam(name = "topicId", required = true) Long topicId,
			@RequestParam(name = "answerId", required = true) Long answerId,
			@RequestParam(name = "points", required = true) int points) {
		try {
			String username = getUserFromJwt(request);
			AnswerModel answer = service.pointAnswer(username, categoryId, technologyId, topicId, answerId, points);
			return new Response<AnswerModel>(answer, null, false);
		} catch (Exception ex) {
			return new Response<AnswerModel>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/answer/edit/text")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response<AnswerModel> editAnswer(HttpServletRequest request,
			@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId,
			@RequestParam(name = "topicId", required = true) Long topicId,
			@RequestParam(name = "answerId", required = true) Long answerId,
			@RequestParam(name = "newText", required = true) String newText) {
		try {
			String username = getUserFromJwt(request);
			AnswerModel answer = service.editAnswer(username, categoryId, technologyId, topicId, answerId, newText);
			return new Response<AnswerModel>(answer, null, false);
		} catch (Exception ex) {
			return new Response<AnswerModel>(null, ex.getMessage(), true);
		}
	}

	@PostMapping("/answer/delete")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Response deleteAnswer(HttpServletRequest request,
			@RequestParam(name = "categoryId", required = true) Long categoryId,
			@RequestParam(name = "technologyId", required = true) Long technologyId,
			@RequestParam(name = "topicId", required = true) Long topicId,
			@RequestParam(name = "answerId", required = true) Long answerId) {
		try {
			String username = getUserFromJwt(request);
			service.deleteAnswer(username, categoryId, technologyId, topicId, answerId);
			return new Response(null, null, false);
		} catch (Exception ex) {
			return new Response(null, ex.getMessage(), true);
		}
	}

	private String getUserFromJwt(HttpServletRequest request) {
		String jwt = request.getHeader("Authorization");
		if (StringUtils.hasText(jwt) && jwt.startsWith("Bearer ")) {
			jwt = jwt.substring(7, jwt.length());
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				return jwtUtils.getUserNameFromJwtToken(jwt);
			}
		}
		return null;
	}
}
