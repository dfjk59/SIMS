package com.sims.teacher.entity.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.sims.common.util.Sex;
import com.sims.teacher.entity.Teacher;

public class TeacherQueryDTO {
	
	private Long id;
	private String teacherNo;
	private String teacherName;
	private Sex sex;
	private Long seniority;//工龄
	private Long age;//年龄
//	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date birthday;
//	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date entryDate;

	private String titles;//职称
	private String phone;
	
	public Long getId() {
		return id;
	}

	public String getTeacherNo() {
		return teacherNo;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public Sex getSex() {
		return sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public String getTitles() {
		return titles;
	}

	public String getPhone() {
		return phone;
	}
	public Date getSenioritycDate() {
		 Calendar cDate = Calendar.getInstance();
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		 String newDateString = sdf.format(new Date());
		 try {
			cDate.setTime(sdf.parse(newDateString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int a=this.getSeniority().intValue();
		 cDate.add(Calendar.YEAR,-a);

		return cDate.getTime();
	}
	public Date getSenioritydDate() {
		 Calendar dDate = Calendar.getInstance();
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		 String newDateString = sdf.format(new Date());
		 try {
			dDate.setTime(sdf.parse(newDateString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int b=this.getSeniority().intValue()+1;
		 dDate.add(Calendar.YEAR,-b);
		 
		return dDate.getTime();
	}
	public Long getSeniority() {
		return seniority;
	}

	public Long getAge() {
		return age;
	}
	public Date getAgecDate() {
		 Calendar cDate = Calendar.getInstance();
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		 String newDateString = sdf.format(new Date());
		 try {
			cDate.setTime(sdf.parse(newDateString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int a=this.getSeniority().intValue();
		 cDate.add(Calendar.YEAR,-a);

		return cDate.getTime();
	}
	public Date getAgedDate() {
		 Calendar dDate = Calendar.getInstance();
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		 String newDateString = sdf.format(new Date());
		 try {
			dDate.setTime(sdf.parse(newDateString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int b=this.getSeniority().intValue()+1;
		 dDate.add(Calendar.YEAR,-b);
		 
		return dDate.getTime();
	}
	public void setId(Long id) {
		this.id = id;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSeniority(Long seniority) {
		this.seniority = seniority;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public static Specification<Teacher> getSpecification(TeacherQueryDTO teacherQueryDTO){
		Specification<Teacher> spec = new Specification<Teacher>() {

			public Predicate toPredicate(Root<Teacher> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> list = new ArrayList<Predicate>(); 
				
				if(null != teacherQueryDTO && !StringUtils.isEmpty(teacherQueryDTO.getTeacherName())) {
					 Predicate  p1 =  cb.like(root.get("teacherName").as(String.class),"%"+ teacherQueryDTO.getTeacherName() + "%");
					 list.add(p1);
				 }
				if(null != teacherQueryDTO && !StringUtils.isEmpty(teacherQueryDTO.getTeacherNo())) {
					 Predicate  p1 =  cb.like(root.get("teacherNo").as(String.class),"%"+ teacherQueryDTO.getTeacherNo() + "%");
					 list.add(p1);
				 }
				 if(null != teacherQueryDTO && !StringUtils.isEmpty(teacherQueryDTO.getTitles())) {
					 Predicate  p2 =  cb.like(root.get("titles").as(String.class),"%"+ teacherQueryDTO.getTitles() + "%");
					 list.add(p2);
				 }
				 if(null != teacherQueryDTO && !StringUtils.isEmpty(teacherQueryDTO.getSex())) {
					 Predicate  p3 =  cb.like(root.get("sex").as(String.class),"%"+ teacherQueryDTO.getSex() + "%");
					 list.add(p3);
				 }
				 if(null != teacherQueryDTO && !StringUtils.isEmpty(teacherQueryDTO.getSeniority())) {
					 
					 Predicate  p4 =  cb.between(root.get("entryDate").as(Date.class), teacherQueryDTO.getSenioritydDate(),teacherQueryDTO.getSenioritycDate());
					 list.add(p4);
				 }
				 if(null != teacherQueryDTO && !StringUtils.isEmpty(teacherQueryDTO.getAge())) {
					 
					 Predicate  p5 =  cb.between(root.get("birthday").as(Date.class), teacherQueryDTO.getAgedDate(),teacherQueryDTO.getAgecDate());
					 list.add(p5);
				 }
				 
//				 if(null != teacherQueryDTO && !StringUtils.isEmpty(teacherQueryDTO.getSeniority())) {
//
//					 Predicate  p4 =  cb.equal(root.get("entryDate").as(Integer.class), teacherQueryDTO.getEntryDate());
//					 list.add(p4);
//				 }
//				 if(null != teacherQueryDTO && !StringUtils.isEmpty(teacherQueryDTO.getAge())) {
//					 
//					 Predicate  p5 =  cb.equal(root.get("getAge()").as(Integer.class),"%"+ teacherQueryDTO.getAge() + "%");
//					 list.add(p5);
//				 }
				 if(null != teacherQueryDTO && !StringUtils.isEmpty(teacherQueryDTO.getPhone())) {
					 Predicate  p6 =  cb.like(root.get("phone").as(String.class),"%"+ teacherQueryDTO.getPhone() + "%");
					 list.add(p6);
				 }
				 Predicate[] p = new Predicate[list.size()];  
				 
				 return cb.and(list.toArray(p));  
			}};
		return spec;
	}

}
