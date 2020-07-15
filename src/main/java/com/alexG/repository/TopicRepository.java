package com.alexG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexG.entities.TopicEntity;


@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {

}
