package com.yimei.hs.boot;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.util.Reflections;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by xiangyang on 16/8/25.
 */
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();

        StatementHandler delegate = (StatementHandler) Reflections.getFieldValue(handler, "delegate");

        BoundSql boundSql = delegate.getBoundSql();

        Object obj = boundSql.getParameterObject();

        //传入的参数是BaseFilter对象就认定它是需要进行分页操作的。
        if (obj instanceof BaseFilter<?>) {

            BaseFilter<?> pageParam = (BaseFilter<?>) obj;

            MappedStatement mappedStatement = (MappedStatement) Reflections.getFieldValue(delegate, "mappedStatement");

            Connection connection = (Connection) invocation.getArgs()[0];

            String sql = boundSql.getSql();

            //给当前的page参数对象设置总记录数
            this.setTotalRecord(pageParam, mappedStatement, connection);

            //获取分页Sql语句
            String pageSql = this.getPageSql(pageParam, sql);

            Reflections.setFieldValue(boundSql, "sql", pageSql);

            //放到ThreadLocal
            PageContext.setPageParam(pageParam);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private void setTotalRecord(BaseFilter<?> page, MappedStatement mappedStatement, Connection connection) {
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        String sql = boundSql.getSql();

        // String countSql = this.getCountSql(sql);
        String countSql = page.getCountSql(sql);

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                //当前的参数pageParam对象设置总记录数
                page.setTotalRecord(totalRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCountSql(String sql) {
        return "select count(1) from (" + sql + ") jm_t";
    }



    private String getPageSql(BaseFilter<?> pageParam, String sql) {
        int offset = (pageParam.getPageNo() - 1) * pageParam.getPageSize();
        StringBuffer sqlBuffer = new StringBuffer(sql);
        sqlBuffer.append(" limit ").append(offset).append(",").append(pageParam.getPageSize());
        return sqlBuffer.toString();
    }
}
