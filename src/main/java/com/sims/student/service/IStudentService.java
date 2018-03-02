package com.sims.student.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sims.student.entity.Student;

public interface IStudentService {
	public void saveOrUpdate(Student student);
	public void delete(Student student);
	public void delete(List<Long> ids);
	public Student findOne(Long id);
	public Student findByStudentNo(String studentNo);
	public List<Student> findAll();
	//��̬������ѯ
	public Page<Student> findAll(Specification<Student> spec, Pageable pageable);
}
