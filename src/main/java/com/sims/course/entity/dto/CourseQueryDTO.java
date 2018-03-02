package com.sims.course.entity.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sims.course.entity.Course;

public class CourseQueryDTO {
	private Long id;
	
	private String cNo;// 课程编号

	private String cName;// 课程名

	private Double credit;

	private String department;

	private String place;

	private String teacherNo;
	private String teacherName;

	public Long getId() {
		return id;
	}

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

	public void setId(Long id) {
		this.id = id;
	}

	public void setcNo(String cNo) {
		this.cNo = cNo;
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

	/**
	 * CriteriaBuilder cb
	 * Root<Course> root
	 * cb.like(root.get("coloum").as(Integer.class),Integer)//getas
	 * aslo gt lt (getas(Integer.class),getas(Integer.class))
	 * 		greaterThanOrEqualTo lessThanOrEqualTo(getas(Integer.class),Integer)
	 * 		equal(),between(),notEqual()
	 * 
	 * CriteriaBuilder cb.or(gt(),like())
	 * upper string:
	 * 		criteriaBuilder.upper(root.getas())
	 * 		StringUtils.upperCase(StringUtils.trim(${value}))
	 * 
	 * eg:
	 * 		 if(courseQueryDTO.!=null) {
					 predicate.add(cb.equal(root.get("").as(.class), courseQueryDTO.));
				 }
	 * @param courseQueryDTO
	 * @return
	 */
    @SuppressWarnings("unused")
	public static Specification<Course> getWhereClause(CourseQueryDTO courseQueryDTO)
    {
    	
		return new Specification<Course>() {
			public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 //1.声明Predicate集合
				 List<Predicate> predicate = new ArrayList<>();
				 //2.根据courseQueryDTO查询条件动态添加Predicate
				 if(courseQueryDTO.getcNo()!=null) {
					 predicate.add(cb.like(root.get("cNo").as(String.class), "%"+courseQueryDTO.getcNo()+"%"));
				 }
				 if(courseQueryDTO.getcName()!=null) {
					 predicate.add(cb.like(root.get("cName").as(String.class), "%"+courseQueryDTO.getcName()+"%"));
				 }
				 if(courseQueryDTO.getDepartment()!=null) {
					 predicate.add(cb.like(root.get("department").as(String.class), "%"+courseQueryDTO.getDepartment()+"%"));
				 }
				 if(courseQueryDTO.getPlace()!=null) {
					 predicate.add(cb.like(root.get("place").as(String.class), "%"+courseQueryDTO.getPlace()+"%"));
				 }
				 if(courseQueryDTO.getTeacherNo()!=null) {
					 predicate.add(cb.like(root.get("teacherNo").as(String.class), "%"+courseQueryDTO.getTeacherNo()+"%"));
				 }
				 if(courseQueryDTO.getTeacherName()!=null) {
					 predicate.add(cb.like(root.get("teacherName").as(String.class), "%"+courseQueryDTO.getTeacherName()+"%"));
				 }

				 //3.根据Predicate集合生成并返回and 连接的 where条件
	             return cb.and(predicate.toArray(new Predicate[predicate.size()]));
			
			}
		};
      
    }
}
