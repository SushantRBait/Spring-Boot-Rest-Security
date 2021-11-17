package com.sushant.main.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data

public class Student {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
    private @Getter @Setter Integer studentId;
	@Column
	private @Getter @Setter String studentFName;
	@Column
	private @Getter @Setter String studentLName;
	@Column
	private @Getter @Setter Long mobileNo;
	@Column
	private @Getter @Setter String email;
    
    @OneToMany(mappedBy = "student", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
     private Set<Project> projects = new HashSet<>();

   

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentFName() {
		return studentFName;
	}

	public void setStudentFName(String studentFName) {
		this.studentFName = studentFName;
	}

	public String getStudentLName() {
		return studentLName;
	}

	public void setStudentLName(String studentLName) {
		this.studentLName = studentLName;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
		for(Project p : projects) {
            p.setStudent(this);
        }
	}


	
	
	
}
