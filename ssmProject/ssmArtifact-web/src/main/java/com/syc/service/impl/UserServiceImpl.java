package com.syc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syc.dao.UserMapper;
import com.syc.model.User;
import com.syc.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    public String getHaha() {
        return "user service imp";
    }

    @Resource
    private UserMapper userMapper;

    Map<String,Long> map = new HashMap<String,Long>();

    @Override
    public User getUserById(Long id){
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
    public User getUserByModel(User user){
        return getUserById(1l);
    }
    public User login(String userName, String pass){
        return getUserById(1l);
    }
    public Set<String> findRoleByUserId(Long id){
        Set<String> set = new HashSet<String>();
        return set;
    }
    public Set<String> findPermissionByUserId(Long id){
        Set<String> set = new HashSet<String>();
        return set;
    }

    /**
     * 条件分页查询
     */
    public PageInfo<User> page(int pageNum, int pageSize, User model) {
        PageHelper.startPage(pageNum, pageSize);
        //List<User> list = userMapper.select(model);
        List<User> list = new ArrayList<User>();
        return new PageInfo<User>(list);
    }


    public List<User> findByPage(Long currPage,Long pageSize){
        map.put("currPage", currPage);
        map.put("pageSize", pageSize);
        //return userMapper.selectByPage(map);
        return new ArrayList<User>();
    }
}
