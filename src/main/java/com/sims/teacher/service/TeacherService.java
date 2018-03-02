package com.sims.teacher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sims.course.dao.CourseRepository;
import com.sims.course.entity.Course;
import com.sims.teacher.dao.TeacherRepository;
import com.sims.teacher.entity.Teacher;

@Service
@Transactional
public class TeacherService implements ITeacherService {
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private CourseRepository courseRepository;

	public void saveOrUpdate(Teacher student) {
		teacherRepository.save(student);
	}

	public void delete(Teacher student) {
		List<Course> list = courseRepository.findByTeacherNo(student.getTeacherNo());
		for (Course course : list) {
			course.setTeacherNo(null);
			course.setTeacherName(null);
		}
		teacherRepository.delete(student);
	}

	public void delete(List<Long> ids) {
		if(null != ids) {
			List<Teacher> students =  (List<Teacher>) teacherRepository.findAll(ids);
			teacherRepository.delete(students);
		}
	}
	@Transactional(readOnly = true)
	public Teacher findOne(Long id) {
		return teacherRepository.findOne(id);
	}
	@Transactional(readOnly = true)
	public List<Teacher> findAll() {
		return (List<Teacher>) teacherRepository.findAll();
	}
	@Transactional(readOnly = true)
	public Page<Teacher> findAll(Specification<Teacher> spec, Pageable pageable) {
		return teacherRepository.findAll(spec, pageable);
	}

	@Override
	public Teacher findByTeacherNo(String teacherNo) {
		return teacherRepository.findByTeacherNo(teacherNo);
	}
}
