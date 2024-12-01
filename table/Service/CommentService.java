package web.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import web.DAO.CommentDAO;
import web.DTO.Comment;

public class CommentService {
    private CommentDAO dao;

    public CommentService() {
        this.dao = new CommentDAO();
    }

    // 특정 레시피에 달린 댓글 목록 가져오기
    public List<Comment> getCommentsByRecipeId(long recipeId) {
        return dao.getCommentsByRecipeId(recipeId);
    }

    // 댓글 추가 처리
    public HashMap<String, String> addComment(long recipeId, String userId, String content) {
        HashMap<String, String> errors = new HashMap<>();

        if (content == null || content.trim().isEmpty()) {
            errors.put("content", "댓글 내용을 입력해주세요.");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        Comment comment = new Comment();
        comment.setRecipeId(recipeId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        // 디버깅 로그
        System.out.println("댓글 추가 - Recipe ID: " + comment.getRecipeId() +
                           ", User ID: " + comment.getUserId() +
                           ", Content: " + comment.getContent());
        dao.addComment(comment);

        return errors;
    }

    // 댓글 삭제 처리
    public HashMap<String, String> deleteComment(long commentId, String userId) {
        HashMap<String, String> errors = new HashMap<>();
        Comment comment = dao.getCommentById(commentId);

        if (comment == null) {
            errors.put("not_found", "댓글을 찾을 수 없습니다.");
            return errors;
        }

        if (!comment.getUserId().equals(userId)) {
            errors.put("authorization", "삭제 권한이 없습니다.");
            return errors;
        }

        dao.deleteComment(commentId);
        return errors;
    }

    // 댓글 총 개수 가져오기
    public int getTotalCommentCountByRecipeId(long recipeId) {
        return dao.getCommentCountByRecipeId(recipeId);
    }
}

