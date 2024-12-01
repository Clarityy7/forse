package web.Controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import web.Service.CommentService;
import web.DAO.CommentDAO;
import web.DTO.Comment;
import web.DTO.User;     // import 이것들 맞나요?

@WebServlet("/comment/*")
public class CommentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CommentService commentService;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentController() {
        this.commentService = new CommentService();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }
    
    private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String action = request.getPathInfo();
        System.out.println("CommentController action: " + action);

        if (action == null || action.equals("/add.do")) {
        	// 댓글 추가 폼
            addComment(request, response);
        } else if (action.equals("/delete.do")) {
        	// 댓글 삭제 처리
            deleteComment(request, response);
        } else if (action.equals("/update.do")) {
        	// 댓글 수정 처리
            updateComment(request, response);
        } else if (action.equals("/userComments.do")) {
        	// 유저 댓글 조회 처리
            getUserComments(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    
    protected void forwardReq(HttpServletRequest request, HttpServletResponse response, String nextPage) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	} // 클라이언트 요청 전달용
    
    // 댓글 작성하기
    private void addComment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false); 
        if (session == null || session.getAttribute("user") == null) { // 세션 없으면 로그인 안함
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }
        User user = (User) session.getAttribute("user"); // 사용자 정보 가져오기
        long recipeId = Long.parseLong(request.getParameter("recipeId")); // 레시피 ID 가져와서 변환용
        String content = request.getParameter("content"); // 댓글 내용

        HashMap<String, String> errors = commentService.addComment(recipeId, user.getId(), content); 

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            forwardReq(request, response, "/webjsp/commentAdd.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/recipe/view.do?recipeID=" + recipeId); // 댓글 추가 성공
        }
    }
    
    
    // 댓글 삭제
    private void deleteComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }

        User user = (User) session.getAttribute("user");
        long commentId = Long.parseLong(request.getParameter("commentId"));

        HashMap<String, String> errors = commentService.deleteComment(commentId, user.getId());

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            response.sendRedirect(request.getHeader("Referer")); // 에러 발생 시 이전 페이지로 리다이렉트
        } else {
            response.sendRedirect(request.getHeader("Referer")); // 성공 시 이전 페이지로 리다이렉트
        }
    }
    
    
    
    // 댓글 수정
    private void updateComment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }

        User user = (User) session.getAttribute("user");
        long commentId = Long.parseLong(request.getParameter("commentId"));
        String content = request.getParameter("content");

        HashMap<String, String> errors = commentService.updateComment(commentId, user.getId(), content);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            forwardReq(request, response, "/webjsp/commentEdit.jsp");
        } else {
            response.sendRedirect(request.getHeader("Referer")); // 성공 시 이전 페이지로 리다이렉트
        }
    }
    
    // 특정 사용자가 작성한 댓글 목록 조회
    private void getUserComments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }

        User user = (User) session.getAttribute("user");
        List<Comment> comments = commentService.getCommentsByUserId(user.getId());

        request.setAttribute("comments", comments); // request 객체에 속성 저장 후 출력용
        forwardReq(request, response, "/webjsp/userComments.jsp"); // 전달
    }
}
    
   