package com.gft.estudosapidevdojo.resource;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.estudosapidevdojo.model.Student;
import com.gft.estudosapidevdojo.repository.StudentRepository;

@RestController
@RequestMapping("/v1/students")
public class StudentResource {

	@Autowired
	StudentRepository studentRepository;

	@GetMapping
	public Page<Student> listAll(@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false) Pageable pageable) {
		return this.studentRepository.findByNameContaining(name, pageable);
	}

	@GetMapping("/{id}")
	public Student getById(@PathVariable Long id) {
		return this.studentRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});
	}

	@Transactional(rollbackFor = Exception.class) // ANOTAÇÃO PARA TORNAR O MÉTODO EM UMA TRANSACTION (ROLLBACK NO CASO
													// DE ERROS)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Student save(@RequestBody @Valid Student student) {
		return this.studentRepository.save(student);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		this.studentRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public Student updateStudent(@PathVariable Long id, @RequestBody @Valid Student student) {
		Student existingStudent = this.studentRepository.findById(id).orElseThrow(() -> {
			throw new EmptyResultDataAccessException(1);
		});

		BeanUtils.copyProperties(student, existingStudent, "id");

		return this.studentRepository.save(existingStudent);
	}

}
