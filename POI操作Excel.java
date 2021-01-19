
Excel
	* 是由多个工作表组成
	* 每个表都是由行和列构成(当年,我就好奇为什么不用Excel当数据库)
	* 行号:使用数字1-N
	* 列号:使用字母A-Z

	* JAVA中操作Excel的有两种比较主流的工具包
		1,JXL
			* 只能操作Excel95极其以后的版本,也就是后缀为.xls和.xlsx两种格式的Excel
			* http://wwww.andykhan.com/jexceapi
		2,POI
			* 全称:Poor Obfuscation Implementation,直译为:可怜的模糊实现
			* 使用POI接口,可以通过JAVA操作Microsoft office套件工具的读写功能
			* http://poi.apache.org
			* POI支持office的所有版本
	
	
	* 获取操作excel需要的jar包,并把这些jar包复制到项目中
	* 操作2003及以前的版本:poi-3.10.1-xxxxx.jar
	* 操作2007及以后的版本:poi-ooxml-3.10.10-xxxx.jar

	* 操作步奏思路
		1,创建工作薄
		2,创建工作表
		3,创建行
		4,创建单元格
	

	* 对应的对象(xls)
		HSSFWorkbook	Excel工作薄
		HSSFSheet		Excel工作表
		HSSFRow			Excel行
		HSSFCell		Excel单元格
	* 对应的对象(xlsx)
		XSSFWorkbook	..
		XSSFSheet		..
		XSSFRow			..
		XSSFCell		..
	* 它们都有一个父类
		* WorkBook
		* 可以截取文件后缀的方式来进行判断					
		* Workbook workBook = is03Exel?new HSSFWorkbook():new XSSFWorkbook();
	
代码演示:
package com.oa.test;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * POI测试
 * @author	KevinBlandy
 * */
public class POITest 
{
	public static void main(String[] args) throws IOException
	{
		write();		//写入到本地
		read();			//从本地读取
	}
	public static void write() throws IOException
	{
		//创建工作薄				HSSFWorkbook
		HSSFWorkbook workBook = new HSSFWorkbook();
		//由工作薄创建工作表,指定名称	HSSFSheet
		HSSFSheet sheet = workBook.createSheet("工作薄");
		//由工作表创建行,第几行		HSSFRow
		HSSFRow row = sheet.createRow(1);
		//创建单元格,第几列			HSSFCell
		HSSFCell cell = row.createCell(1);
		//由单元格,创建内容
		cell.setCellValue("KevinBlandy");
		//创建字节输出流
		OutputStream outFile = new FileOutputStream("D:\\test.xls");
		//调用工作薄方法,传递输出流对象,序列化到本地硬盘
		workBook.write(outFile);
		//关闭资源
		outFile.close();
		workBook.close();
	}
	public static void read() throws IOException
	{
		//获取本地文件流
		InputStream inputFile = new FileInputStream("D:\\test.xls");
		//创建工作薄对象,传递文件流
		HSSFWorkbook workBook = new HSSFWorkbook(inputFile);
		//从工作薄对象,获取指定名称的工作表
		HSSFSheet sheet = workBook.getSheet("工作薄");
		/**
		 * 也可以根据下标,也就是索引获取
		 * HSSFSheet sheet = workBook.getSheetAt(0);
		 * */
		//由工作表读取指定行
		HSSFRow row = sheet.getRow(1);
		//由指定行,获取指定单元格
		HSSFCell cell = row.getCell(1);
		//设置单元格格式为字符串格式
		cell.setCellType(CellType.STRING);
		//由指定单元格,获取其字符串类型的值
		String value = cell.getStringCellValue();
		System.out.println(value);
	}
}





POI 样式控制
	* Excel的样式控制
	1,合并单元格对象
		是属于工作薄,应用于工作表
		CellRangeAddress c = CellRangeAddress(起始行号,结束行号,起始列号,结束列号)
		
	2,
		

	
	
