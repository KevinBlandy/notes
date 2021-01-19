import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by KevinBlandy on 2017/4/1 14:09
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSource.class);

    // 轮询计数,初始为-1,AtomicInteger是线程安全的
    private AtomicInteger counter = new AtomicInteger(-1);

    // 记录读库的key(配置在ioc中)
    private List<Object> slaveDataSources = new ArrayList<>(0);

    /**
     * 该方法的返回值,会作为一个Map<String,DataSource>中的key,去获取对应的数据源.
     * 所以,每次请求.只要改变这个key,就可以改变当此次请求,在持久层会话中注入的数据源,实现自动的读写分离
     */
    @Override
    protected Object determineCurrentLookupKey() {
        Object key = null;
        if (DynamicDataSourceHolder.isMaster() || this.slaveDataSources.isEmpty()) {
            /**
             * AOP标识主库,或者没有任何从库
             */
            key = DynamicDataSourceHolder.MASTER;
        }else {
            /**
             * 轮询算法获取从库
             */
            key = this.getSlaveKey();
        }
        LOGGER.info("[动态数据源] dataSourceKey = {}",key);
        return key;

    }
    /**
     * 父类实现了InitializingBean,Spring会在Bean初始化完成后回调 afterPropertlesSet方法
     * 通过该方法,获取到所有的读库key,存储到集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        // 由于父类的resolvedDataSources属性是私有的子类获取不到，需要使用反射获取
        Field field = ReflectionUtils.findField(AbstractRoutingDataSource.class, "resolvedDataSources");
        field.setAccessible(true); // 暴力访问
        try {
            //传递当前对象,反射获取该字段值,所有的数据源
            Map<Object, DataSource> resolvedDataSources = (Map<Object, DataSource>) field.get(this);
            for (Map.Entry<Object, DataSource> entry : resolvedDataSources.entrySet()) {
                //遍历所有的key
                if (DynamicDataSourceHolder.MASTER.equals(entry.getKey())) {
                    //如果是写库,跳过之
                    continue;
                }
                //添加写库的key到集合
                slaveDataSources.add(entry.getKey());
            }
        } catch (Exception e) {
            LOGGER.error("afterPropertiesSet error! ", e);
        }
    }

    /**
     * 轮询算法
     * @return
     */
    public Object getSlaveKey() {
        // 同步自增 1 后取模计算,得到的下标为：0、1、2、3……
        Integer index = counter.incrementAndGet() % this.slaveDataSources.size();
        if (counter.get() > 9999) { 	// 以免超出Integer范围
            counter.set(-1);		 	// 还原
        }
        return slaveDataSources.get(index);
    }
}
