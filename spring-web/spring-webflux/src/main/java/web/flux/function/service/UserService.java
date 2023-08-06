package web.flux.function.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import web.flux.function.entity.User;

//用户操作接口
public interface UserService {
    //根据id查询用户
    Mono<User> getUserById(int id);

    //查询所有用户
    Flux<User> getAllUser();

    //添加用户
    Mono<Void> saveUserInfo(Mono<User> user);
}
