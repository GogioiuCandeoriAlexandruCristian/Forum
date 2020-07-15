package com.alexG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexG.entities.TechnologyEntity;


@Repository
public interface TechnologyRepository extends JpaRepository<TechnologyEntity, Long> {

}
