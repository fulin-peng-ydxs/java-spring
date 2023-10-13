package schedule.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import schedule.utils.TaskSchedulerHolder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 缓存服务管理器
 * @author peng_fu_lin
 * 2023-06-27 16:32
 */
@Order
@Slf4j
@Service
public class CacheServiceManager implements ApplicationListener<ContextRefreshedEvent> {

    //缓存服务集合
    private final List<CacheService> cacheServices;

    //任务调度器：用于将缓存处理业务进行自动调度
    private final TaskScheduler taskScheduler;
    //调度任务集合：可用于处理注销调度任务执行
    private final Map<String, ScheduledFuture<?>> scheduledFutureMap=new HashMap<>();

    @Autowired
    public CacheServiceManager(ApplicationContext context, TaskSchedulerHolder taskSchedulerHolder){
        cacheServices =new LinkedList<>();
        String[] beanNamesForType = context.getBeanNamesForType(CacheService.class);
        for (String beanName : beanNamesForType) {
            CacheService cacheService = context.getBean(beanName, CacheService.class);
            cacheServices.add(cacheService);
        }
        //任务调度器持有者：用于从容器获取任务调度器
        taskScheduler= taskSchedulerHolder.getSimpleTaskScheduler();
        log.info("初始化缓存服务...");
        initCache();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            log.info("注册缓存服务的调度任务...");
            registerScheduleTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化缓存: 服务完成启动后调用一次
     */
    public void initCache(){
        if (cacheServices.size()>0) {   //初始化缓存
            cacheServices.forEach(value->{
                log.info("正在初始化：{}服务的缓存",value.getClass().getSimpleName());
                value.initCache();
            });
        }
    }

    /**
     * 刷新缓存：提供给外部服务或接口主动调用
     */
    public void flushCache(){
        if (cacheServices.size()>0) {
            cacheServices.forEach(value->{
                log.info("正在更新：{}服务的缓存",value.getClass().getSimpleName());
                value.updateCache();
            });
        }
    }

    /**
     * 注册定时任务
     */
    public void registerScheduleTask(){
        if (cacheServices.size()>0) {
            cacheServices.forEach(value-> {
                        if(value.getCronExpression()!=null){
                            String name = value.getClass().getName();
                            if (scheduledFutureMap.get(name)==null) {
                                Runnable runnable = value::updateCache;
                                ScheduledFuture<?> schedule =TaskSchedulerHolder.addCronScheduleTask(taskScheduler,runnable,value.getCronExpression());
                                scheduledFutureMap.put(name,schedule);
                            }
                        }
                    }
            );
        }
    }

    /**
     * 移除定时任务
     */
    public void removeScheduleTask(String cacheMonitorName){
        ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(cacheMonitorName);
        if(scheduledFuture!=null){
            TaskSchedulerHolder.removeScheduleTask(scheduledFuture);
            scheduledFutureMap.remove(cacheMonitorName);
        }
    }
}