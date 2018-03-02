package com.sims.course.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="t_course")
public class Course {
	private Long id;
	
	private String cNo;//课程编号
	
	private String cName;//课程名
	
	private Double credit;
	
	private String department;
	
	private String place;
	
	private String teacherNo;
	
	private String teacherName;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
    @Column(unique=true,nullable=false)
	public String getcNo() {
		return cNo;
	}

	public String getcName() {
		return cName;
	}

	public Double getCredit() {
		return credit;
	}

	public String getDepartment() {
		return department;
	}

	public String getPlace() {
		return place;
	}


	public String getTeacherNo() {
		return teacherNo;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setcNo(String cNo) {
		this.cNo = cNo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}


}
