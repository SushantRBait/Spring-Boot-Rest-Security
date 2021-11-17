package com.sushant.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sushant.main.model.Project;

public interface ProjectRepository extends JpaRepository<Project,Integer> {

}
