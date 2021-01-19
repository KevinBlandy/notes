import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
/**
 * DynamicDataSource AOP 切面
 * */
public class DataSourceAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAspect.class);
	/**
	 * 定义,标识为检索方法的方法名开头
	 * */
	private String[] readOnlyMethodNames = null;
	/**
     * 在进入Service方法之前执行
     * */
    public void before(JoinPoint point) {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        LOGGER.debug("读写分类拦截,业务层方法={}",methodName);
        if (isSlave(methodName)) {
            // 标记为读库
        	LOGGER.debug("标记 == > 读库");
            DynamicDataSourceHolder.markSlave();
        } else {
            // 标记为写库
        	LOGGER.debug("标记 == > 写库");
            DynamicDataSourceHolder.markMaster();
        }
    }

    /**
     * 判断是否为读库
     * */
    private Boolean isSlave(String methodName) {
    	if(StringUtils.isEmpty(methodName)){
    		return false;
    	}
    	for(String name : this.getMethodNames()){
    		if(methodName.startsWith(name)){
    			//匹配任意,则认为是检索方法
    			return true;
    		}
    	}
    	return false;
    }

	public String[] getMethodNames() {
		if(this.readOnlyMethodNames == null || this.readOnlyMethodNames.length == 0){
			//默认
			this.readOnlyMethodNames = new String[]{"query","get","find","load"};
		}
		return this.readOnlyMethodNames;
	}
	public void setMethodNames(String[] readOnlyMethNames) {
		this.readOnlyMethodNames = readOnlyMethNames;
	}
}
