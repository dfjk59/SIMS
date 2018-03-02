package com.sims.score.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sims.course.entity.Course;
import com.sims.score.entity.Score;
import com.sims.student.entity.Student;


public interface IScoreService 
{
	public void saveOrUpdate(Score score);
	public void delete(Score score);
	public Score findOne(Long id);
	public List<Score> findAll();
	//动态条件查询
	public Page<Score> findAll(Specification<Score> spec, Pageable pageable);
	
	public void deleteByCNo(Course course);
	public void deleteByNumber(Student student);
}
