package mybatis.plugs.page.dialect;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultDialectManager implements IDialectManager {

    private Map<String, IDialect> dialects;

    private final IDialect defaultDialect = new DefaultDialect();

    public DefaultDialectManager() {
    }

    public void setDialects(Map<String, IDialect> dialects) {
        this.dialects = dialects;
        this.initDialects();
    }

    protected void initDialects() {
        if (this.dialects == null) {
            this.dialects = new HashMap();
            this.dialects.put("dm", new DmDialect());
            this.dialects.put("db2", new Db2Dialect());
            this.dialects.put("mysql", new MySQLDialect());
            this.dialects.put("oracle", new OracleDialect());
            this.dialects.put("sqlServer2005", new SQLServer2005Dialect());
            this.dialects.put("sqlServer", new SQLServerDialect());
        }

    }

    public IDialect getDialect(String dialect) {
        this.initDialects();
        IDialect dialectClass = (IDialect)this.dialects.get(dialect);
        if (dialectClass == null) {
            dialectClass = this.defaultDialect;
        }

        return dialectClass;
    }
}
