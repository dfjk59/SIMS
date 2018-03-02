package com.sims.teacher.web;

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
import com.sims.system.entity.Role;
import com.sims.system.entity.User;
import com.sims.system.service.IRoleService;
import com.sims.system.service.IUserService;
import com.sims.teacher.entity.Teacher;
import com.sims.teacher.entity.dto.TeacherQueryDTO;
import com.sims.teacher.service.ITeacherService;


@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private ITeacherService teacherService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	/**
	  * 导出数据库
	 */
	@RequestMapping("/downloadFile")
	@SystemLog(module = "教师模块", action = "导出教师信息")
	public @ResponseBody ExtAjaxResponse teacherExportExcel(HttpServletResponse response) 
	{
		ExportExcelUtil<Teacher> ex = new ExportExcelUtil<Teacher>();
		String title = "教师信息";
	    String[] headers = { "教师账号", "教师姓名", "性别", "出生日期","入职日期","职称","电话"};
	    List<Teacher> dataset = new ArrayList<Teacher>();
	    dataset = teacherService.findAll();
	    try {
	        //BufferedInputStream bis = new BufferedInputStream(
	           //      new FileInputStream("book.jpg"));
	          OutputStream out = new FileOutputStream("D://教师信息表.xls");

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
    @SystemLog(module = "教师模块", action = "导入教师信息")
    public @ResponseBody ExtAjaxResponse deploy(@RequestParam(value = "file", required = true) MultipartFile file) {
        // 获取上传的文件名
        String fileName = file.getOriginalFilename();
        System.out.println("fileName:"+fileName);
//      DecimalFormat format = new DecimalFormat("#");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
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
	                    if(row.getLastCellNum()!=7) {
	                    	
	                    	System.out.println("列数不对 ！请检查Excel格式");
	                    	break;
	                    }
	                    if(null == (row)||row == null) {
	                    	continue;
	                    }
//	                    System.out.println("遍历行");
	                    Cell teacherNo = row.getCell(0);
						Cell teacherName = row.getCell(1);
	                    Cell sex = row.getCell(2);
	                    Cell birthday = row.getCell(3);
	                    Cell entryDate = row.getCell(4);
	                    Cell titles = row.getCell(5);
	                    Cell phone = row.getCell(6);
	                    Teacher teacher = new Teacher();

						teacher.setTeacherNo((String) ImportExcelUtil.getCellValue(teacherNo));
	                    teacher.setTeacherName((String)ImportExcelUtil.getCellValue(teacherName));
	                    if("男".equals(ImportExcelUtil.getCellValue(sex))) {
	                    	teacher.setSex(Sex.MALE);
	                    }else {
	                    	teacher.setSex(Sex.FEMALE);
	                    }
	                    String sbirthday = String.valueOf(ImportExcelUtil.getCellValue(birthday));
	                    teacher.setBirthday(sdf.parse(sbirthday));
	                    teacher.setTitles((String) ImportExcelUtil.getCellValue(titles));
	                    teacher.setPhone((String) ImportExcelUtil.getCellValue(phone));
	                    String sentryDate = String.valueOf(ImportExcelUtil.getCellValue(entryDate));
	                    teacher.setEntryDate(sdf.parse(sentryDate));
	                    
	                    User user = new User();
	                    user.setUserName(teacher.getTeacherNo());
	                    
	                    Role role = roleService.findByRoleName("teacher");
	                    
	                    userService.saveOrUpdate(user, role.getId());

	                    teacherService.saveOrUpdate(teacher);

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
	@RequiresPermissions("teacher:add")
	@SystemLog(module = "教师模块", action = "保存教师信息")
	public @ResponseBody ExtAjaxResponse saveOrUpdate(Teacher teacher) {
		try {
			User user = new User();
            user.setUserName(teacher.getTeacherNo());
            
            Role role = roleService.findByRoleName("teacher");
            
            userService.saveOrUpdate(user, role.getId());

			teacherService.saveOrUpdate(teacher);
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}

	}

	@RequestMapping("/delete")
	@RequiresPermissions("teacher:delete")
	@SystemLog(module = "教师模块", action = "删除教师信息")
	public @ResponseBody ExtAjaxResponse delete(@RequestParam Long id) {
		try {
			Teacher teacher = teacherService.findOne(id);
			if (null != teacher) {
				teacherService.delete(teacher);
			}
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping("/deleteTeachers")
	@RequiresPermissions("teacher:delete")
	@SystemLog(module = "教师模块", action = "删除教师信息")
	public @ResponseBody ExtAjaxResponse deleteUsers(@RequestParam Long[] ids) {
		try {
			if (null != ids) {
				List<Long> idLists = Arrays.asList(ids);
				teacherService.delete(idLists);
			}
			return new ExtAjaxResponse(true, "操作成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false, "操作失败！");
		}
	}

	@RequestMapping("/findOne")
	@RequiresPermissions("teacher:find")
	@SystemLog(module = "教师模块", action = "查询教师信息")
	public @ResponseBody Teacher findOne(@RequestParam Long id) {
		Teacher teacher = teacherService.findOne(id);

		return teacher;
	}
	
	@RequestMapping("/findSingle")
	@RequiresPermissions("teacher:find")
	@SystemLog(module = "教师模块", action = "查询教师信息")
	public @ResponseBody ExtJsonResult<Teacher> findSingle(@RequestParam String teacherNo) {
		Teacher teacher = teacherService.findByTeacherNo(teacherNo);
		List<Teacher> list =new ArrayList<Teacher>();
		list.add(teacher);
		return new ExtJsonResult<Teacher>(list);
	}
	
	@RequestMapping("/findAll")
	@RequiresPermissions("teacher:find")
	@SystemLog(module = "教师模块", action = "查询教师信息")
	public @ResponseBody List<Teacher> findAll() {
		List<Teacher> lists = teacherService.findAll();

		return lists;
	}

	@RequestMapping("/findPage")
	@RequiresPermissions("teacher:find")
	@SystemLog(module = "教师模块", action = "查询教师信息")
	public @ResponseBody Page<Teacher> findPage(TeacherQueryDTO teacherQueryDTO, ExtPageable pageable) {
		Page<Teacher> page = teacherService.findAll(TeacherQueryDTO.getSpecification(teacherQueryDTO),
				pageable.getPageable());

		return page;
	}
}
