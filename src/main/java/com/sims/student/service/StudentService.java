package com.sims.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sims.student.dao.StudentRepository;
import com.sims.student.entity.Student;

@Service
@Transactional
public class StudentService implements IStudentService {
	
	@Autowired
	private StudentRepository studentRepository;


	public void saveOrUpdate(Student student) {
		studentRepository.save(student);
	}

	public void delete(Student student) {
		studentRepository.delete(student);
	}

	public void delete(List<Long> ids) {
		if(null != ids) {
			List<Student> students =  (List<Student>) studentRepository.findAll(ids);
			studentRepository.delete(students);
		}
	}
	@Transactional(readOnly = true)
	public Student findOne(Long id) {
		return studentRepository.findOne(id);
	}
	@Transactional(readOnly = true)
	public List<Student> findAll() {
		return (List<Student>) studentRepository.findAll();
	}
	@Transactional(readOnly = true)
	public Page<Student> findAll(Specification<Student> spec, Pageable pageable) {
		return studentRepository.findAll(spec, pageable);
	}

	@Transactional(readOnly = true)
	public Student findByStudentNo(String studentNo) {
		return studentRepository.findByStudentNo(studentNo);
	}
}
