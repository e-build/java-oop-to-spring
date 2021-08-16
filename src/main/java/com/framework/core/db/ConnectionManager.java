package com.framework.core.db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/jos-h2-db;AUTO_SERVER=TRUE";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PW = "";

    private static DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(DB_DRIVER);
        ds.setUrl(DB_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PW);
        return ds;
    }

    public static Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void executeInitialScript(){
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS USERS");
            stmt.executeUpdate("DROP TABLE IF EXISTS RECIPES");
            stmt.executeUpdate(getCreateUsersTableSQL());
            stmt.executeUpdate(getCreateRecipesTableSQL());
//            pstmt = conn.prepareStatement("INSERT INTO USERS VALUES('1', 'e-build@gmail.com', '1234qwer', 'e-build')");
            int insertRowCnt = 0;
            pstmt = conn.prepareStatement("INSERT INTO USERS(USERNAME, PASSWORD, NICKNAME) VALUES('e-build@gmail.com', '1234qwer', 'e-build')");
            insertRowCnt += pstmt.executeUpdate();
            pstmt = conn.prepareStatement("INSERT INTO RECIPES(NAME, CONTENTS, CATEGORY, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) VALUES('양평 해장국', '숙취가 심한 아침 양평 해장국 한숫가락은 얼마나 맛있을까?', '한식', 1, '2021-08-16 07:01:17', 1, '2021-08-16 07:01:17')");
            insertRowCnt += pstmt.executeUpdate();
            pstmt = conn.prepareStatement("INSERT INTO RECIPES(NAME, CONTENTS, CATEGORY, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) VALUES('봉골레 파스타', '저만 아는 이태원 한적한 골목의 맛있는 파스타 전문점 레시피를 소개합니다! ', '양식', 1, '2021-08-15 07:01:17', 1, '2021-08-15 07:01:17')");
            insertRowCnt += pstmt.executeUpdate();
            pstmt = conn.prepareStatement("INSERT INTO RECIPES(NAME, CONTENTS, CATEGORY, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) VALUES('굴 자장면', '왜 굴은 짬뽕에만 넣는다고 생각하세요? 자장면과 굴의 환상적인 조합을 소개해드립니다!', '한식', 1, '2021-08-14 07:01:17', 1, '2021-08-14 07:01:17')");
            insertRowCnt += pstmt.executeUpdate();

            if (insertRowCnt == 4)
                System.out.println("DATABASE INIT SUCCESS");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getCreateUsersTableSQL(){
        return "CREATE TABLE USERS (\n" +
                "    ID NUMBER AUTO_INCREMENT,\n" +
                "    USERNAME VARCHAR(50) NOT NULL,\n" +
                "    PASSWORD VARCHAR(50) NOT NULL,\n" +
                "    NICKNAME VARCHAR(50) NOT NULL,\n" +
                "    CONSTRAINT USER_PK PRIMARY KEY (ID)\n" +
                ")";
    }


    private static String getCreateRecipesTableSQL(){
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
