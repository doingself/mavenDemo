package com.syc.service.impl;

import com.syc.dao.UserDao;
import com.syc.dao.UserMapper;
import com.syc.model.User;
import com.syc.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    public String getHaha() {
        return "user service imp";
    }

//    @Resource
//    private UserDao userDao;

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserById(int id){
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
}
