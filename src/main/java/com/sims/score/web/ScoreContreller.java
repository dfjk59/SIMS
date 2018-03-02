package com.sims.score.web;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sims.common.util.ExtAjaxResponse;
import com.sims.common.util.ExtPageable;
import com.sims.common.util.ScoreExportExcelUtil;
import com.sims.common.util.SystemLog;
import com.sims.score.entity.Score;
import com.sims.score.entity.dto.ScoreQueryDTO;
import com.sims.score.service.IScoreService;

@Controller
@RequestMapping("/score")
public class ScoreContreller 
{
	@Autowired
	private IScoreService scoreService;
	
	    
	@RequestMapping("/saveOrUpdate")
	@RequiresPermissions("score:add")
	@SystemLog(module = "成绩模块", action = "保存学生成绩")
	public @ResponseBody ExtAjaxResponse saveOrUpdate(Score score) 
	{
		try {
			score.setRecordDate(new Date());
			scoreService.saveOrUpdate(score);
			return new ExtAjaxResponse(true,"修改成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"修改失败！");
		}
		
	}
	@RequestMapping("/delete")
	@RequiresPermissions("score:delete")
	@SystemLog(module = "成绩模块", action = "删除学生成绩")
	public @ResponseBody ExtAjaxResponse delete(@RequestParam Long id) 
	{
		try {
			Score score =  scoreService.findOne(id);
			if(null!=score) {
				scoreService.delete(score);
			}			
			return new ExtAjaxResponse(true,"修改成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ExtAjaxResponse(false,"修改失败！");
		}
	
	}
	//,consumes = "application/json"
	@RequestMapping(value = "/deleteScores") 
	@RequiresPermissions("score:delete")
	@SystemLog(module = "成绩模块", action = "删除学生成绩")
	public @ResponseBody ExtAjaxResponse deleteScores(@RequestParam Long[] ids) 
	{
		try {
			for (Long id : ids) {
				Score score =  scoreService.findOne(id);
				if(null!=score) {
					scoreService.delete(score);
				}						
			}
			return new ExtAjaxResponse(true,"修改成功！");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ExtAjaxResponse(false,"修改失败！");
		}
	
	}
	
	@RequestMapping("/findOne")
	//@RequiresPermissions("score:find")
	@SystemLog(module = "成绩模块", action = "查询成绩")
	public @ResponseBody ExtAjaxResponse findOne(@RequestParam Long id) 
	{
		try {
			scoreService.findOne(id);
			
			return new ExtAjaxResponse(true,"修改成功！");
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtAjaxResponse(false,"修改失败！");
		}
		
	}
	
	
	@RequestMapping("/findAll")
	//@RequiresPermissions("score:find")
	@SystemLog(module = "成绩模块", action = "查询成绩")
	public @ResponseBody Page<Score> findAll(ScoreQueryDTO  scoreQueryDTO ,ExtPageable pageable)
			throws JsonParseException, JsonMappingException, IOException{
				Page<Score> page =scoreService.findAll(ScoreQueryDTO.getWhereClause(scoreQueryDTO),pageable.getPageable());
				return page;
			}
	
	@RequestMapping("/findSingleAll")
	//@RequiresPermissions("score:find")
	public @ResponseBody Page<Score> findSingleAll(@RequestParam String number,ScoreQueryDTO  scoreQueryDTO ,ExtPageable pageable)
			throws JsonParseException, JsonMappingException, IOException{
				Page<Score> page =scoreService.findAll(ScoreQueryDTO.getWhereClause(scoreQueryDTO),pageable.getPageable());
				return page;
			}
	 	
	@RequestMapping("/scoreExportExcel")
	//@RequiresPermissions("score:find")
	public @ResponseBody ExtAjaxResponse logExportExcel(HttpServletResponse response) 
	{
		List<Score> logs = scoreService.findAll();
		if(ScoreExportExcelUtil.exportExcel(logs,response)){
			return new ExtAjaxResponse(false,"导出成功！");
		}
		return new ExtAjaxResponse(false,"导出失败！");
	}
	
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @SystemLog(module = "成绩模块", action = "上传成绩")
    public @ResponseBody ExtAjaxResponse deploy(@RequestParam(value = "file", required = true) MultipartFile file) {
        // 获取上传的文件名
        String fileName = file.getOriginalFilename();
        System.out.println("fileName:"+fileName);
        DecimalFormat format = new DecimalFormat("#"); 
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
	                for(/*int i=0*/ int i=2/*sheet.getFirstRowNum()*/; i<=sheet.getLastRowNum(); i++){  
	                    //获取数据表里面的某一行  
	                    row = sheet.getRow(i);  
	                    if(row.getLastCellNum()!=6) {
	                    	
	                    	System.out.println("列数不对 ！请检查Excel格式");
	                    	break;
	                    }
//	                    System.out.println("遍历行");
	                    Cell score = row.getCell(0);
						Cell number = row.getCell(1);
	                    Cell name = row.getCell(2);
	                    Cell cNo  = row.getCell(3);
	                    Cell cName = row.getCell(4);
	                    Cell recordDate = row.getCell(5);
	                    
	                    Score score1 = new Score();
//	                  if("男".equals(sex.getStringCellValue())) {
//                    	score.setSex(Sex.MALE);
//                    }else {
//                    	score.setSex(Sex.FEMALE);
//                    }
//	                  String p =format.format(phone.getNumericCellValue());
	                    score1.setScore(score.getNumericCellValue());
	                    System.out.println("score");
	                    score1.setNumber(number.getStringCellValue());
	                    System.out.println("number");
	                    score1.setName(name.getStringCellValue());
	                    System.out.println("name");
	                    score1.setcNo(cNo.getStringCellValue());
	                    System.out.println("cNo");
	                    score1.setcName(cName.getStringCellValue());
	                    System.out.println("cName");
	                    score1.setRecordDate(recordDate.getDateCellValue());
	                    System.out.println("recordDate");
	                    
	                    scoreService.saveOrUpdate(score1);

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
        } catch (Exception e) {
            System.out.println(e);
            return new ExtAjaxResponse(false,"上传失败!");
        }
    }

//	@RequestMapping("/findPage")
//	public @ResponseBody Page<ScoreQueryDTO> findAll(ExtPageable pageable)
//	{
//		
//		return scoreService.findPage(pageable.getPageable());
//	}

}
