package com.alexG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexG.entities.AnswerEntity;


@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

}
