package com.sims.course.web;

import java.io.IOException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sims.common.util.ExtAjaxResponse;
import com.sims.common.util.ExtPageable;
import com.sims.common.util.SystemLog;
import com.sims.course.entity.Course;
import com.sims.course.entity.dto.CourseQueryDTO;
import com.sims.course.service.ICourseService;
import com.sims.score.service.IScoreService;

@Controller
@RequestMapping("/course")
public class CourseContreller 
{
	@Autowired
	private ICourseService courseService;
	@Autowired
	private IScoreService scoreService;
	    
	@RequestMapping("/saveOrUpdate")
	@RequiresPermissions("course:add")
	@SystemLog(module = "课程模块", action = "保存课程")
	public @ResponseBody ExtAjaxResponse saveOrUpdate(Course course) 
	{
		try {
			courseService.saveOrUpdate(course);
			return new ExtAjaxResponse(true,"修改成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"修改失败！");
		}
		
	}
	@RequestMapping("/delete")
	@RequiresPermissions("course:delete")
	@SystemLog(module = "课程模块", action = "删除课程")
	public @ResponseBody ExtAjaxResponse delete(@RequestParam Long id) 
	{
		try {
			Course course =  courseService.findOne(id);
			if(null!=course) {
				scoreService.deleteByCNo(course);
				courseService.delete(course);
				
			}			
			return new ExtAjaxResponse(true,"修改成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ExtAjaxResponse(false,"修改失败！");
		}
	
	}
	//,consumes = "application/json"
	@RequestMapping(value = "/deleteCourses")
	@RequiresPermissions("course:delete")
	@SystemLog(module = "课程模块", action = "删除课程")
	public @ResponseBody ExtAjaxResponse deleteCourses(@RequestParam Long[] ids) 
	{
		try {
			for (Long id : ids) {
				Course course =  courseService.findOne(id);
				if(null!=course) {
					scoreService.deleteByCNo(course);
					courseService.delete(course);
				}						
			}
			return new ExtAjaxResponse(true,"修改成功！");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ExtAjaxResponse(false,"修改失败！");
		}
	
	}
	
	@RequestMapping("/findOne")
	//@RequiresPermissions("course:find")
	@SystemLog(module = "课程模块", action = "查询课程")
	public @ResponseBody ExtAjaxResponse findOne(@RequestParam Long id) 
	{
		try {
			Course course =  courseService.findOne(id);
			
			return new ExtAjaxResponse(true,"修改成功！");
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtAjaxResponse(false,"修改失败！");
		}
		
	}
	
	
	@RequestMapping("/findAll")
	//@RequiresPermissions("course:find")
	@SystemLog(module = "课程模块", action = "查询课程")
	public @ResponseBody Page<Course> findAll(CourseQueryDTO  courseQueryDTO ,ExtPageable pageable)
			throws JsonParseException, JsonMappingException, IOException{
				Page<Course> page =courseService.findAll(CourseQueryDTO.getWhereClause(courseQueryDTO),pageable.getPageable());
				return page;
			}
	
	@RequestMapping("/findSingleAll")
	//@RequiresPermissions("course:find")
	public @ResponseBody Page<Course> findSingleAll(@RequestParam String teacher,CourseQueryDTO  courseQueryDTO ,ExtPageable pageable)
			throws JsonParseException, JsonMappingException, IOException{
				Page<Course> page =courseService.findAll(CourseQueryDTO.getWhereClause(courseQueryDTO),pageable.getPageable());
				return page;
			}
//	@RequestMapping("/findPage")
//	public @ResponseBody Page<CourseQueryDTO> findAll(ExtPageable pageable)
//	{
//		
//		return courseService.findPage(pageable.getPageable());
//	}

}
