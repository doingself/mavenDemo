package com.syc.service.impl;

import com.syc.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    public String getHaha() {
        return "user service imp";
    }
}
