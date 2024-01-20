package spring.utils;


import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import java.util.Comparator;
import java.util.List;

/**
 * 排序工具类
 * @author pengshuaifeng
 * 2024/1/20
 */
public class OrderedSortUtils {

    /**
     * 根据Ordered接口以及@Order和@Priority注释实现排序
     * <p>优先级：Ordered->Order->Priority，默认使用容器内置的OrderComparator处理</p>
     * 2024/1/20 23:07
     * @author pengshuaifeng
     */
    public static void sortPostProcessors(List<?> list, ConfigurableListableBeanFactory beanFactory) {
        Comparator<Object> comparatorToUse = null;
        if (beanFactory instanceof DefaultListableBeanFactory) {
            comparatorToUse = ((DefaultListableBeanFactory) beanFactory).getDependencyComparator();
        }
        if (comparatorToUse == null) {
            comparatorToUse = AnnotationAwareOrderComparator.INSTANCE;
        }
        list.sort(comparatorToUse);
    }

    /**
     * 根据Ordered接口以及@Order和@Priority注释实现排序
     * <p>优先级：Ordered->Order->Priority</p>
     * 2024/1/20 23:07
     * @author pengshuaifeng
     */
    public static void sortPostProcessors(List<?> list) {
        //AnnotationAwareOrderComparator是OrderComparator的扩展，
        // 它支持Spring的org.springframework.core.Ordered接口以及@Order和@Priority注释，
        // 由Ordered实例提供的order值覆盖静态定义的注释值(如果有的话)。
        Comparator<Object> comparatorToUse = AnnotationAwareOrderComparator.INSTANCE;
        list.sort(comparatorToUse);
    }
}
