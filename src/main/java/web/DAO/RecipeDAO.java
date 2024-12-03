package web.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import web.Util.DatabaseUtil;
import web.DTO.Recipe;

public class RecipeDAO {

    // disconnect 
    void disconnect(PreparedStatement pstmt, Connection conn) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 레시피 추가 메서드
    public void addRecipe(Recipe recipe) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO recipe (title, description, imagePath, userID, regdate) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, recipe.getTitle());
            pstmt.setString(2, recipe.getDescription());
            pstmt.setString(3, recipe.getImagePath());
            pstmt.setString(4, recipe.getUserID());
            pstmt.setTimestamp(5, Timestamp.valueOf(recipe.getRegdate()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(pstmt, conn);
        }
    }

    // 레시피 목록 가져오기 (페이징 처리)
    public List<Recipe> getRecipes(int page, int pageSize) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipe ORDER BY regdate DESC LIMIT ?, ?";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, (page - 1) * pageSize);
            pstmt.setInt(2, pageSize);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setRecipeID(rs.getInt("recipeID"));
                recipe.setTitle(rs.getString("title"));
                recipe.setDescription(rs.getString("description"));
                recipe.setImagePath(rs.getString("imagePath"));
                recipe.setUserID(rs.getString("userID"));
                recipe.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
                recipes.add(recipe);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(pstmt, conn);
        }
        return recipes;
    }

    // 레시피 총 개수 가져오기 (페이징을 위한)
    public int getRecipeCount() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int count = 0;
        String sql = "SELECT COUNT(*) FROM recipe";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(pstmt, conn);
        }
        return count;
    }

    // 레시피 상세 보기
    public Recipe getRecipeById(int recipeID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Recipe recipe = null;
        String sql = "SELECT * FROM recipe WHERE recipeID = ?";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, recipeID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                recipe = new Recipe();
                recipe.setRecipeID(rs.getInt("recipeID"));
                recipe.setTitle(rs.getString("title"));
                recipe.setDescription(rs.getString("description"));
                recipe.setImagePath(rs.getString("imagePath"));
                recipe.setUserID(rs.getString("userID"));
                recipe.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(pstmt, conn);
        }
        return recipe;
    }

    // 사용자 ID를 기준으로 해당 사용자가 작성한 레시피를 모두 가져오기
    public List<Recipe> getRecipesByUserId(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipe WHERE userID = ? ORDER BY regdate DESC";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setRecipeID(rs.getInt("recipeID"));
                recipe.setTitle(rs.getString("title"));
                recipe.setDescription(rs.getString("description"));
                recipe.setUserID(rs.getString("userID"));
                recipe.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
                recipes.add(recipe);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(pstmt, conn);
        }
        return recipes;
    }

    // 레시피 수정
    public void updateRecipe(Recipe recipe) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE recipe SET title = ?, description = ?, imagePath = ? WHERE recipeID = ?";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, recipe.getTitle());
            pstmt.setString(2, recipe.getDescription());
            pstmt.setString(3, recipe.getImagePath());
            pstmt.setInt(4, recipe.getRecipeID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(pstmt, conn);
        }
    }

    // 레시피 삭제
    public void deleteRecipe(int recipeID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM recipe WHERE recipeID = ?";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, recipeID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(pstmt, conn);
        }
    }
    
    //추가!! 모든 레시피를 조회하는 sql 쿼리 작성
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipe ORDER BY regdate DESC"; // 최신 순 정렬
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setRecipeID(rs.getInt("recipeID"));
                recipe.setTitle(rs.getString("title"));
                recipe.setDescription(rs.getString("description"));
                recipe.setImagePath(rs.getString("imagePath"));
                recipe.setUserID(rs.getString("userID"));
                recipe.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
                recipes.add(recipe);
            }
            rs.close();
            System.out.println("DAO에서 가져온 레시피 개수: " + recipes.size()); // 디버깅
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }




}
