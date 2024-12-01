package web.DAO

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import web.DTO.Comment;

public class CommentDAO { // 복붙
	Connection conn = null;
	PreparedStatement pstmt = null;
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/spring4fs";

	// 연결인데 복사 붙여넣기 했습니다.
    private void connect() {
        try {
            Class.forName(jdbc_Driver);
            conn = DriverManager.getConnection(jdbc_url, "spring4", "spring4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        String sql = "INSERT INTO recipe_comment (recipe_id, user_id, content) VALUES (?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, comment.getRecipeId());
            pstmt.setString(2, comment.getUserId());
            pstmt.setString(3, comment.getContent());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    // 댓글 삭제
    public void deleteComment(long commentId) {
        connect();
        String sql = "DELETE FROM recipe_comment WHERE comment_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, commentId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    // 댓글 수정
    public void updateComment(Comment comment) {
        connect();
        String sql = "UPDATE recipe_comment SET content = ? WHERE comment_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, comment.getContent());
            pstmt.setLong(2, comment.getCommentId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    // 댓글 ID로 댓글 가져오기 , 아마 recipe_id에 저장된 comment_id들을 불러올듯 함
    public Comment getCommentById(long commentId) {
        connect();
        String sql = "SELECT * FROM recipe_comment WHERE comment_id = ?";
        Comment comment = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, commentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                comment = new Comment();
                comment.setCommentId(rs.getLong("comment_id"));
                comment.setRecipeId(rs.getLong("recipe_id"));
                comment.setUserId(rs.getString("user_id"));
                comment.setContent(rs.getString("content"));
                comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return comment;
    }

    // 특정 사용자가 작성한 댓글 목록 가져오기 
    public List<Comment> getCommentsByUserId(String userId) {
        connect();
        String sql = "SELECT * FROM recipe_comment WHERE user_id = ? ORDER BY created_at DESC";
        List<Comment> comments = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getLong("comment_id"));
                comment.setRecipeId(rs.getLong("recipe_id"));
                comment.setUserId(rs.getString("user_id"));
                comment.setContent(rs.getString("content"));
                comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                comments.add(comment);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return comments;
    }
}

