package com.framework.mybatis;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Properties;

@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class TimeInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

        // 只拦截 insert 操作

        if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
            if (parameter != null) {
                try {
                    Field createTimeField = parameter.getClass().getDeclaredField("createTime");
                    createTimeField.setAccessible(true);
                    Object value = createTimeField.get(parameter);
                    if (value == null) {
                        // 这里可以用 LocalDateTime，也可以用 Date，根据你的实体字段类型决定
                        createTimeField.set(parameter, LocalDateTime.now());
                    }
                } catch (NoSuchFieldException e) {
                    // 如果实体没有 createTime 字段，忽略
                }
            }
        }
        if(ms.getSqlCommandType() == SqlCommandType.UPDATE){
            if (parameter != null) {
                try {
                    Field updateTimeField = parameter.getClass().getDeclaredField("updateTime");
                    updateTimeField.setAccessible(true);
                    Object value = updateTimeField.get(parameter);
                    if (value == null) {
                        // 这里可以用 LocalDateTime，也可以用 Date，根据你的实体字段类型决定
                        updateTimeField.set(parameter, LocalDateTime.now());
                    }
                } catch (NoSuchFieldException e) {
                    // 如果实体没有 createTime 字段，忽略
                }
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // 通过MyBatis提供的Plugin.wrap包装目标对象
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
