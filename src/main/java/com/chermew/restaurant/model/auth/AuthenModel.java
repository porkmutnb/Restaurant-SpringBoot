package com.chermew.restaurant.model.auth;

import lombok.Data;

import java.util.Date;

@Data
public class AuthenModel {
    private String username;
    private String password;
    private String fullname;
    private Integer genderId;
    private Date birthdate;
    private String token;
}
