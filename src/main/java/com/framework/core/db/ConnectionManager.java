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
            pstmt = conn.prepareStatement("INSERT INTO USERS VALUES('1', 'e-build@gmail.com', '1234qwer', 'e-build')");

            int insertRowCnt = pstmt.executeUpdate();
            if (insertRowCnt == 1)
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
                "    id varchar(12) NOT NULL,\n" +
                "    username varchar(50) NOT NULL,\n" +
                "    password varchar(50) NOT NULL,\n" +
                "    nickname varchar(50) NOT NULL,\n" +
                "    PRIMARY KEY (id)\n" +
                ")";
    }


    private static String getCreateRecipesTableSQL(){
        return "CREATE TABLE RECIPES(\n" +
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
