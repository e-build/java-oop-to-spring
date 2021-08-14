package com.bussiness.recipe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:dd")
    private LocalDateTime createdAt;
    private int updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:dd")
    private LocalDateTime updateAt;
}
