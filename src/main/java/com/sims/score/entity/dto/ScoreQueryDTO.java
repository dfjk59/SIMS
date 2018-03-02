package com.sims.score.entity.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sims.score.entity.Score;

public class ScoreQueryDTO {
	private Long id;

	private Double score;// 分数

	private String cNo;// 课程号

	private String cName;// 课程名

	private String number;// 学号

	private String name;// 姓名
	
	@JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String recordDate;

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

	public String getRecordDate() {
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

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	/**
	 * CriteriaBuilder cb Root<Course> root
	 * cb.like(root.get("coloum").as(Integer.class),Integer)//getas aslo gt lt
	 * (getas(Integer.class),getas(Integer.class)) greaterThanOrEqualTo
	 * lessThanOrEqualTo(getas(Integer.class),Integer) equal(),between(),notEqual()
	 * 
	 * CriteriaBuilder cb.or(gt(),like()) upper string:
	 * criteriaBuilder.upper(root.getas())
	 * StringUtils.upperCase(StringUtils.trim(${value}))
	 * 
	 * eg: if(courseQueryDTO.!=null) {
	 * predicate.add(cb.equal(root.get("").as(.class), courseQueryDTO.)); }
	 * 
	 * @param courseQueryDTO
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Specification<Score> getWhereClause(ScoreQueryDTO courseQueryDTO) {
		return new Specification<Score>() {
			public Predicate toPredicate(Root<Score> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 1.声明Predicate集合
				List<Predicate> predicate = new ArrayList<>();
				// 2.根据courseQueryDTO查询条件动态添加Predicate
				if (courseQueryDTO.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class), courseQueryDTO.getId()));
				}
				if (courseQueryDTO.getcNo() != null) {
					predicate.add(cb.like(root.get("cNo").as(String.class), "%" + courseQueryDTO.getcNo() + "%"));
				}
				if (courseQueryDTO.getcName() != null) {
					predicate.add(cb.like(root.get("cName").as(String.class), "%" + courseQueryDTO.getcName() + "%"));
				}
				if (courseQueryDTO.getNumber() != null) {
					predicate.add(cb.like(root.get("number").as(String.class), "%" + courseQueryDTO.getNumber() + "%"));
				}
				if (courseQueryDTO.getName() != null) {
					predicate.add(cb.like(root.get("name").as(String.class), "%" + courseQueryDTO.getName() + "%"));
				}
				if (courseQueryDTO.getScore() != null) {
					predicate.add(cb.equal(root.get("score").as(Double.class), courseQueryDTO.getScore()));
				}
//				if (courseQueryDTO.getRecordDate() != null) {
//					System.out.println(courseQueryDTO.getRecordDate());
//					predicate.add(cb.equal(root.get("recordDate").as(String.class), courseQueryDTO.getRecordDate()));
//				}
//				if (courseQueryDTO.getRecordDate() == null) {
//					predicate.add(cb.lessThan(root.get("firstday").as(Date.class), courseQueryDTO.getRecordDate()));
//				}
//				if (courseQueryDTO.getRecordDate() == null) {
//					predicate.add(cb.greaterThan(root.get("lastday").as(Date.class), courseQueryDTO.getRecordDate()));
//				}

				// 3.根据Predicate集合生成并返回and 连接的 where条件
				return cb.and(predicate.toArray(new Predicate[predicate.size()]));

			}
		};

	}
}
