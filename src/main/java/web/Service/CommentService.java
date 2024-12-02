package web.Service;

import web.DAO.CommentDAO;
import web.DTO.Comment;
import java.util.List;

public class CommentService {
    private CommentDAO commentDAO;

    public CommentService() {
        commentDAO = new CommentDAO();
    }

    public void addComment(Comment comment) {
        commentDAO.addComment(comment);
    }

    public List<Comment> getCommentsByRecipeID(int recipeID) {
        return commentDAO.getCommentsByRecipeID(recipeID);
    }

    public void deleteComment(int commentID) {
        commentDAO.deleteComment(commentID);
    }
    
    public Comment getCommentById(int commentID) {
        return commentDAO.getCommentById(commentID);
    }
}
