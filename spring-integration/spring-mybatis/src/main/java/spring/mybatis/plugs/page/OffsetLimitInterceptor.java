package spring.mybatis.plugs.page;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring.mybatis.plugs.page.dialect.IDialect;
import spring.mybatis.plugs.page.dialect.IDialectManager;

import java.util.Properties;

@Component
@Intercepts({@Signature(
		type = Executor.class,
		method = "query",
		args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
public class OffsetLimitInterceptor implements Interceptor {
	private static Logger logger = LoggerFactory.getLogger(OffsetLimitInterceptor.class);
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;
	private IDialectManager dialectManager;
	@Value("${mybatis.dialect:mysql}")
	private String dialect;

	public OffsetLimitInterceptor() {
	}

	@Autowired
	public void setDialectManager(IDialectManager dialectManager) {
		this.dialectManager = dialectManager;
	}

	public String getDialect() {
		return this.dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public Object intercept(Invocation invocation) throws Throwable {
		this.processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	void processIntercept(Object[] queryArgs) {
		MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
		IDialect dialectClass = this.dialectManager.getDialect(this.dialect);
		if (dialectClass.supportsLimit() && (offset != 0 || limit != 2147483647)) {
			BoundSql boundSql = ms.getBoundSql(parameter);
			String sql = boundSql.getSql().trim();
			if (dialectClass.supportsLimitOffset()) {
				sql = dialectClass.getLimitString(sql, offset, limit);
				offset = 0;
			} else {
				sql = dialectClass.getLimitString(sql, 0, limit);
			}

			limit = 2147483647;
			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
			Configuration con = new Configuration();
			BoundSql newBoundSql = new BoundSql(con, sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			if (boundSql.getParameterMappings() != null) {
				boundSql.getParameterMappings().stream().forEach((param) -> {
					String name = param.getProperty();
					if (boundSql.hasAdditionalParameter(name)) {
						newBoundSql.setAdditionalParameter(name, boundSql.getAdditionalParameter(name));
					}

				});
			}

			MappedStatement newMs = this.copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
			logger.debug("Mybatis OffsetLimit Proccess 分页SQL:{}", sql);
		}

	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		if (ms.getKeyProperties() != null) {
			String[] var4 = ms.getKeyProperties();
			int var5 = var4.length;

			for(int var6 = 0; var6 < var5; ++var6) {
				String property = var4[var6];
				builder.keyProperty(property);
			}
		}

		MappedStatement newMs = builder.build();
		return newMs;
	}

	public Object plugin(Object target) {
		return target instanceof Executor ? Plugin.wrap(target, this) : target;
	}

	public void setProperties(Properties properties) {
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSql getBoundSql(Object arg0) {
			return this.boundSql;
		}

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}
	}
}
