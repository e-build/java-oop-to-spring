package com.business.recipe.dao;

import com.business.recipe.domain.Recipe;
import com.framework.core.db.JdbcTemplate;
import com.framework.core.db.PreparedStatementSetter;
import com.framework.core.new_mvc.annotation.Repository;
import com.framework.utils.DateUtils;

import java.sql.*;
import java.util.List;

@Repository
public class RecipeDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public Recipe selectRecipeById(int id){
        return jdbcTemplate.queryForObject("SELECT * FROM RECIPES WHERE ID = ?", this::createRecipeFromResultSet, id);
    }

    public List<Recipe> selectRecipeList(){
        return jdbcTemplate.query("SELECT * FROM RECIPES", this::createRecipeFromResultSet);
    }

    public int insertOne(Recipe recipe){
        String sql = "INSERT INTO RECIPES (NAME, CONTENTS, CATEGORY, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, recipe.getName());
                pstmt.setString(2, recipe.getContents());
                pstmt.setString(3, recipe.getCategory());
                pstmt.setInt(4, recipe.getCreatedBy());
                pstmt.setTimestamp(5, DateUtils.LDTToTimestamp(recipe.getCreatedAt()));
                pstmt.setInt(6, recipe.getUpdateBy());
                pstmt.setTimestamp(7, DateUtils.LDTToTimestamp(recipe.getUpdateAt()));
            }
        };
        return jdbcTemplate.update(sql, pss);
    }

    private Recipe createRecipeFromResultSet(ResultSet rs) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setId(rs.getInt(1));
        recipe.setName(rs.getString(2));
        recipe.setContents(rs.getString(3));
        recipe.setCategory(rs.getString(4));
        recipe.setCreatedBy(rs.getInt(5));
        recipe.setCreatedAt(DateUtils.TimestampToLDT(rs.getTimestamp(6)));
        recipe.setUpdateBy(rs.getInt(7));
        recipe.setUpdateAt(DateUtils.TimestampToLDT(rs.getTimestamp(8)));
        return recipe;
    }




}
