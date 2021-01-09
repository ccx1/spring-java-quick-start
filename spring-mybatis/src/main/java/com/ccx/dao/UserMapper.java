package com.ccx.dao;

import com.ccx.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> queryUsers();

    User queryUser(User user);

    void registerNewUser(User user);

    void updateLastLoginTime(@Param("userId") String userId);
}
