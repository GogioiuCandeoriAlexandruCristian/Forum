package com.alexG.model;

import java.util.List;

import org.mapstruct.Mapper;

import com.alexG.domain.entities.Answer;
import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.Technology;
import com.alexG.domain.entities.Topic;
import com.alexG.domain.entities.User;
import com.alexG.security.model.UserModel;

@Mapper
public interface ModelMapperDomainToModel {
	List<CategoryModel> categoriesToCategoryModel(List<Category> categories);

	List<TechnologyModel> technologiesToTechnologyModel(List<Technology> technologies);

	TechnologyModel technlogyToTechnologyModel(Technology technology);

	TopicModel topicToTopicModel(Topic topic);

	AnswerModel answerToAnswerModel(Answer answer);

	UserModel userToUserModel(User user);
}