package web.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.DAO.UserDAO;
import web.DTO.Recipe;
import web.DTO.User;
import web.Service.RecipeService;
import web.Service.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


/**
 * Servlet implementation class webController
 */
@WebServlet("/posteat/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UserDAO dao;
	UserService userService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        dao = new UserDAO();
        userService = new UserService();
        // TODO Auto-generated constructor stub
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
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo();
		System.out.println("UserController action: " + action);
		// 메인
		if (action == null || action.equals("/main.do")) {
			System.out.println("action: " + action);
			forwardReq(request, response, "/webjsp/main.jsp");
		}// 회원가입
		else if(action.equals("/register.do")) {
			if (request.getMethod().equalsIgnoreCase("GET")) {
	            forwardReq(request, response, "/webjsp/register.jsp");
	        } else if (request.getMethod().equalsIgnoreCase("POST")) {
	            processRegister(request, response);
	        }
		}
		else if(action.equals("/login.do") && request.getMethod().equalsIgnoreCase("GET")) {
	    	  forwardReq(request, response, "/webjsp/login.jsp"); 
	    }// 로그인
	    else if(action.equals("/login.do") && request.getMethod().equalsIgnoreCase("POST")) {
	    	  processLogin(request, response);
	    }// 로그아웃
	    else if(action.equals("/logout.do")) {
	    	  HttpSession session = request.getSession(false);
	    	  if(session != null)
	    		  session.invalidate();
	    	  response.sendRedirect(request.getContextPath() + "/posteat/main.do");
	    }
	    else if(action.equals("/profile.do")) {
	        // 프로필 보기
	        HttpSession session = request.getSession(false);
	        if(session != null && session.getAttribute("user") != null) {
	        	User user = (User) session.getAttribute("user");

	            // 사용자가 작성한 레시피 가져오기
	            RecipeService recipeService = new RecipeService();
	            List<Recipe> recipes = recipeService.getRecipesByUserId(user.getId());

	            // 레시피를 request에 설정
	            request.setAttribute("recipes", recipes);
	            
	            forwardReq(request, response, "/webjsp/profile.jsp");
	        } else {
	            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
	        }
	    } else if(action.equals("/modify.do")) {
	        if(request.getMethod().equalsIgnoreCase("GET")) {
	            // 프로필 수정 폼 보여주기
	            forwardReq(request, response, "/webjsp/modify.jsp");
	        } else if(request.getMethod().equalsIgnoreCase("POST")) {
	            // 프로필 수정 처리
	            processModify(request, response);
	        }
	    }
	}
	
	protected void forwardReq(HttpServletRequest request, HttpServletResponse response, String nextPage) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

	
	protected void processLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        HashMap<String, String> errors = userService.processLogin(id, password, session);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            forwardReq(request, response, "/webjsp/login.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/posteat/main.do");
        }
	}
	
	protected void processRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // 폼에서 전달된 파라미터 추출
		String id = request.getParameter("id");
		String password = request.getParameter("password");
	    String confirmpassword = request.getParameter("confirmpassword");
	    String nickname = request.getParameter("nickname");
	    
	    System.out.println("입력된 아이디:" + id);
	    System.out.println("입력된 비밀번호:" + password);
	    System.out.println("입력된 비밀번호확인:" + confirmpassword);
	    System.out.println("입력된 닉네임:" + nickname);
	    
	    HashMap<String, String> errors = userService.processRegister(id, password, confirmpassword, nickname);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            forwardReq(request, response, "/webjsp/register.jsp");
        } else {
            request.getSession().setAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
            response.sendRedirect(request.getContextPath() + "/posteat/main.do");
        }
	}
	
	protected void processModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession(false);
	    if(session == null || session.getAttribute("user") == null) {
	        response.sendRedirect(request.getContextPath() + "/posteat/login.do");
	        return;
	    }
	    
	    User currentUser = (User) session.getAttribute("user");
	    String nickname = request.getParameter("nickname");
	    String password = request.getParameter("password");
	    String confirmPassword = request.getParameter("confirmPassword");
	    
	    HashMap<String, String> errors = userService.processModify(currentUser, nickname, password, confirmPassword);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            forwardReq(request, response, "/webjsp/modify.jsp");
        } else {
            // 세션 정보 업데이트
            session.setAttribute("user", currentUser);
            response.sendRedirect(request.getContextPath() + "/posteat/profile.do");
        }
	}
	
}
