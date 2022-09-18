package spring.ioc.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Component;

@Component
public class BeanDefinitionRegistryPostProcessor implements org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("BeanDefinitionRegistryPostProcessor...bean��������"+beanFactory.getBeanDefinitionCount());
	}

	//BeanDefinitionRegistry Bean������Ϣ�ı������ģ��Ժ�BeanFactory���ǰ���BeanDefinitionRegistry���汣���ÿһ��bean������Ϣ����beanʵ����
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("postProcessBeanDefinitionRegistry...bean��������"+registry.getBeanDefinitionCount());
		//RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(String.class).getBeanDefinition();
		registry.registerBeanDefinition("hello", beanDefinition);
	}

}
