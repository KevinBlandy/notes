
public static void word2Pdf(InputStream src, OutputStream dsc) throws IOException {
		
	//打开与Openoffice的链接
	OpenOfficeConnection connection = new SocketOpenOfficeConnection("192.168.265", 8100);
	connection.connect();

	//源文件document docx 类型
	DocumentFormat docxDocumentFormat = new DocumentFormat("Microsoft Word 2007 XML", DocumentFamily.TEXT,"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
	
	//源文件document doc 类型
	DocumentFormat docDocumentFormat = new DocumentFormat("Microsoft Word",
	DocumentFamily.TEXT,"application/msword", "doc");
	docDocumentFormat.setExportFilter(DocumentFamily.TEXT, "MS Word 97");


	//目标文件document pdf类型
	DocumentFormat pdfDocumentFormat = new DocumentFormat("Portable Document Format", "application/pdf", "pdf");
	pdfDocumentFormat.setExportFilter(DocumentFamily.DRAWING, "draw_pdf_Export");
	pdfDocumentFormat.setExportFilter(DocumentFamily.PRESENTATION, "impress_pdf_Export");
	pdfDocumentFormat.setExportFilter(DocumentFamily.SPREADSHEET, "calc_pdf_Export");
	pdfDocumentFormat.setExportFilter(DocumentFamily.TEXT, "writer_pdf_Export");

	
	//本地服务器转换
	//DocumentConverter documentConverter = new OpenOfficeDocumentConverter(connection);	
	
	//远程服务器转换
	StreamOpenOfficeDocumentConverter streamOpenOfficeDocumentConverter = new StreamOpenOfficeDocumentConverter(connection);
	//convert有几个重载,可以接收File等参数
	streamOpenOfficeDocumentConverter.convert(src, docDocumentFormat, dsc, pdfDocumentFormat);

	// 关闭连接
	connection.disconnect();
}