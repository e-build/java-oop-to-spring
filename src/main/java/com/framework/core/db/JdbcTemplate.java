package com.framework.core.db;

import com.framework.core.db.exception.DataAccessException;
import com.framework.core.di.annotation.Component;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JdbcTemplate {

    public <T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException{
        List<T> resultList = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pss.setValues(pstmt);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
                resultList.add(rowMapper.mapRow(rs));
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return resultList;
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object...  params) throws DataAccessException{
        List<T> resultList = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            for ( int i = 0; i < params.length ; i++ )
                pstmt.setObject(i+1, params[i]);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
                resultList.add(rowMapper.mapRow(rs));
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return resultList;
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper){
        List<T> list = query(sql, pss, rowMapper);
        if ( list.isEmpty() )
            return null;
        return list.get(0);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... params){
        List<T> list = query(sql, rowMapper, params);
        if ( list.isEmpty() )
            return null;
        return list.get(0);
    }

    public int update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        int updateCnt = 0;
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pss.setValues(pstmt);
            updateCnt = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return updateCnt;
    }

    public int update(String sql, Object... params) throws DataAccessException {
        int updateCnt = 0;
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            for ( int i = 0; i < params.length ; i++ )
                pstmt.setObject(i, params[i]);

            updateCnt = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return updateCnt;
    }

}
