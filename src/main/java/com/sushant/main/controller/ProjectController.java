package com.sushant.main.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sushant.main.model.Project;
import com.sushant.main.model.Student;
import com.sushant.main.repository.ProjectRepository;
import com.sushant.main.repository.StudentRepository;

@RestController
@RequestMapping("/api/Projects")
public class ProjectController {
	private final StudentRepository studentRepository;
    private final ProjectRepository projectRepository;
	@Autowired
    public ProjectController(StudentRepository studentRepository, ProjectRepository projectRepository) {
	
		this.studentRepository = studentRepository;
		this.projectRepository = projectRepository;
	}
    
    
	 @PostMapping("/add")
	    public ResponseEntity<Project> create(@RequestBody  Project project) {
	        Optional<Student> optionalStudent = studentRepository.findById(project.getStudent().getStudentId());
	        if (!optionalStudent.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        project.setStudent(optionalStudent.get());

	        Project savedProject = projectRepository.save(project);
	        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	            .buildAndExpand(savedProject.getProjid()).toUri();

	        return ResponseEntity.created(location).body(savedProject);
	    }


	    @PutMapping("/updateById/{id}")
	    public ResponseEntity<Project> update(@RequestBody  Project project, @PathVariable Integer id) {
	        Optional<Student> optionalStudent = studentRepository.findById(project.getStudent().getStudentId());
	        if (!optionalStudent.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        Optional<Project> optionalProject = projectRepository.findById(id);
	        if (!optionalProject.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        project.setStudent(optionalStudent.get());
	        project.setProjid(optionalProject.get().getProjid());
	        projectRepository.save(project);

	        return ResponseEntity.noContent().build();
	    }

	    @DeleteMapping("/deleteById/{id}")
	    public ResponseEntity<Project> delete(@PathVariable Integer id) {
	        Optional<Project> optionalProject = projectRepository.findById(id);
	        if (!optionalProject.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        projectRepository.delete(optionalProject.get());

	        return ResponseEntity.noContent().build();
	    }

	    @GetMapping("/getAll")
	    public ResponseEntity<Page<Project>> getAll(Pageable pageable) {
	        return ResponseEntity.ok(projectRepository.findAll(pageable));
	    }

	    @GetMapping("getById/{id}")
	    public ResponseEntity<Project> getById(@PathVariable Integer id) {
	        Optional<Project> optionalBook = projectRepository.findById(id);
	        if (!optionalBook.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        return ResponseEntity.ok(optionalBook.get());
	    }

}
