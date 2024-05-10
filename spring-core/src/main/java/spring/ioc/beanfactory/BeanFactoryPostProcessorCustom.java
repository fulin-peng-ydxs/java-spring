package spring.ioc.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * bean�������ô�����
 * �����Զ����޸�Ӧ�ó��������ĵ�bean���壬���������ĵĵײ�bean������bean����ֵ��
 * �������ϵͳ����Ա���Զ��������ļ��ǳ����ã���Щ�����ļ�������Ӧ�ó��������������õ�bean���ԡ��йؽ��������������Ŀ��伴�ý��������
 * �����propertyresourcecconfigururer�������ʵ�֡�
 * BeanFactoryPostProcessor������bean���彻�����޸ģ���������beanʵ�����������������ܻᵼ�¹����beanʵ������Υ���������������벻���ĸ����á�
 * �����Ҫbeanʵ���������뿼��ʵ��BeanPostProcessor��

 * ע�᣺
 * ApplicationContext����bean�������Զ����BeanFactoryPostProcessor bean�����ڴ����κ�����bean֮ǰӦ�����ǡ�
 * BeanFactoryPostProcessorҲ����ͨ����̷�ʽע�ᵽConfigurableApplicationContext�С�

 * ����
 * ��ApplicationContext���Զ���⵽��BeanFactoryPostProcessor bean������org.springframework.core.priorityorordered��org.springframework.core.Ordered�����������
 * ����෴��BeanFactoryPostProcessor bean��ͨ��ConfigurableApplicationContext�Ա�̷�ʽע��ģ�
 * ��������ע���˳��Ӧ��;ͨ��ʵ��priityordered��Ordered�ӿڱ����κ��������彫�����ע��ĺ��������ԡ����⣬@Orderע�Ͳ��ᱻBeanFactoryPostProcessor bean�������ڡ�

 * 2024/5/10 00:04
 * @author pengshuaifeng
 */
@Component
public class BeanFactoryPostProcessorCustom implements BeanFactoryPostProcessor {


	/**
	 * �ڱ�׼��ʼ��֮���޸�Ӧ�ó��������ĵ��ڲ�bean���������е�bean���嶼�Ѽ��أ�����û��ʵ�����κ�bean��
	 * �������ǻ�������ԣ������ǶԼ��ڳ�ʼ����bean��
	 * 2024/5/10 00:27
	 * @author pengshuaifeng
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("BeanFactoryPostProcessor...postProcessBeanFactory...");
		int count = beanFactory.getBeanDefinitionCount();
		String[] names = beanFactory.getBeanDefinitionNames();
		System.out.println("��ǰBeanFactory����"+count+" ��Bean");
		System.out.println(Arrays.asList(names));
	}

}
