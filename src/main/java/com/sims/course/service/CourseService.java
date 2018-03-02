package com.sims.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sims.course.dao.CourseRepository;
import com.sims.course.entity.Course;



@Service
@Transactional
public class CourseService implements ICourseService
{
	@Autowired
	private CourseRepository courseRepository;

	public void saveOrUpdate(Course course) {
		courseRepository.save(course);
	}
	public void delete(Course course) {
		courseRepository.delete(course);
	}

	@Transactional(readOnly = true)
	public Course findOne(Long id) {
		return courseRepository.findOne(id);
	}
	@Transactional(readOnly = true)
	public List<Course> findAll() {
		return (List<Course>) courseRepository.findAll();
	}
	@Transactional(readOnly = true)
	public Page<Course> findAll(Pageable page) {
		return courseRepository.findAll(page);
	}
	@Transactional(readOnly = true)
	public Page<Course> findAll(Specification<Course> spec, Pageable pageable) {
		return courseRepository.findAll(spec, pageable);
	}
}
