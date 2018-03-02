package com.sims.teacher.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sims.teacher.entity.Teacher;



public interface ITeacherService {
	public void saveOrUpdate(Teacher teacher);
	public void delete(Teacher teacher);
	public void delete(List<Long> ids);
	public Teacher findOne(Long id);
	public Teacher findByTeacherNo(String teacherNo);
	public List<Teacher> findAll();
	//��̬������ѯ
	public Page<Teacher> findAll(Specification<Teacher> spec, Pageable pageable);
}
