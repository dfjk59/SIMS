package com.sims.score.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sims.course.entity.Course;
import com.sims.score.dao.ScoreRepository;
import com.sims.score.entity.Score;
import com.sims.student.entity.Student;



@Service
@Transactional
public class ScoreService implements IScoreService
{
	@Autowired
	private ScoreRepository scoreRepository;		//自动注入持久层方法
	@Autowired
	private ScoreRepository studentRepository;
	public void saveOrUpdate(Score score) {
		if(studentRepository.findByNumber(score.getNumber())!=null) {
		scoreRepository.save(score);
		}
	}
	public void delete(Score score) {
		scoreRepository.delete(score);
	}

	@Transactional(readOnly = true)					//查询方法 事务只读		
	public Score findOne(Long id) {
		return scoreRepository.findOne(id);
	}
	@Transactional(readOnly = true)
	public List<Score> findAll() {
		return (List<Score>) scoreRepository.findAll();
	}
	@Transactional(readOnly = true)
	public Page<Score> findAll(Specification<Score> spec, Pageable pageable) {
		return scoreRepository.findAll(spec, pageable);
	}
	public void deleteByCNo(Course course) {
		String cNo = course.getcNo();
		List<Score> list = scoreRepository.findByCNo(cNo);
		for (Score score : list) {
			scoreRepository.delete(score);
		}		
	}
	
	public void deleteByNumber(Student student) {
		String number = student.getStudentNo();
		List<Score> list = scoreRepository.findByNumber(number);
		for (Score score : list) {
			scoreRepository.delete(score);
		}	
	}
}
