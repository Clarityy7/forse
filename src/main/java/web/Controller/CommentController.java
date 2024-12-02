package web.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.DTO.Comment;
import web.DTO.User;
import web.Service.CommentService;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Servlet implementation class CommentController
 */
@WebServlet("/comment/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private CommentService commentService;

    public CommentController() {
        commentService = new CommentService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }

    protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getPathInfo();
		System.out.println("CommentController action: " + action);

        if (action.equals("/add.do")) {
            addComment(request, response);
        } else if (action.equals("/delete.do")) {
            deleteComment(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
	
	// 댓글 추가 처리
    private void addComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }

        int recipeID = Integer.parseInt(request.getParameter("recipeID"));
        String content = request.getParameter("content");

        if (content == null || content.trim().isEmpty()) {
            // 에러 처리
            response.sendRedirect(request.getContextPath() + "/recipe/view.do?recipeID=" + recipeID);
            return;
        }

        Comment comment = new Comment();
        comment.setRecipeID(recipeID);
        comment.setUserID(user.getId());
        comment.setContent(content);
        comment.setRegdate(LocalDateTime.now());

        commentService.addComment(comment);

        response.sendRedirect(request.getContextPath() + "/recipe/view.do?recipeID=" + recipeID);
    }
	
	// 댓글 삭제 처리
    private void deleteComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 삭제 권한 확인 및 처리 로직 구현
    	HttpSession session = request.getSession(false);
    	User user = null;
    	if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }
        
        int recipeID = Integer.parseInt(request.getParameter("recipeID"));
        int commentID =Integer.parseInt(request.getParameter("commentID")); 
        		
        // 댓글 정보 가져오기
        Comment comment = commentService.getCommentById(commentID);
        if (comment == null) {
            // 댓글이 존재하지 않는 경우 처리
            response.sendRedirect(request.getContextPath() + "/recipe/view.do?recipeID=" + recipeID);
            return;
        }
        
        // 현재 사용자와 댓글 작성자 비교
        if (!user.getId().equals(comment.getUserID())) {
            // 권한 없음
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "댓글을 삭제할 권한이 없습니다.");
            return;
        }
        
        commentService.deleteComment(commentID);
        response.sendRedirect(request.getContextPath() + "/recipe/view.do?recipeID=" + recipeID);
    }

}
