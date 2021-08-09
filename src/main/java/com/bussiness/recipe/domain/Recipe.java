package com.bussiness.recipe.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Recipe {

    private String name;
    private String contents;
    private String category;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updateBy;
    private LocalDateTime updateAt;
}
