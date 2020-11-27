package com.gft.estudosapidevdojo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.estudosapidevdojo.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	public Page<Student> findByNameContaining(String name, Pageable pageable);

}
