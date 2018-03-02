package com.sims;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sims.course.entity.Course;
import com.sims.course.service.ICourseService;
import com.sims.score.entity.Score;
import com.sims.score.service.IScoreService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-core.xml", "classpath:applicationContext-jpa.xml" })
public class CourseInit {

	@Autowired
	private ICourseService courseService;
	@Autowired
	private IScoreService scoreService;
	
	@Test
	@Rollback(value = false)
	public void courseInit() {

		for (int i = 1; i < 102; i++) {

			Course course = new Course();
			String str = String.format("%03d", i);
			course.setcName("课程" + str);
			course.setcNo("C00" + str);
			course.setDepartment("部门" + str);
			course.setCredit(3.5);
			course.setPlace("6f" + str);
			course.setTeacherNo("T00" + str);
			course.setTeacherName("教师" + str);

			courseService.saveOrUpdate(course);
		}
	}
	@Test
		@Rollback(value = false)
		public void scoreInit() {

			for (int i = 1; i < 10; i++) {
				for (int j = 1; j < 10; j++) {
					Score score	= new Score();
					String str2 = String.format("%03d", j);
					String str3 = String.format("%03d", i*10+j);
					score.setName("学生"+str3);
					score.setNumber("S00"+str3);
					score.setcNo("C00"+str2);
					score.setcName("课程"+str2);
					score.setScore(Double.parseDouble(str3));
					score.setRecordDate(new Date());
					scoreService.saveOrUpdate(score);
				}
			}
		}
	@Test
	public void findAll() {
		List<Course> lists = courseService.findAll();

		for (Course course : lists) {
			System.out.println(course.getcName());
		}
	}
}
