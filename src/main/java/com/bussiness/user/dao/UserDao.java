package com.bussiness.user.dao;

import com.bussiness.user.domain.User;
import com.framework.core.db.ConnectionManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UserDao {

    Logger log = LoggerFactory.getLogger(UserDao.class);

    public void insertUser(User user){
        PreparedStatement pstmt = null;
        Connection conn = ConnectionManager.getConnection();
        try {
            pstmt = conn.prepareStatement("INSERT INTO USERS(USERNAME, PASSWORD, NICKNAME) VALUES("+user.getUsername()+","+user.getPassword()+","+user.getNickname()+")");
            int insertRowCnt = pstmt.executeUpdate();
            if (insertRowCnt == 1)
                log.info("USER INSERT SUCCESS");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User selectUserByUsername(String username){
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = null;
        User user = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM USERS WHERE USERNAME = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
                user = createUserFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public User selectUserById(String id){
        Connection conn = ConnectionManager.getConnection();
        Statement stmt = null;
        User user = null;
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM USERS WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next())
                user = createUserFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        return new User( rs.getInt("ID"),
                trim(rs.getString("USERNAME")),
                trim(rs.getString("PASSWORD")),
                trim(rs.getString("NICKNAME"))
        );
    }

    private String trim(String value){
        return StringUtils.trim(value);
    }
}
