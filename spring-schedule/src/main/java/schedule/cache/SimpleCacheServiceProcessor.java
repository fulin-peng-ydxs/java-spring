package schedule.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import schedule.utils.TaskScheduleManager;
import java.util.LinkedList;
import java.util.List;

/**
 * 缓存协调器
 * @author peng_fu_lin
 * 2023-06-27 16:32
 */
@Order
@Slf4j
@Service
public class SimpleCacheServiceProcessor implements ApplicationListener<ContextRefreshedEvent> {

    private List<CacheService> CacheServices;

    @Autowired
    TaskScheduleManager taskScheduleManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        CacheServices=new LinkedList<>();
        String[] beanNamesForType = context.getBeanNamesForType(CacheService.class);
        for (String beanName : beanNamesForType) {
            CacheService CacheService = context.getBean(beanName, CacheService.class);
            CacheServices.add(CacheService);
        }
        log.debug("初始化缓存服务...");
        initCache();
        log.debug("注册缓存任务...");
        registerScheduled();
    }

    /**
     * 初始化缓存: 服务完成启动后调用一次
     */
    protected void initCache(){
        if (CacheServices.size()>0) {   //初始化缓存
            CacheServices.forEach(value->{
                log.debug("正在初始化：{}服务的缓存",value.getClass().getSimpleName());
                value.initCache();
            });
        }
    }

    /**
     * 注册定时任务：服务完成启动后调用一次
     */
    protected void registerScheduled(){
        if (CacheServices.size()<1)
            return;
        CacheServices.forEach(value-> {
                    if(value.getCronExpression()!=null){
                        String name = value.getClass().getName();
                        if (taskScheduleManager.getScheduled(name)==null) {
                            Runnable runnable = value::updateCache;
                            taskScheduleManager.registerCron(runnable,name,value.getCronExpression());
                        }
                    }
                }
        );
    }

    /**
     * 刷新缓存：提供给外部服务或接口主动调用
     */
    public void flushCache(){
        if (CacheServices.size()>0) {
            CacheServices.forEach(value->{
                log.debug("正在更新：{}服务的缓存",value.getClass().getSimpleName());
                value.updateCache();
            });
        }
    }

    /**
     * 移除定时任务:提供给外部服务或接口主动调用
     */
    public void removeScheduled(String CacheServiceName){
        taskScheduleManager.closeScheduled(CacheServiceName);
    }
}