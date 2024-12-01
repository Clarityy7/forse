package web.DAO;

import java.sql.*;
import web.DTO.Like;

public class LikeDAO { // 복붙
	Connection conn = null;
	PreparedStatement pstmt = null;
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/spring4fs";

    // 데이터베이스 연결 , 복붙했어요.
    private void connect() {
        try {
            Class.forName(jdbc_Driver);
            conn = DriverManager.getConnection(jdbc_url, "spring4", "spring4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 연결 해제
    private void disconnect() {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    // 좋아요를 누른 경우
    public void addLike(long recipeId, String userId) {
        connect();
        String sql = "INSERT INTO recipe_like (recipe_id, user_id) VALUES (?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, recipeId);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
    
    // 사용자가 해당 레시피에 좋아요를 눌렀는지 확인
    public boolean hasLiked(long recipeId, String userId) {
        connect();
        String sql = "SELECT 1 FROM recipe_like WHERE recipe_id = ? AND user_id = ?";
        boolean hasLiked = false;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, recipeId);
            pstmt.setString(2, userId);
            ResultSet rs = pstmt.executeQuery(); 
            if (rs.next()) {
                hasLiked = true; // 안눌렀으면 false임
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return hasLiked;
    }
    
    // 레시피가 받은 좋아요 갯수 확인
    public int getLikeCount(long recipeId) {
        connect();
        String sql = "SELECT COUNT(*) FROM recipe_like WHERE recipe_id = ?";
        int likeCount = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, recipeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                likeCount = rs.getInt(1); // 좋아요 개수 가져오는 부분
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return likeCount;
    }
    
    // 좋아요 취소하기
    public void removeLike(long recipeId, String userId) {
        connect();
        String sql = "DELETE FROM recipe_like WHERE recipe_id = ? AND user_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, recipeId);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
       
    
}
