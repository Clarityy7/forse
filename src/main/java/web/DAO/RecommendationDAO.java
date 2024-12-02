package web.DAO;

import java.sql.*;
import web.Util.DatabaseUtil;

public class RecommendationDAO {

    // 추천 누르면 발생 
    public boolean addRecommendation(int recipeID, String userID) {
        String sql = "INSERT INTO recipe_recommendation (recipeID, userID) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, recipeID);
            pstmt.setString(2, userID);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                // 중복 추천인 경우 처리
                return false;
            }
            e.printStackTrace();
            return false;
        }
    }

    // 추천 수 가져오기
    public int getRecommendationCount(int recipeID) {
        String sql = "SELECT COUNT(*) FROM recipe_recommendation WHERE recipeID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, recipeID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public boolean hasAlreadyRecommended(int recipeID, String userID) {
        String sql = "SELECT COUNT(*) FROM recommendation WHERE recipeID = ? AND userID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recipeID);
            pstmt.setString(2, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // 추천이 이미 존재하면 true 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
