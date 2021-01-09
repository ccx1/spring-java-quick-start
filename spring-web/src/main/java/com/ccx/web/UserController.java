package com.ccx.web;


import com.ccx.model.User;
import com.ccx.result.ResultEntity;
import com.ccx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private IUserService mUserService;

    @PostMapping("/login")
    public ResultEntity<String> login(@RequestBody User user){
        return ResultEntity.ok(mUserService.login(user));
    }


    @PostMapping("/register")
    public ResultEntity<String> register(@RequestBody User user){
        return ResultEntity.ok(mUserService.register(user));
    }


}
