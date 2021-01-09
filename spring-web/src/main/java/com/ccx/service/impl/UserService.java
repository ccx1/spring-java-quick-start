package com.ccx.service.impl;

import com.ccx.dao.UserMapper;
import com.ccx.enums.HttpStatus;
import com.ccx.model.User;
import com.ccx.result.CodeException;
import com.ccx.service.IUserService;
import com.ccx.utils.RedisUtils;
import com.ccx.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {


    @Autowired
    private UserMapper mUserMapper;

    @Autowired
    private RedisUtils mRedisUtils;

    public String login(User user) {
        User temp = queryUser(user);
        if (temp == null) {
            throw new CodeException(HttpStatus.USER_NOT_EXIST_FAIL);
        }
        String uuid = TextUtils.uuid();
        // 更新最后登录时间
        mUserMapper.updateLastLoginTime(temp.getUserId());

        mRedisUtils.set(uuid, temp);
        return uuid;
    }

    public String register(User user) {
        User temp = queryUser(user);
        if (temp != null) {
            throw new CodeException(HttpStatus.USER_EXIST_FAIL);
        }
        mUserMapper.registerNewUser(user);
        return login(user);
    }

    private User queryUser(User user) {
        return mUserMapper.queryUser(user);
    }


}
