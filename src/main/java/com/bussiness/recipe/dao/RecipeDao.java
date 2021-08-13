package com.bussiness.recipe.dao;

import com.bussiness.recipe.domain.Recipe;
import com.framework.core.db.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {

    public Recipe selectRecipeById(int id){
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = null;
        Recipe recipe = null;
        try{
            pstmt = conn.prepareStatement("SELECT * FROM RECIPES WHERE ID = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while ( rs.next() )
                recipe = createRecipeFromResultSet(rs);
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
        return recipe;
    }

    public List<Recipe> selectRecipeList(){
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = null;
        List<Recipe> recipeList = new ArrayList<Recipe>();
        try{
            pstmt = conn.prepareStatement("SELECT * FROM RECIPES");
            ResultSet rs = pstmt.executeQuery();

            while ( rs.next() )
                recipeList.add(createRecipeFromResultSet(rs));
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
        return recipeList;
    }

    public boolean insertOne(Recipe recipe){
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = null;
        boolean result = false;
        String sql = "INSERT INTO RECIPES (NAME, CONTENTS, CATEGORY, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try{
            pstmt = conn.prepareStatement(sql );
            pstmt.setString(1, recipe.getName());
            pstmt.setString(2, recipe.getContents());
            pstmt.setString(3, recipe.getCategory());
            pstmt.setTimestamp(4, recipe.getCreatedAt());
            pstmt.setInt(5, recipe.getCreatedBy());
            pstmt.setTimestamp(6, recipe.getUpdateAt());
            pstmt.setInt(7, recipe.getUpdateBy());

            int insertCount = pstmt.executeUpdate();
            if (insertCount == 1)
                result = true;

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
        return result;
    }

    private Recipe createRecipeFromResultSet(ResultSet rs) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setId(rs.getInt(1));
        recipe.setName(rs.getString(2));
        recipe.setContents(rs.getString(3));
        recipe.setCategory(rs.getString(4));
        recipe.setCreatedBy(rs.getInt(5));
        recipe.setCreatedAt(rs.getTimestamp(6));
        recipe.setUpdateBy(rs.getInt(7));
        recipe.setUpdateAt(rs.getTimestamp(8));

        return recipe;
    }




}
