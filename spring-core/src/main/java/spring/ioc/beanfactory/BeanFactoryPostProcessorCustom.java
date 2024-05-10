package spring.ioc.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * bean工厂后置处理器
 * 允许自定义修改应用程序上下文的bean定义，调整上下文的底层bean工厂的bean属性值。
 * 对于针对系统管理员的自定义配置文件非常有用，这些配置文件覆盖在应用程序上下文中配置的bean属性。有关解决此类配置需求的开箱即用解决方案，
 * 请参阅propertyresourcecconfigururer及其具体实现。
 * BeanFactoryPostProcessor可以与bean定义交互和修改，但不能与bean实例交互。这样做可能会导致过早的bean实例化，违反容器并导致意想不到的副作用。
 * 如果需要bean实例交互，请考虑实现BeanPostProcessor。

 * 注册：
 * ApplicationContext在其bean定义中自动检测BeanFactoryPostProcessor bean，并在创建任何其他bean之前应用它们。
 * BeanFactoryPostProcessor也可以通过编程方式注册到ConfigurableApplicationContext中。

 * 处理：
 * 在ApplicationContext中自动检测到的BeanFactoryPostProcessor bean将根据org.springframework.core.priorityorordered和org.springframework.core.Ordered语义进行排序。
 * 与此相反，BeanFactoryPostProcessor bean是通过ConfigurableApplicationContext以编程方式注册的，
 * 它将按照注册的顺序应用;通过实现priityordered或Ordered接口表达的任何排序语义将被编程注册的后处理器忽略。此外，@Order注释不会被BeanFactoryPostProcessor bean考虑在内。

 * 2024/5/10 00:04
 * @author pengshuaifeng
 */
@Component
public class BeanFactoryPostProcessorCustom implements BeanFactoryPostProcessor {


	/**
	 * 在标准初始化之后修改应用程序上下文的内部bean工厂。所有的bean定义都已加载，但还没有实例化任何bean。
	 * 这允许覆盖或添加属性，甚至是对急于初始化的bean。
	 * 2024/5/10 00:27
	 * @author pengshuaifeng
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("BeanFactoryPostProcessor...postProcessBeanFactory...");
		int count = beanFactory.getBeanDefinitionCount();
		String[] names = beanFactory.getBeanDefinitionNames();
		System.out.println("当前BeanFactory中有"+count+" 个Bean");
		System.out.println(Arrays.asList(names));
	}

}
