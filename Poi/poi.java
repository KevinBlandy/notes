--------------------
Poi
--------------------
	# ��ַ
		http://poi.apache.org/
		http://poi.apache.org/apidocs/5.0/

	# Maven
		<dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi</artifactId>
			<version>${poi.version}</version>
		</dependency>
		<dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi-ooxml</artifactId>
			<version>${poi-ooxml.version}</version>
		</dependency>














Excel
	* ���ɶ�����������
	* ÿ���������к��й���(����,�Ҿͺ���Ϊʲô����Excel�����ݿ�)
	* �к�:ʹ������1-N
	* �к�:ʹ����ĸA-Z

	* JAVA�в���Excel�������ֱȽ������Ĺ��߰�
		1,JXL
			* ֻ�ܲ���Excel95�����Ժ�İ汾,Ҳ���Ǻ�׺Ϊ.xls��.xlsx���ָ�ʽ��Excel
			* http://wwww.andykhan.com/jexceapi
		2,POI
			* ȫ��:Poor Obfuscation Implementation,ֱ��Ϊ:������ģ��ʵ��
			* ʹ��POI�ӿ�,����ͨ��JAVA����Microsoft office�׼����ߵĶ�д����
			* http://poi.apache.org
			* POI֧��office�����а汾
	
	
	* ��ȡ����excel��Ҫ��jar��,������Щjar�����Ƶ���Ŀ��
	* ����2003����ǰ�İ汾:poi-3.10.1-xxxxx.jar
	* ����2007���Ժ�İ汾:poi-ooxml-3.10.10-xxxx.jar

	* ��������˼·
		1,����������
		2,����������
		3,������
		4,������Ԫ��
	

	* ��Ӧ�Ķ���(xls)
		HSSFWorkbook	Excel������
		HSSFSheet		Excel������
		HSSFRow			Excel��
		HSSFCell		Excel��Ԫ��
	* ��Ӧ�Ķ���(xlsx)
		XSSFWorkbook	..
		XSSFSheet		..
		XSSFRow			..
		XSSFCell		..
	* ���Ƕ���һ������
		* WorkBook
		* ���Խ�ȡ�ļ���׺�ķ�ʽ�������ж�					
		* Workbook workBook = is03Exel?new HSSFWorkbook():new XSSFWorkbook();
	
������ʾ:
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
 * POI����
 * @author	KevinBlandy
 * */
public class POITest 
{
	public static void main(String[] args) throws IOException
	{
		write();		//д�뵽����
		read();			//�ӱ��ض�ȡ
	}
	public static void write() throws IOException
	{
		//����������				HSSFWorkbook
		HSSFWorkbook workBook = new HSSFWorkbook();
		//�ɹ���������������,ָ������	HSSFSheet
		HSSFSheet sheet = workBook.createSheet("������");
		//�ɹ���������,�ڼ���		HSSFRow
		HSSFRow row = sheet.createRow(1);
		//������Ԫ��,�ڼ���			HSSFCell
		HSSFCell cell = row.createCell(1);
		//�ɵ�Ԫ��,��������
		cell.setCellValue("KevinBlandy");
		//�����ֽ������
		OutputStream outFile = new FileOutputStream("D:\\test.xls");
		//���ù���������,�������������,���л�������Ӳ��
		workBook.write(outFile);
		//�ر���Դ
		outFile.close();
		workBook.close();
	}
	public static void read() throws IOException
	{
		//��ȡ�����ļ���
		InputStream inputFile = new FileInputStream("D:\\test.xls");
		//��������������,�����ļ���
		HSSFWorkbook workBook = new HSSFWorkbook(inputFile);
		//�ӹ���������,��ȡָ�����ƵĹ�����
		HSSFSheet sheet = workBook.getSheet("������");
		/**
		 * Ҳ���Ը����±�,Ҳ����������ȡ
		 * HSSFSheet sheet = workBook.getSheetAt(0);
		 * */
		//�ɹ������ȡָ����
		HSSFRow row = sheet.getRow(1);
		//��ָ����,��ȡָ����Ԫ��
		HSSFCell cell = row.getCell(1);
		//���õ�Ԫ���ʽΪ�ַ�����ʽ
		cell.setCellType(CellType.STRING);
		//��ָ����Ԫ��,��ȡ���ַ������͵�ֵ
		String value = cell.getStringCellValue();
		System.out.println(value);
	}
}





POI ��ʽ����
	* Excel����ʽ����
	1,�ϲ���Ԫ�����
		�����ڹ�����,Ӧ���ڹ�����
		CellRangeAddress c = CellRangeAddress(��ʼ�к�,�����к�,��ʼ�к�,�����к�)
		
	2,
		

	
	
