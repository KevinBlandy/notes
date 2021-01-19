---------------------------
服务器响应
---------------------------
	# 需要依赖
		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpmime</artifactId>
		</dependency>

import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@GetMapping
	public void test (HttpServletResponse response) throws Exception {
		
		HttpEntity httpEntity = MultipartEntityBuilder.create()
					// 表单 => （名称，表单数据，类型）
					.addPart("name", new StringBody(UriUtils.encode("SpringBoot中文社区", StandardCharsets.UTF_8), ContentType.APPLICATION_FORM_URLENCODED))
					// JSON => （名称，Json数据，类型）
					.addPart("info", new StringBody("{\"site\": \"https://springboot.io\", \"year\": 2019}", ContentType.APPLICATION_JSON))
					// 文件 => （名称，文件，类型，文件名称）
					.addBinaryBody("logo", new File("D:\\logo.png"), ContentType.IMAGE_PNG, "logo.png")
					.build();
		
		// 设置ContentType
		response.setContentType(httpEntity.getContentType().getValue());
		
		// 响应客户端
		httpEntity.writeTo(response.getOutputStream());
	}
}

---------------------------
客户端解析
---------------------------
	# 需要依赖
		<dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>1.4</version>
		</dependency>

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class SimpleRequestContext implements RequestContext {
	private final Charset charset;			// 编码
	private final MediaType contentType;	// 类型
	private final InputStream content;		// 数据
	public SimpleRequestContext(Charset charset, MediaType contentType, InputStream content) {
		this.charset = charset;
		this.contentType = contentType;
		this.content = content;
	}
	@Override
	public String getCharacterEncoding() {
		return this.charset.displayName();
	}
	@Override
	public String getContentType() {
		return this.contentType.toString();
	}
	@Override
	public int getContentLength() {
		try {
			return this.content.available();
		} catch (IOException e) {
		}
		return 0;
	}
	@Override
	public InputStream getInputStream() throws IOException {
		return this.content;
	}
}

public class MultipartTest {
	public static void main(String[] args) throws IOException, FileUploadException {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Resource> responseEntity = restTemplate.getForEntity("http://localhost:8081/test", Resource.class);
		

		RequestContext requestContext = new SimpleRequestContext(StandardCharsets.UTF_8, responseEntity.getHeaders().getContentType(), responseEntity.getBody().getInputStream());
		
		FileUploadBase fileUploadBase = new PortletFileUpload();
		
		FileItemFactory fileItemFactory = new DiskFileItemFactory();
		
		fileUploadBase.setFileItemFactory(fileItemFactory);
		fileUploadBase.setHeaderEncoding(StandardCharsets.UTF_8.displayName());
		List<FileItem> fileItems = fileUploadBase.parseRequest(requestContext);
		for (FileItem fileItem : fileItems) {
			// 请求头
			System.out.println("headers:==========================");
			FileItemHeaders fileItemHeaders = fileItem.getHeaders();
			Iterator<String> headerNamesIterator = fileItemHeaders.getHeaderNames();
			while (headerNamesIterator.hasNext()) { // 迭代name
				String headerName = headerNamesIterator.next();
				Iterator<String> headerValueIterator =  fileItemHeaders.getHeaders(headerName);
				while (headerValueIterator.hasNext()) {	// 迭代value
					String headerValue = headerValueIterator.next();
					System.out.println(headerName + ":" +  headerValue);
				}
			}
			
			// 请求体
			System.out.println("body:==========================");
			if(fileItem.isFormField()) { // 是普通表单项
				byte[] data = fileItem.get();
				System.out.println(new String(data, StandardCharsets.UTF_8));
			} else {			// 是文件表单项
				String fileName = fileItem.getName();
				InputStream inputStream = fileItem.getInputStream();
				System.out.println("fileName=" + fileName + ", size=" +  inputStream.available());
			}
		}
	}
}
