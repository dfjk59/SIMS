package com.sims.student.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sims.common.entity.BaseEntity;
import com.sims.common.util.Sex;

@Entity
@Table(name="t_student")

public class Student extends BaseEntity<Long>{

	private String studentNo;
	private String studentName;
	private Sex sex;

	private Date birthday;
	
	private String email;
	private String phone;
	private String address;

	private String className;
	@Column(unique=true,nullable=false)
	public String getStudentNo() {
		return studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public Sex getSex() {
		return sex;
	} 
	@DateTimeFormat(pattern="yyyy/MM/dd") 
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
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

	@Override
	public String toString() {
		return "Student [studentNo=" + studentNo + ", studentName=" + studentName + ", sex=" + sex + ", birthday="
				+ birthday + ", email=" + email + ", phone=" + phone + ", address=" + address + ", className="
				+ className + "]";
	}

}
