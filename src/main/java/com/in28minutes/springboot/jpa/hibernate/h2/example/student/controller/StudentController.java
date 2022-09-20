package com.in28minutes.springboot.jpa.hibernate.h2.example.student.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.springboot.jpa.hibernate.h2.example.student.Student;
import com.in28minutes.springboot.jpa.hibernate.h2.example.student.StudentRepository;

@RestController
@RequestMapping("students")
public class StudentController {

	@Autowired
	private StudentRepository repository;

	@GetMapping({ "/", "" })
	public List<Student> studentList() {

		return repository.findAll();
	}

	@GetMapping("/detail/{id}")
	public Student detail(@PathVariable("id") final Long id) {

		Optional<Student> studentopt = repository.findById(id);

		return studentopt.get();
	}

	@PutMapping({ "/", "" })
	public Student updateStudent(@RequestBody @Base64Encoded Student student) throws Exception {

		// check existing
		Optional<Student> studentOpt = repository.findById(student.getId());

		if (studentOpt.isEmpty())
			throw new Exception("Not found");

		return repository.save(student);
	}

	@PostMapping({ "/", "" })
	public Student newStudent(@RequestBody @Base64Encoded Student student) throws Exception {

		// check existing
		Optional<Student> studentOpt = Optional.empty();
		if (student.getId() != null)
			studentOpt = repository.findById(student.getId());
		if (studentOpt.isPresent())
			throw new Exception("Student with id[" + student.getId() + "] already existed");

		return repository.save(student);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") final Long id) throws Exception {

		// check existing
		Optional<Student> studentOpt = Optional.empty();
		if (id != null)
			studentOpt = repository.findById(id);
		if (studentOpt.isEmpty())
			throw new Exception("Student didn't existed");
		
		repository.deleteById(id);
		return ResponseEntity.ok("Deleted");
	}

}
