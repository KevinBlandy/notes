---------------------------
流式读取
---------------------------


@Select("select * from xxx order by xx desc")
@Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = Integer.MIN_VALUE)
@ResultType(XxxObject.class)
void queryStreamResult(ResultHandler<XxxObject> handler);

