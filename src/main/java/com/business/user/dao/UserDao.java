package com.business.user.dao;

import com.business.user.domain.User;
import com.framework.core.db.JdbcTemplate;
import com.framework.core.db.PreparedStatementSetter;
import com.framework.core.db.RowMapper;
import com.framework.core.new_mvc.annotation.Repository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

@Repository
public class UserDao {

    Logger log = LoggerFactory.getLogger(UserDao.class);
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public int insertUser(User user){
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
        };
        return jdbcTemplate.update("INSERT INTO USERS(USERNAME, PASSWORD, NICKNAME) VALUES(?,?,?)", pss);
    }

    public User selectUserByUsername(String username){
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, username);
        };
        RowMapper<User> rowMapper = this::createUserFromResultSet;
        return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE USERNAME = ?", pss, rowMapper);
    }

    public User selectUserById(int id){
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setInt(1, id);
        };
        RowMapper<User> rowMapper = this::createUserFromResultSet;
        return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE ID = ?", pss, rowMapper);
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
