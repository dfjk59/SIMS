package com.sims.student.web;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sims.common.util.ExportExcelUtil;
import com.sims.common.util.ExtAjaxResponse;
import com.sims.common.util.ExtJsonResult;
import com.sims.common.util.ExtPageable;
import com.sims.common.util.ImportExcelUtil;
import com.sims.common.util.Sex;
import com.sims.common.util.SystemLog;
import com.sims.score.service.IScoreService;
import com.sims.student.entity.Student;
import com.sims.student.entity.dto.StudentQueryDTO;
import com.sims.student.service.IStudentService;
import com.sims.system.entity.Role;
import com.sims.system.entity.User;
import com.sims.system.service.IRoleService;
import com.sims.system.service.IUserService;


@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private IStudentService studentService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IScoreService scoreService;
	/**
	  * 导出数据库
	 */
	@RequestMapping("/downloadFile")
	@SystemLog(module = "学生模块", action = "导出学生信息")
	public @ResponseBody ExtAjaxResponse teacherExportExcel(HttpServletResponse response) 
	{
		ExportExcelUtil<Student> ex = new ExportExcelUtil<Student>();
		String title = "学生信息";
	    String[] headers = { "学生账号", "学生姓名", "性别", "出生日期","邮箱","电话","籍贯","班级"};
	    List<Student> dataset = new ArrayList<Student>();
	    dataset = studentService.findAll();
	    try {
	        //BufferedInputStream bis = new BufferedInputStream(
	           //      new FileInputStream("book.jpg"));
	          OutputStream out = new FileOutputStream("D://学生信息表.xls");

	          ex.exportExcel(title,headers, dataset, out,"yyyy/MM/dd");
	          out.close();
	          System.out.println("excel导出成功！");
	          return new ExtAjaxResponse(true,"导出成功！");
	       } catch (FileNotFoundException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	          return new ExtAjaxResponse(false,"导出失败！");
	       } catch (IOException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	          return new ExtAjaxResponse(false,"导出失败！");
	       }
	}
	/**
	  * 后台上传文件处理Action
	 */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @SystemLog(module = "学生模块", action = "导入学生信息")
    public @ResponseBody ExtAjaxResponse deploy(@RequestParam(value = "file", required = true) MultipartFile file) {
        // 获取上传的文件名
        String fileName = file.getOriginalFilename();
        System.out.println("fileName:"+fileName);
//      DecimalFormat format = new DecimalFormat("#"); 
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy/MM/dd"); 
        Workbook workbook = null;
        try {
            // 得到输入流（字节流）对象
            InputStream fileInputStream = file.getInputStream();
            System.out.println(fileInputStream);
            // 文件的扩展名
            String extension = FilenameUtils.getExtension(fileName);
            try {
                if ("xls".equals(extension)) {
                    //未测试 excel 2003
                    workbook = new HSSFWorkbook(fileInputStream);
                } else {
                    workbook = WorkbookFactory.create(fileInputStream);
                }
//                System.out.println("创建workbook对象");
                for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
	                Sheet sheet = workbook.getSheetAt(sheetNum);
	                if (sheet == null) {
	                	continue;
	                }
	                Row row = null;
//	                System.out.println("遍历表");
	                //获取Excel的总行数:Sheet.getLastRowNum()				
	                for(/*int i=0*/ int i=1/*sheet.getFirstRowNum()*/; i<=sheet.getLastRowNum(); i++){  
	                    //获取数据表里面的某一行  
	                    row = sheet.getRow(i);  
	                    if(row.getLastCellNum()!=8) {
	                    	
	                    	System.out.println("列数不对 ！请检查Excel格式");
	                    	break;
	                    }
	                    if(null == row||row == null) {
	                    	continue;
	                    }
//	                    System.out.println("遍历行");
	                    Cell studentNo = row.getCell(0);
	                    Cell studentName = row.getCell(1);
	                    Cell sex = row.getCell(2);
	                    Cell birthday = row.getCell(3);
	                    Cell email = row.getCell(4);
	                    Cell phone = row.getCell(5);
	                    Cell address = row.getCell(6);
	                    Cell className = row.getCell(7);
	                    Student student = new Student();
	                    student.setStudentNo((String) ImportExcelUtil.getCellValue(studentNo));
	                    student.setStudentName((String)ImportExcelUtil.getCellValue(studentName));
	                    if("男".equals(ImportExcelUtil.getCellValue(sex))) {
	                    	student.setSex(Sex.MALE);
	                    }else {
	                    	student.setSex(Sex.FEMALE);
	                    }
	                    String sbirthday = String.valueOf(ImportExcelUtil.getCellValue(birthday));
	                    student.setBirthday(sdf.parse(sbirthday));
	                    student.setEmail((String) ImportExcelUtil.getCellValue(email));
	                    student.setPhone((String) ImportExcelUtil.getCellValue(phone));
	                    student.setAddress((String) ImportExcelUtil.getCellValue(address));
	                    student.setClassName((String) ImportExcelUtil.getCellValue(className));
	                    
	                    User user = new User();
	                    user.setUserName(student.getStudentNo());
	                    
	                    Role role = roleService.findByRoleName("student");
	                    
	                    userService.saveOrUpdate(user, role.getId());

	                    studentService.saveOrUpdate(student);
	                }  
               }
           } finally {
               try {
               	fileInputStream.close();
               } catch (Exception e) {
                   //no op
               }
           }
            return new ExtAjaxResponse(true,"保存成功!");
        } catch (NullPointerException e) {
            System.out.println(e);
            return new ExtAjaxResponse(false,"Excel中存在空表或空行 ");
        }catch (Exception e) {
            System.out.println(e);
            if((e.toString()).indexOf("org.springframework.orm.jpa.JpaSystemException")==-1){
            	return new ExtAjaxResponse(false,e.toString());
            }else {
            	return new ExtAjaxResponse(false,"\r\n Excel与数据库中存在重复数据 ，\n请检查数据是否满足要求\r\n");
            }
        }
    }
    
	@RequestMapping("/saveOrUpdate")
	@RequiresPermissions("student:add")
	@SystemLog(module = "学生模块", action = "保存学生信息")
	public @ResponseBody ExtAjaxResponse saveOrUpdate(Student student) {
		try {
			User user = new User();
            user.setUserName(student.getStudentNo());
            Role role = roleService.findByRoleName("student");
            userService.saveOrUpdate(user, role.getId());
			studentService.saveOrUpdate(student);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}

	}

	@RequestMapping("/delete")
	@RequiresPermissions("student:delete")
	@SystemLog(module = "学生模块", action = "删除学生信息")
	public @ResponseBody ExtAjaxResponse delete(@RequestParam Long id) {
		try {
			Student student = studentService.findOne(id);
			
			if (null != student) {
				scoreService.deleteByNumber(student);
				studentService.delete(student);
			}
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping("/deleteStudents")
	@RequiresPermissions("student:delete")
	@SystemLog(module = "学生模块", action = "删除学生信息")
	public @ResponseBody ExtAjaxResponse deleteUsers(@RequestParam Long[] ids) {
		try {
			if (null != ids) {
				List<Long> idLists = Arrays.asList(ids);
				for (Long long1 : idLists) {
					Student student = studentService.findOne(long1);
					scoreService.deleteByNumber(student);
					studentService.delete(student);
				}
			}
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping("/findSingle")
	//@RequiresPermissions("student:find")
	public @ResponseBody ExtJsonResult<Student> findSingle(@RequestParam String studentNo) {
		Student student = studentService.findByStudentNo(studentNo);
		List<Student> list =new ArrayList<Student>();
		list.add(student);
		return new ExtJsonResult<Student>(list);
	}
	
	@RequestMapping("/findOne")
	//@RequiresPermissions("student:find")
	public @ResponseBody Student findOne(@RequestParam Long id) {
		Student student = studentService.findOne(id);

		return student;
	}

	@RequestMapping("/findAll")
	//@RequiresPermissions("student:find")
	public @ResponseBody List<Student> findAll() {
		List<Student> lists = studentService.findAll();

		return lists;
	}

	@RequestMapping("/findPage")
	//@RequiresPermissions("student:find")
	public @ResponseBody Page<Student> findPage(StudentQueryDTO studentQueryDTO, ExtPageable pageable) {
		Page<Student> page = studentService.findAll(StudentQueryDTO.getSpecification(studentQueryDTO),
				pageable.getPageable());

		return page;
	}

}
