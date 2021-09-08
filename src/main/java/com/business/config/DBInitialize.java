package com.business.config;

import com.framework.core.db.JdbcTemplate;
import com.framework.core.di.annotation.Component;
import com.framework.core.di.annotation.Inject;

@Component
public class DBInitialize {

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public DBInitialize(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        executeInitialScript();
    }

    public void executeInitialScript(){
        jdbcTemplate.update("DROP TABLE IF EXISTS USERS");
        jdbcTemplate.update("DROP TABLE IF EXISTS RECIPES");
        jdbcTemplate.update(getCreateUsersTableSQL());
        jdbcTemplate.update(getCreateRecipesTableSQL());

        int insertRowCnt = 0;
        insertRowCnt += jdbcTemplate.update("INSERT INTO USERS(USERNAME, PASSWORD, NICKNAME) VALUES('e-build@gmail.com', '1234qwer', 'e-build')");
        insertRowCnt += jdbcTemplate.update("INSERT INTO RECIPES(NAME, CONTENTS, CATEGORY, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) VALUES('양평 해장국', '숙취가 심한 아침 양평 해장국 한숫가락은 얼마나 맛있을까?', '한식', 1, '2021-08-16 07:01:17', 1, '2021-08-16 07:01:17')");
        insertRowCnt += jdbcTemplate.update("INSERT INTO RECIPES(NAME, CONTENTS, CATEGORY, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) VALUES('봉골레 파스타', '저만 아는 이태원 한적한 골목의 맛있는 파스타 전문점 레시피를 소개합니다! ', '양식', 1, '2021-08-15 07:01:17', 1, '2021-08-15 07:01:17')");
        insertRowCnt +=  jdbcTemplate.update("INSERT INTO RECIPES(NAME, CONTENTS, CATEGORY, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) VALUES('굴 자장면', '왜 굴은 짬뽕에만 넣는다고 생각하세요? 자장면과 굴의 환상적인 조합을 소개해드립니다!', '한식', 1, '2021-08-14 07:01:17', 1, '2021-08-14 07:01:17')");
        if (insertRowCnt == 4)
            System.out.println("DATABASE INIT SUCCESS");
    }

    private String getCreateUsersTableSQL(){
        return "CREATE TABLE USERS (\n" +
                "    ID NUMBER AUTO_INCREMENT,\n" +
                "    USERNAME VARCHAR(50) NOT NULL,\n" +
                "    PASSWORD VARCHAR(50) NOT NULL,\n" +
                "    NICKNAME VARCHAR(50) NOT NULL,\n" +
                "    CONSTRAINT USER_PK PRIMARY KEY (ID)\n" +
                ")";
    }


    private String getCreateRecipesTableSQL(){
        return "CREATE TABLE RECIPES (\n" +
                "    ID          NUMBER AUTO_INCREMENT,\n" +
                "    NAME        VARCHAR(50)    NOT NULL,\n" +
                "    CONTENTS    VARCHAR(10000) NOT NULL,\n" +
                "    CATEGORY    VARCHAR(10)    NOT NULL,\n" +
                "    CREATED_BY   NUMBER         NOT NULL,\n" +
                "    CREATED_AT   TIMESTAMP      NOT NULL,\n" +
                "    UPDATED_BY    NUMBER         NOT NULL,\n" +
                "    UPDATED_AT    TIMESTAMP      NOT NULL,\n" +
                "    CONSTRAINT RECIPE_PK PRIMARY KEY (ID)\n" +
                ")";
    }
}
