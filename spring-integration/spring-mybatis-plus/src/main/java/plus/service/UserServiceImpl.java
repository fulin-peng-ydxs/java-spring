package plus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.dao.UserMapper;
import plus.entity.User;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService { }