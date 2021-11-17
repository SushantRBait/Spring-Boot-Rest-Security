package com.sushant.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sushant.main.model.Student;
import com.sushant.main.repository.ProjectRepository;
import com.sushant.main.repository.StudentRepository;

import org.springframework.web.bind.annotation.*;  
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


//import javax.validation.Valid;  
import java.net.URI;  
import java.util.Optional;

@RestController
@RequestMapping("/api/Students")
public class StudentController {

	private final StudentRepository studentRepository;
    private final ProjectRepository projectRepository;
    
    @Autowired
	public StudentController(StudentRepository studentRepository, ProjectRepository projectRepository) {
		
		this.studentRepository = studentRepository;
		this.projectRepository = projectRepository;
	}
    
    @PostMapping("/add")
    public ResponseEntity<Student> create( @RequestBody Student student) {
    	Student savedLStudent = studentRepository.save(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedLStudent.getStudentId()).toUri();

        return ResponseEntity.created(location).body(savedLStudent);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<Student> update(@PathVariable Integer id, @RequestBody Student student) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        student.setStudentId(optionalStudent.get().getStudentId());
        studentRepository.save(student);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Student> delete(@PathVariable Integer id) {
        Optional<Student> optionalLibrary = studentRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        studentRepository.delete(optionalLibrary.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Student> getById(@PathVariable Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalStudent.get());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Student>> getAll(Pageable pageable) {
        return ResponseEntity.ok(studentRepository.findAll(pageable));
    }

}
