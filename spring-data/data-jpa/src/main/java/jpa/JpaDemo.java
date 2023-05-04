package jpa;

import jpa.config.JpaConfig;
import jpa.entity.User;
import jpa.repository.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class JpaDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        List<User> userList = userRepository.findAll();
        System.out.println(userList);
        User userById = userRepository.getUserById(1);
        System.out.println(userById);
    }
}
