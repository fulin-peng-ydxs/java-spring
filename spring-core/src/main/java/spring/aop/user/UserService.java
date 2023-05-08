package spring.aop.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void  userTest(String demo){
        System.out.println("用户服务方法测试"+demo);
    }

}
