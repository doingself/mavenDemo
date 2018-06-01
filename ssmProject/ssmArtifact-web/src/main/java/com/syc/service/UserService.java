package com.syc.service;

import com.github.pagehelper.PageInfo;
import com.syc.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    public String getHaha();

    public User getUserById(Long id);
    public User getUserByModel(User user);
    public User login(String userName, String pass);
    public Set<String> findRoleByUserId(Long id);
    public Set<String> findPermissionByUserId(Long id);

    public PageInfo<User> page(int pageNum, int pageSize, User model);
    public List<User> findByPage(Long pageNum, Long pageSize);
 }
