package com.gft.estudosapidevdojo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.estudosapidevdojo.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	public List<Student> findByNameContaining(String name);

}
