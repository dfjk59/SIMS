package com.sims.score.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table( name = "t_score" )
public class Score{
	
	private Long id;
	
	private Double score;//分数
	
	private String cNo;//课程号
	
	private String cName;//课程名
	
	private String number;//学号
	
	private String name;//姓名
	
	private Date recordDate;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public Double getScore() {
		return score;
	}
	public String getcNo() {
		return cNo;
	}
	public String getcName() {
		return cName;
	}
	public String getNumber() {
		return number;
	}
	public String getName() {
		return name;
	}
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
	public Date getRecordDate() {
		return recordDate;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public void setcNo(String cNo) {
		this.cNo = cNo;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
}
