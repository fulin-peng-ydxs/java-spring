package spring.ioc.bean.factory;

import org.springframework.beans.factory.FactoryBean;
import spring.ioc.bean.Person;

/**
 * FactoryBean—› æ
 *
 * @author PengFuLin
 * 2022/9/17 11:13
 */
public class FactoryBeanDemo implements FactoryBean<Person>
{
    @Override
    public Person getObject() throws Exception {
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }
}
