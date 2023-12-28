package schedule.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
/**
 * 任务调度管理器
 * @author fulin-peng
 * 2023-11-06  11:54
 */
@Getter
@Slf4j
@Service
public class TaskScheduleManager implements ApplicationListener<ContextRefreshedEvent> {

    //任务调度器
    private  TaskScheduler taskScheduler;

    //调度任务集合：任务名称为唯一标识
    private final Map<String, ScheduledFuture<?>> scheduledFutureMap=new HashMap<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            ApplicationContext applicationContext = event.getApplicationContext();
            ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor = applicationContext.getBean(ScheduledAnnotationBeanPostProcessor.class);
            Field registrarField = scheduledAnnotationBeanPostProcessor.getClass()
                    .getDeclaredField("registrar");
            registrarField.setAccessible(true);
            Object registrar = registrarField.get(scheduledAnnotationBeanPostProcessor);
            Field taskSchedulerField = registrar.getClass()
                    .getDeclaredField("taskScheduler");
            taskSchedulerField.setAccessible(true);
            taskScheduler =(TaskScheduler)taskSchedulerField.get(registrar);
            log.debug("获取任务调度器:{}",taskScheduler.getClass().getName());
        } catch (Exception e) {
            log.error("获取任务调度器异常：",e);
        }
    }

    /**
     * 注册cron任务
     * 2023/11/6 0006 11:59
     * @author fulin-peng
     */
    public ScheduledFuture<?> registerCron(Runnable runnable,String cronName,String cronExpression){
        ScheduledFuture<?> schedule = taskScheduler.schedule(runnable, new CronTrigger(cronExpression));
        scheduledFutureMap.put(cronName,schedule);
        log.debug("注册cron任务:{}/{}",cronName,cronExpression);
        return schedule;
    }

    /**
     * 获取调度任务
     * 2023/11/6 0006 12:03
     * @author fulin-peng
     */
    public ScheduledFuture<?> getScheduled(String scheduledName){
        return scheduledFutureMap.get(scheduledName);
    }

    /**
     * 关闭调度任务
     * 2023/11/6 0006 12:08
     * @author fulin-peng
     */
    public void closeScheduled(String scheduledName){
        ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(scheduledName);
        if(scheduledFuture!=null && !scheduledFuture.isCancelled()){
            scheduledFuture.cancel(true);
            scheduledFutureMap.remove(scheduledName);
            log.debug("移除调度任务:{}",scheduledName);
        }
    }
}