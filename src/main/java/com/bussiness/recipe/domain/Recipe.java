package com.bussiness.recipe.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Recipe {

    private String name;
    private String contents;
    private Integer createdBy;
    private LocalDateTime createdAt;
    private Integer updateBy;
    private LocalDateTime updateAt;
}
