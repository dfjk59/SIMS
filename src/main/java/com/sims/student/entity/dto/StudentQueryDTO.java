package com.sims.student.entity.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sims.common.util.Sex;
import com.sims.student.entity.Student;

public class StudentQueryDTO {

	private Long id;
	private String studentName;
	private Sex sex;
	@DateTimeFormat(pattern="yyyy/MM/dd") 
	private Date birthday;
	
	private String email;
	private String phone;
	private String address;
	private String className;


	public StudentQueryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudentQueryDTO(Long id,String studentName,Sex sex,Date birthday,String address,String className) {
		super();
		this.id = id;
		this.studentName = studentName;
		this.sex = sex;
		this.birthday = birthday;
		this.address = address;
		this.className = className;
		// TODO Auto-generated constructor stub
	}
	public String getStudentName() {
		return studentName;
	}

	public Sex getSex() {
		return sex;
	}
	@JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
	public Date getBirthday() {
		return birthday;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public Long getId() {
		return id;
	}

	public String getClassName() {
		return className;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static Specification<Student> getSpecification(StudentQueryDTO studentQueryDTO){
		Specification<Student> spec = new Specification<Student>() {

			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> list = new ArrayList<Predicate>(); 
				
				if(null != studentQueryDTO && !StringUtils.isEmpty(studentQueryDTO.getStudentName())) {
					 Predicate  p1 =  cb.like(root.get("studentName").as(String.class),"%"+ studentQueryDTO.getStudentName() + "%");
					 list.add(p1);
				 }
				 if(null != studentQueryDTO && !StringUtils.isEmpty(studentQueryDTO.getClassName())) {
					 Predicate  p2 =  cb.like(root.get("className").as(String.class),"%"+ studentQueryDTO.getClassName() + "%");
					 list.add(p2);
				 }
				 if(null != studentQueryDTO && !StringUtils.isEmpty(studentQueryDTO.getSex())) {
					 Predicate  p3 =  cb.equal(root.get("sex").as(Sex.class), studentQueryDTO.getSex() );
					 list.add(p3);
				 }
				 if(null != studentQueryDTO && !StringUtils.isEmpty(studentQueryDTO.getAddress())) {
					 Predicate  p4 =  cb.like(root.get("address").as(String.class),"%"+ studentQueryDTO.getAddress() + "%");
					 list.add(p4);
				 }
				 if(null != studentQueryDTO && !StringUtils.isEmpty(studentQueryDTO.getBirthday())) {
					 Predicate  p5 =  cb.greaterThanOrEqualTo(root.get("birthday").as(Date.class), studentQueryDTO.getBirthday());
					 list.add(p5);
				 }

				 if(null != studentQueryDTO && !StringUtils.isEmpty(studentQueryDTO.getPhone())) {
					 Predicate  p6 =  cb.like(root.get("phone").as(String.class),"%"+ studentQueryDTO.getPhone() + "%");
					 list.add(p6);
				 }
				 if(null != studentQueryDTO && !StringUtils.isEmpty(studentQueryDTO.getEmail())) {
					 Predicate  p7 =  cb.like(root.get("email").as(String.class),"%"+ studentQueryDTO.getEmail() + "%");
					 list.add(p7);
				 }
				 Predicate[] p = new Predicate[list.size()];  
				 
				 return cb.and(list.toArray(p));  
			}};
		return spec;
	}
}
