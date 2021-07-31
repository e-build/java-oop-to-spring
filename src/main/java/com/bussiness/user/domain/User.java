package com.bussiness.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class User {

    private int id;
    private String username;
    private String password;
    private String nickname;
}
