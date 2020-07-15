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
public interface ModelMapperModelToDomain {
	List<Category> categoriesModelToCategories(List<CategoryModel> categories);

	List<Technology> technologiesModelToTechnologies(List<TechnologyModel> technologies);

	Technology technologyModelToTechnology(TechnologyModel technology);

	Topic topicModelToTopic(TopicModel topic);

	Answer answerModelToAnswer(AnswerModel answer);

	User userModelToUser(UserModel user);
}
