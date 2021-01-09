package com.ccx.service;

import com.ccx.model.User;

import java.util.List;

public interface ITestService {
    List<User> getUser();

    String getRedisTestData(String id);
}
