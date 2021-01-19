----------------------------
maven						|
----------------------------
	<dependency>
		<groupId>com.artofsolving</groupId>
		<artifactId>jodconverter</artifactId>
		<version>2.2.1</version>
	</dependency>


----------------------------
һЩdocument				|
----------------------------

	# doc
		DocumentFormat docDocumentFormat = new DocumentFormat("Microsoft Word",DocumentFamily.TEXT,"application/msword", "doc");
		docDocumentFormat.setExportFilter(DocumentFamily.TEXT, "MS Word 97");

	# docx
		DocumentFormat docxDocumentFormat = new DocumentFormat("Microsoft Word 2007 XML", DocumentFamily.TEXT,"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");

	# pdf
		DocumentFormat pdfDocumentFormat = new DocumentFormat("Portable Document Format", "application/pdf", "pdf");
		pdfDocumentFormat.setExportFilter(DocumentFamily.DRAWING, "draw_pdf_Export");
		pdfDocumentFormat.setExportFilter(DocumentFamily.PRESENTATION, "impress_pdf_Export");
		pdfDocumentFormat.setExportFilter(DocumentFamily.SPREADSHEET, "calc_pdf_Export");
		pdfDocumentFormat.setExportFilter(DocumentFamily.TEXT, "writer_pdf_Export");