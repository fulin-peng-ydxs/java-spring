package mybatis.plugs.page.dialect;

public class Db2Dialect extends DefaultDialect {
    public Db2Dialect() {
    }

    public boolean supportsLimit() {
        return true;
    }

    public boolean supportsLimitOffset() {
        return true;
    }

    protected String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        if (limit < 0) {
            pagingSelect.append(sql);
        } else {
            pagingSelect.append("select * from ( select row_.*, row_number() over() as rownum_ from ( ").append(sql).append(" ) row_ ) where rownum_ > ").append(offset).append(" and rownum_ <= ").append(offset + limit);
        }

        return pagingSelect.toString();
    }
}
