package web.Controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import web.Service.LikeService;
import web.DTO.User;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/like/*")
public class LikeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LikeService likeService;

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeController() {
        this.likeService = new LikeService();
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
        System.out.println("LikeController action: " + action);
    	// 위 복붙한 것들
        
        if (action == null || action.equals("/add.do")) {
            addLike(request, response);
        } else if (action.equals("/remove.do")) {
            removeLike(request, response);
        } else if (action.equals("/count.do")) {
            getLikeCount(request, response);
        }
    }
    
    
    
    
    
    private void forwardReq(HttpServletRequest request, HttpServletResponse response, String nextPage) throws ServletException, IOException {
        RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
        dispatch.forward(request, response);
    }
    
    // 좋아요 추가
    private void addLike(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	HttpSession session = request.getSession(false);
    	if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;                        
        }
    	User user = (User) session.getAttribute("user");
        long recipeId = Long.parseLong(request.getParameter("recipeId"));

        HashMap<String, String> errors = likeService.removeLike(recipeId, user.getId());
        
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            response.sendRedirect(request.getHeader("Referer"));
        } else {
            response.sendRedirect(request.getContextPath() + "/recipe/view.do?recipeID=" + recipeId);
        }
    
    }
    
    // 좋아요 취소
    private void removeLike(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	HttpSession session = request.getSession(false);
    	if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }
    	User user = (User) session.getAttribute("user");
        long recipeId = Long.parseLong(request.getParameter("recipeId"));

        HashMap<String, String> errors = likeService.removeLike(recipeId, user.getId());
        
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            response.sendRedirect(request.getHeader("Referer"));
        } else {
            response.sendRedirect(request.getContextPath() + "/recipe/view.do?recipeID=" + recipeId); // 레시피 댓글이니 레시피 보여주기
        }
    	
    }
    
    
    // 좋아요 갯수 카운트하기
    private void getLikeCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	long recipeId = Long.parseLong(request.getParameter("recipeId")); // 레시피 id 가져와서
        int likeCount = likeService.getLikeCount(recipeId); // 카운트 몇 개 인지 확인

        request.setAttribute("likeCount", likeCount);
        forwardReq(request, response, "/webjsp/recipeDetail.jsp");    	  	
    }
    
    
}

// handle에 좋아요 추가 , 삭제 , 개수 몇개인지 확인하는거 넣기