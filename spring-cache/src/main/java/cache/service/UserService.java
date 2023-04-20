package cache.service;

import cache.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserService {

    private Map<Integer,User> result=new HashMap<>();

    /**声明缓存：将方法+参数为key，返回结果为值存储在名称为user的Cache中
     * 2023/4/19-0:39
     * @author pengfulin
    */
    @Cacheable("user")
    public User getUser(int id){
        System.out.println("查库操作");
        User user = result.get(id);
        user=user==null?new User(id,"小王八蛋",38):user;
        return user;
    }
}
