package com.alexG.model;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alexG.domain.entities.Answer;
import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.Role;
import com.alexG.domain.entities.Technology;
import com.alexG.domain.entities.Topic;
import com.alexG.domain.entities.User;
import com.alexG.security.model.RoleModel;
import com.alexG.security.model.UserModel;

@Mapper
public interface ModelMapperDomainToModel {
	List<CategoryModel> categoriesToCategoryModel(List<Category> categories);

	CategoryModel categoryToCategoryModel(Category categories);

	TechnologyModel technlogyToTechnologyModel(Technology technology);

	TopicModel topicToTopicModel(Topic topic);

	AnswerModel answerToAnswerModel(Answer answer);

	@Mapping(target = "password", ignore = true)
	UserModel userToUserModel(User user);
	
	RoleModel roleToRoleModel(Role role);
}