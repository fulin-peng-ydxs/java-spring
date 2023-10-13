package schedule.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.concurrent.ScheduledFuture;
/**
 * TaskScheduler管理器
 * author: pengshuaifeng
 * 2023/10/14
 */
@Component
public class TaskSchedulerHolder {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 获取TaskScheduler
     * 2023/10/14 00:43
     * @author pengshuaifeng
     */
    public TaskScheduler getSimpleTaskScheduler(){
        try {
            ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor = applicationContext.getBean(ScheduledAnnotationBeanPostProcessor.class);
            Field registrarField = scheduledAnnotationBeanPostProcessor.getClass()
                    .getDeclaredField("registrar");
            registrarField.setAccessible(true);
            Object registrar = registrarField.get(scheduledAnnotationBeanPostProcessor);
            Field taskSchedulerField = registrar.getClass()
                    .getDeclaredField("taskScheduler");
            taskSchedulerField.setAccessible(true);
            return (TaskScheduler)taskSchedulerField.get(registrar);
        } catch (Exception e) {
            throw new RuntimeException("获取TaskScheduler异常",e);
        }
    }

    /**
     * 加入调度任务
     * 2023/10/14 00:55
     * @author pengshuaifeng
     */
    public static ScheduledFuture<?> addCronScheduleTask(TaskScheduler taskScheduler,Runnable runnable,String cronExpression){
        return taskScheduler.schedule(runnable, new CronTrigger(cronExpression));
    }

    /**
     * 移除调度任务
     * 2023/10/14 00:56
     * @author pengshuaifeng
     */
    public static void removeScheduleTask( ScheduledFuture<?> scheduledFuture){
        if(scheduledFuture.isCancelled()){
            scheduledFuture.cancel(true);
        }
    }
}
