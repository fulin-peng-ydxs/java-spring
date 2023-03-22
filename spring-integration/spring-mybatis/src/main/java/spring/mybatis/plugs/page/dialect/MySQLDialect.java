package spring.mybatis.plugs.page.dialect;

public class MySQLDialect extends DefaultDialect {
    public MySQLDialect() {
    }

    public boolean supportsLimitOffset() {
        return true;
    }

    public boolean supportsLimit() {
        return true;
    }

    protected String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        return offset > 0 ? sql + " limit " + offsetPlaceholder + "," + limitPlaceholder : sql + " limit " + limitPlaceholder;
    }
}
