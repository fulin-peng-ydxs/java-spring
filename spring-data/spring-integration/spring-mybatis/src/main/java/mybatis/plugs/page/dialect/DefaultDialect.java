package mybatis.plugs.page.dialect;

public class DefaultDialect implements IDialect {
    public DefaultDialect() {
    }

    public boolean supportsLimit() {
        return false;
    }

    public boolean supportsLimitOffset() {
        return this.supportsLimit();
    }

    public String getLimitString(String sql, int offset, int limit) {
        return this.getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
    }

    protected String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        throw new UnsupportedOperationException("paged queries not supported");
    }
}
