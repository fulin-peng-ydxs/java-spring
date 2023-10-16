package mybatis.plugs.page.dialect;

public interface IDialect {
    boolean supportsLimit();

    boolean supportsLimitOffset();

    String getLimitString(String var1, int var2, int var3);
}
