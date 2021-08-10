package com.framework.core.db;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/jwp-basic;AUTO_SERVER=TRUE";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PW = "";

    public static DataSource getDataSource() {
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
        String DROP_USERS_TABLE_SQL = "DROP TABLE IF EXISTS USERS";
        String CREATE_USERS_TABLE_SQL = "CREATE TABLE USERS (\n" +
                                        "    id varchar(12) NOT NULL,\n" +
                                        "    username varchar(50) NOT NULL,\n" +
                                        "    password varchar(50) NOT NULL,\n" +
                                        "    nickname varchar(50) NOT NULL,\n" +
                                        "    PRIMARY KEY (id)\n" +
                                        ")";
        String INSERT_USERS_DEFAULT_SQL = "INSERT INTO USERS VALUES('1', 'e-build@gmail.com', '1234qwer', 'e-build')";
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null; // SQL 문을 데이터베이스에 보내기위한 객체
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(DROP_USERS_TABLE_SQL);
            stmt.executeUpdate(CREATE_USERS_TABLE_SQL);
            pstmt = conn.prepareStatement(INSERT_USERS_DEFAULT_SQL);

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
}
