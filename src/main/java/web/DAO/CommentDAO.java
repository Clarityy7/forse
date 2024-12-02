package web.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import web.DTO.Comment;
import web.Util.DatabaseUtil;

public class CommentDAO {
    private Connection conn;
    private PreparedStatement pstmt;

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
    
    // 댓글 추가 메서드
    public void addComment(Comment comment) {
        String sql = "INSERT INTO comment (recipeID, userID, content, regdate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, comment.getRecipeID());
            pstmt.setString(2, comment.getUserID());
            pstmt.setString(3, comment.getContent());
            pstmt.setTimestamp(4, Timestamp.valueOf(comment.getRegdate()));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 특정 레시피의 댓글 목록 가져오기
    public List<Comment> getCommentsByRecipeID(int recipeID) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment WHERE recipeID = ? ORDER BY regdate ASC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, recipeID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentID(rs.getInt("commentID"));
                comment.setRecipeID(rs.getInt("recipeID"));
                comment.setUserID(rs.getString("userID"));
                comment.setContent(rs.getString("content"));
                comment.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
                comments.add(comment);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    // 댓글 삭제 메서드
    public void deleteComment(int commentID) {
        String sql = "DELETE FROM comment WHERE commentID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, commentID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 특정 댓글 
    public Comment getCommentById(int commentID) {
        String sql = "SELECT * FROM comment WHERE commentID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, commentID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentID(rs.getInt("commentID"));
                comment.setRecipeID(rs.getInt("recipeID"));
                comment.setUserID(rs.getString("userID"));
                comment.setContent(rs.getString("content"));
                comment.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
                return comment;
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
}
