package com.sims.common.util;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import com.sims.score.entity.Score;

public class ScoreExportExcelUtil {
	public static boolean exportExcel(List<Score> logs,HttpServletResponse response){
		// 创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = workBook.createSheet("ScoreSheet");
		// sheet页名称
//		workBook.setSheetName(0,"ScoreSheet");
		// 创建header页
//		HSSFHeader header = sheet.getHeader();
		// 设置标题居中
//		header.setCenter("Score");
        //创建行  
        Row row = sheet.createRow(0);  
        //设置行高  
        row.setHeightInPoints(30);  



        //创建样式  
        CellStyle cs = workBook.createCellStyle();  
        cs.setAlignment(CellStyle.ALIGN_CENTER);  
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
//        cs.setBorderBottom(CellStyle.BORDER_DOTTED);   
//        cs.setBorderLeft(CellStyle.BORDER_THIN);   
//        cs.setBorderRight(CellStyle.BORDER_THIN);  
//        cs.setBorderTop(CellStyle.BORDER_THIN);  
        sheet.setDefaultColumnWidth(15);

		// 设置字符集
		//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		//cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
		//cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
        
		// 设置第一行为Header

//		HSSFCell cell0 = row.createCell(Short.valueOf("0"));
//		HSSFCell cell1 = row.createCell(Short.valueOf("1"));
//		HSSFCell cell2 = row.createCell(Short.valueOf("2"));
//		HSSFCell cell3 = row.createCell(Short.valueOf("3"));
//		HSSFCell cell4 = row.createCell(Short.valueOf("4"));
//		HSSFCell cell5 = row.createCell(Short.valueOf("5"));
//		cell0.setCellValue("score");
//		cell1.setCellValue("number");
//		cell2.setCellValue("name");
//		cell3.setCellValue("cNo");
//		cell4.setCellValue("cName");
//		cell5.setCellValue("recordDate");
        
        //创建单元格  
        Cell cell = row.createCell(0); 
        cell.setCellStyle(cs);
        cell.setCellValue("score");  
        cell = row.createCell(1);
        cell.setCellStyle(cs);
        cell.setCellValue("number");  
        cell = row.createCell(2);
        cell.setCellStyle(cs);
        cell.setCellValue("name");  
        cell = row.createCell(3);
        cell.setCellStyle(cs);
        cell.setCellValue("cNo");  
        cell = row.createCell(4);
        cell.setCellStyle(cs);
        cell.setCellValue("cName");  
        cell = row.createCell(5);
        cell.setCellStyle(cs);
        cell.setCellValue("recordDate");  
        


		if(logs!=null&&!logs.isEmpty())
		{
			for (int i = 0; i < logs.size(); i++) {
				Score log = logs.get(i);
				row = sheet.createRow(i + 1);
//				cell0 = row.createCell(Short.valueOf("0"));
//				cell1 = row.createCell(Short.valueOf("1"));
//				cell2 = row.createCell(Short.valueOf("2"));
//				cell3 = row.createCell(Short.valueOf("3"));
//				cell4 = row.createCell(Short.valueOf("4"));
//				cell5 = row.createCell(Short.valueOf("5"));

				// 设置字符集
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				//cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
				//cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
		        cell = row.createCell(0); 
		        cell.setCellStyle(cs);
		        cell.setCellValue(log.getScore());  
		        cell = row.createCell(1);
		        cell.setCellStyle(cs);
		        cell.setCellValue(log.getNumber());  
		        cell = row.createCell(2);
		        cell.setCellStyle(cs);
		        cell.setCellValue(log.getName());  
		        cell = row.createCell(3);
		        cell.setCellStyle(cs);
		        cell.setCellValue(log.getcNo());  
		        cell = row.createCell(4);
		        cell.setCellStyle(cs);
		        cell.setCellValue(log.getcName());  
		        cell = row.createCell(5);
		        cell.setCellStyle(cs);
		        cell.setCellValue(log.getRecordDate());
				//cell1.setCellValue(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(log.getCreatedate()));


//				sheet.setColumnWidth((short) 0, (short) 2000);
//				sheet.setColumnWidth((short) 1, (short) 3000);
//				sheet.setColumnWidth((short) 2, (short) 3000);
//				sheet.setColumnWidth((short) 3, (short) 3000);
//				sheet.setColumnWidth((short) 4, (short) 3000);
//				sheet.setColumnWidth((short) 5, (short) 5000);
			}
		}

		// 通过Response把数据以Excel格式保存
		response.reset();response.setContentType("application/msexcel;charset=UTF-8");
		try
		{
			response.addHeader("Content-Disposition",
					"attachment;filename=\"" + new String(("Scores" + ".xls").getBytes("GBK"), "ISO8859_1") + "\"");
			OutputStream out = response.getOutputStream();
			workBook.write(out);
			out.flush();
			if(null != out){ 
				out.close();
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	

}

