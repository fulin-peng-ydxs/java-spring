package mybatis.plugs.def.fields;

import lombok.extern.slf4j.Slf4j;
import mybatis.utils.ClassUtils;
import mybatis.utils.StringUtils;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 默认字段插件：自动注入创建人、创建时间、修改人、修改时间
 */
@Slf4j
@Component
@Intercepts({ @Signature(
		type = Executor.class,
		method = "update",
		args = { MappedStatement.class, Object.class })
})
public class MybatisDefaultFieldInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		log.debug("进入-默认字段插件处理器：");

		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		String sqlId = mappedStatement.getId(); //sql标识
		log.debug("------sqlId------{}" ,sqlId);
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();  //sql类型
		log.debug("------sqlCommandType------{}", sqlCommandType);

		Object parameter = invocation.getArgs()[1];  //sql参数
		if (parameter == null) {
			return invocation.proceed();
		}

		if (SqlCommandType.INSERT == sqlCommandType) {  //插入语句
			//获取参数对象所有字段
			Class<?> clazz = parameter.getClass();
			List<Field> fields = ClassUtils.getSupperClassFields(clazz, true, null);
			for (Field field : fields) {
				log.debug("------field.name------{}",field.getName());
				//TODO 1.后续可改成可配置式,2.对于field的处理可用工具统一处理，减少编码冗余
				try {
					//创建人字段
					if ("createdBy".equals(field.getName())) {
						String fieldValue = ClassUtils.getFieldValue(field, parameter, String.class);
						if (StringUtils.isEmpty(fieldValue)) {
							ClassUtils.setFieldValue(field,parameter,getUserId());
						}
					}
					// 注入创建时间
					if ("created".equals(field.getName())) {
						Date fieldValue = ClassUtils.getFieldValue(field, parameter, Date.class);
						if (fieldValue==null) {
							ClassUtils.setFieldValue(field,parameter,getCurrentDate());
						}
					}
					//注入部门编码
					if ("sysOrgCode".equals(field.getName())) {
						String fieldValue = ClassUtils.getFieldValue(field, parameter, String.class);
						if (StringUtils.isEmpty(fieldValue)) {
							ClassUtils.setFieldValue(field,parameter,getUserOrgCode());
						}
					}
				} catch (Exception e) {
					throw new RuntimeException("新增自动注入标准字段异常",e);
				}
			}
		} else if (SqlCommandType.UPDATE == sqlCommandType) { //更新语句
			if (parameter instanceof ParamMap) {
				ParamMap<?> p = (ParamMap<?>) parameter;
				String et = "et";
				if (p.containsKey(et)) {
					parameter = p.get(et);
				} else {
					parameter = p.get("param1");
				}
				if (parameter == null) {
					return invocation.proceed();
				}
			}
			Class<?> clazz = parameter.getClass();
			List<Field> fields = ClassUtils.getSupperClassFields(clazz, true, null);
			for (Field field : fields) {
				log.debug("------field.name------{}",field.getName());
				try {
					if ("updateBy".equals(field.getName())) {
						String fieldValue = ClassUtils.getFieldValue(field, parameter, String.class);
						if (StringUtils.isEmpty(fieldValue)) {
							ClassUtils.setFieldValue(field,parameter,getUserId());
						}
					}
					if ("updateTime".equals(field.getName())) {
						Date fieldValue = ClassUtils.getFieldValue(field, parameter, Date.class);
						if (fieldValue==null) {
							ClassUtils.setFieldValue(field,parameter, getCurrentDate());
						}
					}
				} catch (Exception e) {
					throw new RuntimeException("更新自动注入标准字段异常",e);
				}
			}
		}
		return invocation.proceed();
	}


	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 获取用户id
	 * 2024/5/5 16:20
	 * @author pengshuaifeng
	 */
	//TODO 需要根据实际系统架构自行实现
	public String getUserId(){
		return  "null";
	}

	/**
	 * 获取用户所属机构
	 * 2024/5/5 16:25
	 * @author pengshuaifeng
	 */
	//TODO 需要根据实际系统架构自行实现
	public String getUserOrgCode(){
		return  "null";
	}
	/**
	 * 获取当前时间
	 * 2024/8/5 下午6:21
	 * @author fulin-peng
	 */
	public Date getCurrentDate(){
		return new Date();
	}
}
