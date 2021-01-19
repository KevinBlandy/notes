import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import io.springboot.demo.common.Message;
import io.springboot.demo.common.Messages;

/**
 * 
 * 日志管理
 * @author KevinBlandy
 *
 */
@RestController
@RequestMapping("/logger")
public class LogController {

	/**
	 * 查看logger列表
	 * @return
	 */
	@GetMapping
	public Object logger() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		List<Map<String, String>> loggers = loggerContext.getLoggerList().stream().map(logger -> {
			Map<String, String> retVal = new LinkedHashMap<>();
			// logger名称 : logger有效级别
			retVal.put(logger.getName(), logger.getEffectiveLevel().levelStr);
			return retVal;
		}).collect(Collectors.toList());
		return Message.ok(loggers);
	}
	
	/**
	 * 修改日志级别
	 * @param logger
	 * @param level
	 * @return
	 */
	@PutMapping
	public Object logger(@RequestParam("name") String name,
							@RequestParam("level") String level) {
		
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		
		Logger logger = loggerContext.exists(name);
		if (logger == null) {
			return Message.error(name + " 日志记录器不存在", Message.Code.NOT_FOUND, HttpStatus.NOT_FOUND).responseEntity();
		}
		
		Level newLevel = Level.toLevel(level, null);
		
		if (newLevel == null) {
			return Message.error(level + " 不是合法的日志级别", Message.Code.BAD_REQUEST, HttpStatus.BAD_REQUEST).responseEntity();
		}
		
		logger.setLevel(newLevel);
		
		return Messages.OK;
	}
}