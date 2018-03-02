package com.sims.course.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sims.course.entity.Course;


public interface ICourseService 
{
	public void saveOrUpdate(Course course);
	public void delete(Course course);
	public Course findOne(Long id);
	public List<Course> findAll();
	public Page<Course> findAll(Pageable pageable);
	//动态条件查询
	public Page<Course> findAll(Specification<Course> spec, Pageable pageable);
}
