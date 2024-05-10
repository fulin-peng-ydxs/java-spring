package spring.ioc.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;


/**
 * bean定义后置处理器
 * 对标准BeanFactoryPostProcessor SPI的扩展，允许在常规BeanFactoryPostProcessor检测开始之前注册进一步的bean定义。
 * 特别地，BeanDefinitionRegistryPostProcessor可以注册更多的bean定义，这些定义反过来定义BeanFactoryPostProcessor实例。
 * 2024/5/10 00:18
 * @author pengshuaifeng
 */
@Component
public class BeanDefinitionRegistryCustom implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("BeanDefinitionRegistryPostProcessor...bean的数量："+beanFactory.getBeanDefinitionCount());
	}

	//BeanDefinitionRegistry：Bean定义信息的保存中心，
	// BeanFactory就是按照BeanDefinitionRegistry里面保存的每一个bean定义信息创建bean实例；
	/**
	 * 在标准初始化之后修改应用程序上下文的内部bean定义注册中心。将加载所有常规bean定义，但还没有实例化任何bean。
	 * 这允许在下一个后处理阶段开始之前添加进一步的bean定义。
	 * 2024/5/10 00:26
	 * @author pengshuaifeng
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("postProcessBeanDefinitionRegistry...bean的数量："+registry.getBeanDefinitionCount());
		//RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(String.class).getBeanDefinition();
		registry.registerBeanDefinition("hello", beanDefinition);
	}

}
