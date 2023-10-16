package mybatis.plugs.page.dialect;

public class DmDialect extends DefaultDialect {
    public DmDialect() {
    }

    public boolean supportsLimit() {
        return true;
    }

    public boolean supportsLimitOffset() {
        return true;
    }

    protected String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        sql = sql.trim();
        boolean isForUpdate = false;
        if (sql.toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }

        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        if (limit < 0) {
            pagingSelect.append(sql);
        } else {
            if (offset >= 0) {
                pagingSelect.append("select * from ( select top " + offset + "," + limit + " *,rownum rownum_ from ( ");
                pagingSelect.append(sql);
                pagingSelect.append(" ) aaa)");
            } else {
                pagingSelect.append("select *,rownum rownum_ from ( ");
                pagingSelect.append(sql);
                pagingSelect.append(" ) aaa");
            }

            if (isForUpdate) {
                pagingSelect.append(" for update");
            }
        }

        return pagingSelect.toString();
    }
}
