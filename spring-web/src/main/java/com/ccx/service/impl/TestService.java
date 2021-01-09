package com.ccx.service.impl;

import com.ccx.dao.UserMapper;
import com.ccx.model.User;
import com.ccx.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService implements ITestService {

    @Autowired
    private UserMapper userMapper;


    @Cacheable(value = "name2", key = "#root.method + '-' + #id")
    public String getRedisTestData(String id) {
        System.out.println("获取数据");
        return "dddddddddddd  ------- > " + System.currentTimeMillis();
    }

    @Cacheable(value = "name", key = "#root.method + '-' + #id")
    public String getRedisTestData2(String id) {
        System.out.println("获取数据");
        return "dddddddddddd  ------- > " + System.currentTimeMillis();
    }


    @Cacheable(value = "name3", key = "#root.method + '-' + #id")
    public String getRedisTestData3(String id) {
        System.out.println("获取数据");
        return "dddddddddddd  ------- > " + System.currentTimeMillis();
    }



    public List<User> getUser() {
        List<User> users = userMapper.queryUsers();
        System.out.println(users);
        return users;
    }
}
