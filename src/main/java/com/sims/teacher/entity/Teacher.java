package com.sims.teacher.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sims.common.entity.BaseEntity;
import com.sims.common.util.Sex;

@Entity
@Table(name="t_teacher")
public class Teacher extends BaseEntity<Long> {
	
	private String teacherNo;
	private String teacherName;
	private Sex sex;

//	private int seniority;//工龄
//
//	private int age;//年龄

	private Date birthday;

	private Date entryDate;

	private String titles;//职称
	private String phone;
	
	@Column(unique=true,nullable=false)
	public String getTeacherNo() {
		return teacherNo;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public Sex getSex() {
		return sex;
	}
//	@Transient
//	public int getSeniority() {
//		if(null != this.entryDate) {
//			Calendar c = Calendar.getInstance();
//			Calendar d = Calendar.getInstance();
//			d.setTime(this.entryDate);
//			c.setTime(new Date());
//			return  c.get(Calendar.YEAR) - d.get(Calendar.YEAR);
//		}
//		else {
//			return 0;
//		}
//		
//	}
//	@Transient
//	public int getAge() {
//		if(null != this.birthday) {
//			Calendar c = Calendar.getInstance();
//			Calendar d = Calendar.getInstance();
//			d.setTime(this.birthday);
//			c.setTime(new Date());
//			return c.get(Calendar.YEAR) - d.get(Calendar.YEAR);
//		}
//		else {
//			return 0;
//		}
//	}
	
//	public int getSeniority() {
//		return seniority;
//	}
//	public int getAge() {
//		return age;
//	}
	
	@DateTimeFormat(pattern="yyyy/MM/dd") 
	@JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
	public Date getBirthday() {
		return birthday;
	}

	@DateTimeFormat(pattern="yyyy/MM/dd") 
	@JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
	public Date getEntryDate() {
		return entryDate;
	}
	public String getTitles() {
		return titles;
	}
	public String getPhone() {
		return phone;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}

//	public void setSeniority(int seniority) {
//		this.seniority = seniority;
//	}
//	public void setAge(int age) {
//		this.age = age;
//	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public void setTitles(String titles) {
		this.titles = titles;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Teacher [teacherNo=" + teacherNo + ", teacherName=" + teacherName + ", sex=" + sex
				+ ", birthday=" + birthday + ", entryDate=" + entryDate + ", titles=" + titles + ", phone=" + phone + "]";
	}

	
}
