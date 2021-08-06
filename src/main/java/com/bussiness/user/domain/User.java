package com.bussiness.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private int id;
    private String username;
    private String password;
    private String nickname;
}
