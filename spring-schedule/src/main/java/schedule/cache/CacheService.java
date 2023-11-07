package schedule.cache;


/**
 * 缓存服务
 *
 * @author peng_fu_lin
 * 2023-06-01 18:16
 */
public interface CacheService {

    /**更新缓存
     * 2023/6/1 0001-18:31
     * @author pengfulin
     */
    void updateCache();


    /**初始化缓存
     * 2023/6/1 0001-18:43
     * @author pengfulin
     */
    default void initCache(){}

    /**定时任务表达式：让缓存可自行更新
     * <p>使用cron表达式定义，为空不加入</p>
     * 2023/6/1 0001-18:43
     * @author pengfulin
     */
    default String getCronExpression(){return null;}
}
