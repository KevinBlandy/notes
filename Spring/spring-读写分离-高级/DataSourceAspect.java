public class DataSourceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * 事务中定义事务属性为:readOnly的方法名
     */
    private List<String> slaveMethodPattern = new ArrayList<String>();

    /**
     * 默认的读库,方法名开头
     */
    private static final String[] DEFAULT_SLAVE_METHOD_START = new String[]{ "query", "find", "get" ,"load"};

    /**
     * 手动指定读库方法前缀
     */
    private String[] slaveMethodStart;

    /**
     * 读取事务管理中的策略
     * @param txAdvice
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void setTxAdvice(TransactionInterceptor txAdvice) throws Exception {
        if (txAdvice == null) {
            // 没有配置事务管理策略
            return;
        }
        //从txAdvice获取到策略配置信息
        TransactionAttributeSource transactionAttributeSource = txAdvice.getTransactionAttributeSource();
        if (!(transactionAttributeSource instanceof NameMatchTransactionAttributeSource)) {
            return;
        }
        //使用反射获取到NameMatchTransactionAttributeSource对象中的nameMap属性值
        NameMatchTransactionAttributeSource matchTransactionAttributeSource = (NameMatchTransactionAttributeSource) transactionAttributeSource;
        Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
        nameMapField.setAccessible(true); //暴力访问
        //获取nameMap的值
        Map<String, TransactionAttribute> map = (Map<String, TransactionAttribute>) nameMapField.get(matchTransactionAttributeSource);
        //遍历nameMap
        for (Map.Entry<String, TransactionAttribute> entry : map.entrySet()) {
            if (!entry.getValue().isReadOnly()) {
                continue;
            }
            //定义了ReadOnly的策略方法名称,加入到slaveMethodPattern
            slaveMethodPattern.add(entry.getKey());
        }
    }

    /**
     * 在进入Service方法之前执行
     * @param point
     */
    public void before(JoinPoint point) {

        // 当前执行的方法名
        String methodName = point.getSignature().getName();

        //flag
        boolean isSlave = false;

        if (slaveMethodPattern.isEmpty()) {
            // 当前Spring容器中没有配置事务策略，采用方法名匹配方式
            isSlave = isSlave(methodName);
        } else {
            // 使用策略规则中,只读属性的方法名进行匹配
            for (String mappedName : this.slaveMethodPattern) {
                if (this.isMatch(methodName, mappedName)) {
                    isSlave = true;
                    break;
                }
            }
        }
        if (isSlave) {
            // 标记为读库
            DynamicDataSourceHolder.markSlave();
            LOGGER.info("[动态数据源] {} = 读库",methodName);
        } else {
            // 标记为写库
            DynamicDataSourceHolder.markMaster();
            LOGGER.info("[动态数据源] {} = 写库",methodName);
        }
    }

    /**
     * 判断是否为读库
     * @param methodName
     * @return
     */
    private Boolean isSlave(String methodName) {
        if(methodName == null){
            return false;
        }
        //从默认,或者IOC中配置的规则进行匹配
        for(String name : this.getSlaveMethodStart()){
            if(methodName.startsWith(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * 通配符匹配
     * @param methodName
     * @param mappedName
     * @return
     */
    protected boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    /**
     * 指定slave的方法名前缀
     * @param slaveMethodStart
     */
    public void setSlaveMethodStart(String[] slaveMethodStart) {
        this.slaveMethodStart = slaveMethodStart;
    }

    public String[] getSlaveMethodStart() {
        if(this.slaveMethodStart == null || this.slaveMethodStart.length == 0){
            // 没有指定，使用默认
            LOGGER.info("slaveMethodStart,未配置,使用默认方法名称规则 = {}",Arrays.toString(DEFAULT_SLAVE_METHOD_START));
            return DEFAULT_SLAVE_METHOD_START;
        }
        return slaveMethodStart;
    }
}
