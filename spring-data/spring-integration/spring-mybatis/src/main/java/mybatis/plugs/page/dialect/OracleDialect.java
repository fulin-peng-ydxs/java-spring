package mybatis.plugs.page.dialect;

public class OracleDialect extends DefaultDialect {
    public OracleDialect() {
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
            if (offset > 0) {
                pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
            } else {
                pagingSelect.append("select * from ( ");
            }

            pagingSelect.append(sql);
            if (offset > 0) {
                String endString = offsetPlaceholder + "+" + limitPlaceholder;
                pagingSelect.append(" ) row_ ) where rownum_ <= " + endString + " and rownum_ > " + offsetPlaceholder);
            } else {
                pagingSelect.append(" ) where rownum <= " + limitPlaceholder);
            }

            if (isForUpdate) {
                pagingSelect.append(" for update");
            }
        }

        return pagingSelect.toString();
    }
}
