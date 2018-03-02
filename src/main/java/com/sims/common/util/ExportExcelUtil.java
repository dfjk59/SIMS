package com.sims.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcelUtil<T> {
   @SuppressWarnings("unchecked")
   public void exportExcel(String title, String[] headers,
    Collection<T> dataset, OutputStream out, String pattern) {
      //声明一个工作薄
      HSSFWorkbook workbook = new HSSFWorkbook();
      //生成一个表格
      HSSFSheet sheet = workbook.createSheet(title);
      //设置表格默认列宽度为15个字节
      sheet.setDefaultColumnWidth(15);
      //生成一个样式
      HSSFCellStyle style = workbook.createCellStyle();
      //设置这些样式
      style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
      style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
      style.setBorderRight(HSSFCellStyle.BORDER_THIN);
      style.setBorderTop(HSSFCellStyle.BORDER_THIN);
      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      //生成一个字体
      HSSFFont font = workbook.createFont();
      font.setColor(HSSFColor.VIOLET.index);
      font.setFontHeightInPoints((short) 12);
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      //把字体应用到当前的样式
      style.setFont(font);
      //生成并设置另一个样式
      HSSFCellStyle style2 = workbook.createCellStyle();
      style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
      style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
      style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
      style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
      style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
      //生成另一个字体
      HSSFFont font2 = workbook.createFont();
      font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
      //把字体应用到当前的样式
      style2.setFont(font2);
    
      //声明一个画图的顶级管理器
      HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
      //定义注释的大小和位置,详见文档
      HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
      //设置注释内容
      comment.setString(new HSSFRichTextString("POI导出"));
      //设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
      //comment.setAuthor("leno");
 
      //产生表格标题行
      HSSFRow row = sheet.createRow(0);
      for (int i = 0; i < headers.length; i++) {
         HSSFCell cell = row.createCell(i);
         cell.setCellStyle(style);
         HSSFRichTextString text = new HSSFRichTextString(headers[i]);
         cell.setCellValue(text);
      }
 
      //遍历集合数据，产生数据行
      Iterator<T> it = dataset.iterator();
      int index = 0;
      while (it.hasNext()) {
         index++;
         row = sheet.createRow(index);
         T t = (T) it.next();
         //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
         Field[] fields = t.getClass().getDeclaredFields();
         for (int i = 0; i < fields.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style2);
            Field field = fields[i];
            String fieldName = field.getName();
            String getMethodName = "get"
                   + fieldName.substring(0, 1).toUpperCase()
                   + fieldName.substring(1);
            try {
                Class tCls = t.getClass();
                Method getMethod = tCls.getMethod(getMethodName,
                      new Class[] {});
                Object value = getMethod.invoke(t, new Object[] {});
                //判断值的类型后进行强制类型转换
                String textValue = null;
//              if (value instanceof Integer) {
//                 int intValue = (Integer) value;
//                 cell.setCellValue(intValue);
//              } else if (value instanceof Float) {
//                 float fValue = (Float) value;
//                 textValue = new HSSFRichTextString(
//                       String.valueOf(fValue));
//                 cell.setCellValue(textValue);
//              } else if (value instanceof Double) {
//                 double dValue = (Double) value;
//                 textValue = new HSSFRichTextString(
//                       String.valueOf(dValue));
//                 cell.setCellValue(textValue);
//              } else if (value instanceof Long) {
//                 long longValue = (Long) value;
//                 cell.setCellValue(longValue);
//              }
                if (value instanceof Sex) {
                	Sex sValue =(Sex) value;
                   
                   if ("FEMALE".equals(sValue)) {
                      textValue ="女";
                   }else {
                	   textValue = "男";
                   }
                } else if (value instanceof Date) {
                   Date date = (Date) value;
                   SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    textValue = sdf.format(date);
                }  else if (value instanceof byte[]) {
                   //有图片时，设置行高为60px;
                   row.setHeightInPoints(60);
                   //设置图片所在列宽度为80px,注意这里单位的一个换算
                   sheet.setColumnWidth(i, (short) (35.7 * 80));
                   // sheet.autoSizeColumn(i);
                   byte[] bsValue = (byte[]) value;
                   HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                         1023, 255, (short) 6, index, (short) 6, index);
                   anchor.setAnchorType(2);
                   patriarch.createPicture(anchor, workbook.addPicture(
                         bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                } else{
                   //其它数据类型都当作字符串简单处理
                   textValue = value.toString();
                }
                //如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                if(textValue!=null){
                   Pattern p = Pattern.compile("^//d+(//.//d+)?$"); 
                   Matcher matcher = p.matcher(textValue);
                   if(matcher.matches()){
                      //是数字当作double处理
                      cell.setCellValue(Double.parseDouble(textValue));
                   }else{
                      HSSFRichTextString richString = new HSSFRichTextString(textValue);
                      HSSFFont font3 = workbook.createFont();
                      font3.setColor(HSSFColor.BLUE.index);
                      richString.applyFont(font3);
                      cell.setCellValue(richString);
                   }
                }
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                //������Դ
            }
         }
      }
      
//      //此部分为单元格合并部分
//      //获得所有的行
//      int rows=sheet.getPhysicalNumberOfRows();
//      //获得所有行
//      int cels=row.getPhysicalNumberOfCells();
//      System.out.println("行数为："+rows+"列数为："+cels);
// 
//      List<Short> CellCompare=new ArrayList<Short>();
//      CellCompare.add((short) 0);
//      CellCompare.add((short) 3);
//      CellCompare.add((short) 7);
//      //创建一个对象进行调用
//      MergeCell mergeCell=new MergeCell();
//      mergeCell.mergeCell(sheet, rows, CellCompare);
//     
//	  for(int k=0;k<cels;k++)
//	  {
//     	for(int k=0;k<CellCompare.size();k++)
//      {
//      	for(int j=0;j<rows;j++)
//      	{
//            //获得第k列中第j行中的内容值
//        String cel1=sheet.getRow(j).getCell(CellCompare.get(k)).getStringCellValue();
//        //设置参数count用于计数，主要是用于比较当如果有相同的行值时，就进行计数
//        int count=0;
//        //进行for循环
//        for(int i=1;i<rows-j;i++)
//        {
//         //获得第k列中第j+i列中的内容值，即同一列中当前行和下一行值进行比较
//         String cel2=sheet.getRow(j+i).getCell(CellCompare.get(k)).getStringCellValue();
//         //把要比较的两行值进行比较
//         if(cel1.equals(cel2))
//         {
//          //如果两行值相同，那么就进行计数，并且继续判断
//          count++;
//         }else
//         {
//          //如果两行值不同，那么就停止比较
//          break;
//         }
//        }
//        //因为只有当两行值相同时，才会合并，而比较是当前行和下一行进行比较，如果出现相同的
//        //一行，那么计数就为1 ，表示有两行相同，可以合并，即只需要计数值count>0就可以合并单元格
//        
//       if(count>0)
//       {
//        //j表示当前行，j+count表示需要合并的结束行
//        sheet.addMergedRegion(new Region(j,(short)CellCompare.get(k),(j+count),(short)CellCompare.get(k)));
//       }
//      }
//    //}
//   }
     
      try {
         workbook.write(out);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}