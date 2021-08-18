package com.business.recipe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Recipe {

    private int id;
    private String name;
    private String contents;
    private String category;
    private int createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:dd")
    private LocalDateTime createdAt;
    private int updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:dd")
    private LocalDateTime updateAt;
}
