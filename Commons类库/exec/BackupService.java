
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.sobyte.constant.SystemConstant;
import org.sobyte.model.dto.BackupFileDTO;
import org.sobyte.utils.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * 数据备份服务
 * 
 * @author KevinBlandy
 *
 */
@Service
@Slf4j
public class BackupService {

	public static final DateTimeFormatter BACK_UP_FILE_NAME_FORMATTER = DateTimeFormatter
			.ofPattern("yyyyMMddHHmmssSSS");

	@Autowired
	private HikariDataSource hikariDataSource;

	/**
	 * 读取缓存的文件列表
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<BackupFileDTO> list() throws IOException {

		if (Files.notExists(SystemConstant.BACK_PATH)) {
			return List.of();
		}

		return Files.list(SystemConstant.BACK_PATH).filter(file -> !Files.isDirectory(file)).sorted((p1, p2) -> {
			try {
				return Files.readAttributes(p1, BasicFileAttributes.class).creationTime()
						.compareTo(Files.readAttributes(p2, BasicFileAttributes.class).creationTime());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}).map(file -> {
			try {
				return BackupFileDTO.builder().name(file.getFileName().toString())
						.creatAt(LocalDateTime.ofInstant(
								Files.readAttributes(file, BasicFileAttributes.class).creationTime().toInstant(),
								ZoneId.systemDefault()))
						.build();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}).toList();
	}

	/*
	 * /backup
	 * 
	 */
	/**
	 *
	 * 执行文件备份操作
	 * 
	 * @throws IOException
	 */
	public void backup() throws IOException {
		// 创建临时工作目录
		Path tempDir = Files.createDirectories(SystemConstant.BACK_PATH.resolve("temp"));
		try {
			log.info("压缩资源文件...");

			/**
			 * 压缩资源文件
			 */
			Path publicFile = ZipUtils.zip(SystemConstant.UPLOAD_PATH.getParent(), tempDir.resolve("public.zip"));

			log.info("资源文件压缩完毕: {}", publicFile);

			/**
			 * SQL资源文件导出 ！！！只能导出本地MYSQL服务器上的数据，如果MYSQL服务器在其他机器，则会执行失败
			 */
			Path sqlFile = tempDir.resolve("db.sql");

			mysqlDump(sqlFile);

			if (!Files.exists(sqlFile)) {
				// SQL导出失败的情况下， 创建空文件
				Files.createFile(sqlFile);
			}

			Path backUpFile = SystemConstant.BACK_PATH
					.resolve("backup-" + BACK_UP_FILE_NAME_FORMATTER.format(LocalDateTime.now()) + ".zip");

			log.info("打包文件: {}", backUpFile);

			ZipUtils.zip(new Path[] { publicFile, sqlFile }, backUpFile);

			log.info("备份成功");
		} finally {
			// 清空工作目录
			FileUtils.cleanDirectory(tempDir.toFile());
		}
	}

	/**
	 * 导出SQL文件
	 * 
	 * @param file 导出的SQL文件
	 */
	private void mysqlDump(Path file) {

		// 获取当前数据库名称
		String database = null;

		try (Connection connection = hikariDataSource.getConnection()) {
			try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT DATABASE();")) {
				if (resultSet.next()) {
					database = resultSet.getString(1);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		ByteArrayOutputStream stdErr = new ByteArrayOutputStream();

		try (OutputStream stdOut = new BufferedOutputStream(Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {

			DefaultExecutor defaultExecutor = new DefaultExecutor();
			defaultExecutor.setWatchdog(new ExecuteWatchdog(TimeUnit.MINUTES.toMillis(10))); // 超时时间，10分钟
			defaultExecutor.setStreamHandler(new PumpStreamHandler(stdOut, stdErr));

			CommandLine commandLine = new CommandLine("mysqldump");
			commandLine.addArgument("-u" + hikariDataSource.getUsername()); // 用户名
			commandLine.addArgument("-p" + hikariDataSource.getPassword()); // 密码
			commandLine.addArgument(database);

			log.info("导出SQL数据...");

			// 同步执行，返回执行结果
			int exitCode = defaultExecutor.execute(commandLine);

			// 异步执行
//				DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
//				defaultExecutor.execute(commandLine, resultHandler);
//
//				// 阻塞，直到执行完毕
//				resultHandler.waitFor();

			log.info("SQL数据导出完毕: exitCode={}, sqlFile={}", exitCode, file.toString());
			
		} catch (Exception e) {
			log.error("SQL数据导出异常: {}", e.getMessage());
			log.error("std err: {}{}", System.lineSeparator(), stdErr.toString());
		}
	}

	/**
	 * 从PATH中解析出可执行文件的绝对路径
	 * 
	 * @param execute
	 * @return
	 */
//	protected String lockPath(String execute) {
//		// 从path中解析可执行文件的绝对路径
//		String path = System.getenv("PATH");
//
//		String separator = System.getProperty("path.separator");
//
//		String[] suffixs = null;
//
//		if (SystemUtils.IS_OS_WINDOWS) {
//			suffixs = new String[] { ".exe", ".bat", ".cmd" };
//		} else {
//			suffixs = new String[] { "" };
//		}
//
//		for (String dir : path.split(separator)) {
//			for (String suffix : suffixs) {
//				Path file = Paths.get(dir, execute + suffix);
//				if (Files.exists(file)) {
//					return file.toString();
//				}
//			}
//		}
//		return null;
//	}
}
