package com.sims.teacher.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sims.teacher.entity.Teacher;


@Repository
public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {

	public Teacher findByTeacherNo(String teacherNo);
}
