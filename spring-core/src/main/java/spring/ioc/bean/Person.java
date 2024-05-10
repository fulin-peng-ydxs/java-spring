package spring.ioc.bean;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: 测试bean
 * @date 2022/4/5 13:03
 */
@Getter
public class Person {

    @Autowired(required = false)
    private Cat cat;

    String name;
    String sex;
}
