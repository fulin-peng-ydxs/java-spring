package spring.ioc.bean.conditions;


import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * @author pengshuaifeng
 * 2024/5/10
 */
@Conditional(MyCondition.class)
@Component
public class ConditionBean {

}
