package com.sushant.main.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sushant.main.model.Student;

public interface StudentRepository extends JpaRepository<Student,Integer> {

}
