package com.ccx.service;

import com.ccx.model.User;

public interface IUserService {


    String login(User user);

    String register(User user);
}
