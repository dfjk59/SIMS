package com.sims.course.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sims.course.entity.Course;

@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course,Long>,JpaSpecificationExecutor<Course>
{
	public List<Course> findByTeacherNo(String teacherNo);
}
