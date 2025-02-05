package mybatis.plugs.page.dialect;

public class SQLServer2005Dialect extends DefaultDialect {
    public SQLServer2005Dialect() {
    }

    public boolean supportsLimit() {
        return true;
    }

    public boolean supportsLimitOffset() {
        return true;
    }

    protected String getLimitString(String querySqlString, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        StringBuffer pagingBuilder = new StringBuffer();
        String orderby = getOrderByPart(querySqlString);
        String distinctStr = "";
        String loweredString = querySqlString.toLowerCase();
        String sqlPartString = querySqlString;
        if (loweredString.trim().startsWith("select")) {
            int index = 6;
            if (loweredString.startsWith("select distinct")) {
                distinctStr = "DISTINCT ";
                index = 15;
            }

            sqlPartString = querySqlString.substring(index);
        }

        pagingBuilder.append(sqlPartString);
        if (orderby == null || orderby.length() == 0) {
            orderby = "ORDER BY CURRENT_TIMESTAMP";
        }

        StringBuffer result = new StringBuffer();
        result.append("WITH query AS (SELECT ").append(distinctStr).append("TOP 100 PERCENT ").append(" ROW_NUMBER() OVER (").append(orderby).append(") as __row_number__, ").append(pagingBuilder).append(") SELECT * FROM query WHERE __row_number__ BETWEEN ").append(offset).append(" AND ").append(offset + limit).append(" ORDER BY __row_number__");
        return result.toString();
    }

    static String getOrderByPart(String sql) {
        String loweredString = sql.toLowerCase();
        int orderByIndex = loweredString.indexOf("order by");
        return orderByIndex != -1 ? sql.substring(orderByIndex) : "";
    }
}
