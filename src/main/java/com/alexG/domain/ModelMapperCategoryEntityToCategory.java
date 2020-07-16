package com.alexG.domain;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.alexG.domain.entities.Answer;
import com.alexG.domain.entities.Category;
import com.alexG.domain.entities.Role;
import com.alexG.domain.entities.Technology;
import com.alexG.domain.entities.Topic;
import com.alexG.domain.entities.User;
import com.alexG.entities.AnswerEntity;
import com.alexG.entities.CategoryEntity;
import com.alexG.entities.RoleEntity;
import com.alexG.entities.TechnologyEntity;
import com.alexG.entities.TopicEntity;
import com.alexG.entities.UserEntity;

@Mapper
public interface ModelMapperCategoryEntityToCategory {
	List<Category> categoryEntitiesToCategory(List<CategoryEntity> categories);
	
	Category categoryEntityToCategory(CategoryEntity category);

	Technology technologyEntityToTechnology(TechnologyEntity technology);

	Topic topicEntityToTopic(TopicEntity topic);

	Answer answerEntityToAnswer(AnswerEntity answer);

	@Mapping(target = "password", ignore = true)
	User userEntityToUser(UserEntity user);

	Role roleEntityToRole(RoleEntity user);
}