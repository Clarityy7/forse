package web.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import web.DTO.Comment;

public class CommentDAO {
    private Connection conn;
    private PreparedStatement pstmt;

    // DB 연결
    private void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/spring4fs", "spring4", "spring4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DB 연결 해제
    private void disconnect() {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 댓글 추가
    public void addComment(Comment comment) {
        connect();
        String sql = "INSERT INTO comment (recipeID, userID, content) VALUES (?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, comment.getRecipeID());
            pstmt.setString(2, comment.getUserID());
            pstmt.setString(3, comment.getContent());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    // 댓글 조회
    public List<Comment> getCommentsByRecipeID(int recipeID) {
        connect();
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment WHERE recipeID = ? ORDER BY regdate DESC";
        try {
            pstmt = conn.prepareStatement(sql);
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return comments;
    }

    // 댓글 삭제
    public void deleteComment(int commentID) {
        connect();
        String sql = "DELETE FROM comment WHERE commentID = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commentID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
}
