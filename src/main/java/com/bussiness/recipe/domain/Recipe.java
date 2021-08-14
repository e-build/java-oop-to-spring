package com.bussiness.recipe.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
public class Recipe {

    private int id;
    private String name;
    private String contents;
    private String category;
    private int createdBy;
    private LocalDateTime createdAt;
    private int updateBy;
    private LocalDateTime updateAt;
}
