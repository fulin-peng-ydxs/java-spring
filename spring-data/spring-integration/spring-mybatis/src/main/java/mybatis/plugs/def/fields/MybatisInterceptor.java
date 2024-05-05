package mybatis.plugs.def.fields;

import lombok.extern.slf4j.Slf4j;
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
public class MybatisInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

		String sqlId = mappedStatement.getId(); //sql标识
		log.debug("------sqlId------" + sqlId);
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();  //sql类型
		log.debug("------sqlCommandType------" + sqlCommandType);

		Object parameter = invocation.getArgs()[1];  //sql参数
		if (parameter == null) {
			return invocation.proceed();
		}

		if (SqlCommandType.INSERT == sqlCommandType) {  //插入语句
			//获取参数对象所有字段
			Class<?> clazz = parameter.getClass();
			List<Field> fieldList = new ArrayList<>();
			while (clazz != null) {
				fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
				clazz = clazz.getSuperclass();
			}
			Field[] fields = new Field[fieldList.size()];
			fieldList.toArray(fields);
			for (Field field : fields) {
				log.debug("------field.name------" + field.getName());
				//TODO 1.后续可改成可配置式,2.对于field的处理可用工具统一处理，减少编码冗余
				try {
					//创建人字段
					if ("createBy".equals(field.getName())) {
						field.setAccessible(true);
						Object localCreateBy = field.get(parameter);
						field.setAccessible(false);
						if (localCreateBy == null || "".equals(localCreateBy)) {
							field.setAccessible(true);
							field.set(parameter, getUserId());
							field.setAccessible(false);
						}
					}
					// 注入创建时间
					if ("createTime".equals(field.getName())) {
						field.setAccessible(true);
						Object localCreateDate = field.get(parameter);
						field.setAccessible(false);
						if (localCreateDate == null || "".equals(localCreateDate)) {
							field.setAccessible(true);
							field.set(parameter, new Date());
							field.setAccessible(false);
						}
					}
					//注入部门编码
					if ("sysOrgCode".equals(field.getName())) {
						field.setAccessible(true);
						Object localSysOrgCode = field.get(parameter);
						field.setAccessible(false);
						if (localSysOrgCode == null || "".equals(localSysOrgCode)) {
							field.setAccessible(true);
							field.set(parameter, getUserOrgCode());
							field.setAccessible(false);
						}
					}
				} catch (Exception e) {
					throw new RuntimeException("新增自动注入标准字段异常",e);
				}
			}
		}

		if (SqlCommandType.UPDATE == sqlCommandType) { //更新语句
			Field[] fields;
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
				Class<?> clazz = parameter.getClass();
				List<Field> fieldList = new ArrayList<>();
				while (clazz != null) {
					fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
					clazz = clazz.getSuperclass();
				}
				fields = new Field[fieldList.size()];
				fieldList.toArray(fields);
			} else {
				Class<?> clazz = parameter.getClass();
				List<Field> fieldList = new ArrayList<>();
				while (clazz != null) {
					fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
					clazz = clazz.getSuperclass();
				}
				fields = new Field[fieldList.size()];
				fieldList.toArray(fields);
			}

			for (Field field : fields) {
				log.debug("------field.name------" + field.getName());
				try {
					if ("updateBy".equals(field.getName())) {
						field.setAccessible(true);
						field.set(parameter, getUserId());
						field.setAccessible(false);
					}
					if ("updateTime".equals(field.getName())) {
						field.setAccessible(true);
						field.set(parameter, new Date());
						field.setAccessible(false);
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

}
