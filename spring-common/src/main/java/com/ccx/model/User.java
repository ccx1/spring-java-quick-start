package com.ccx.model;

import lombok.Data;

import java.util.List;

@Data
public class User {

    private long id;

    private String username;

    private String password;

    private String token;

    private String registerId;

    private String userId;

    private List<User> userList;


}
