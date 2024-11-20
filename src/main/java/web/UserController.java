package web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Servlet implementation class webController
 */
@WebServlet("/posteat/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UserDAO dao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        dao = new UserDAO();
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
		System.out.println("action: " + action);
		if (action == null || action.equals("/main.do")) {
			forwardReq(request, response, "/webjsp/main.jsp");
		}
		else if(action.equals("register.do")) {
			HashMap<String, String> errors = new HashMap<>();
		    request.setAttribute("errors", errors);

		    String id = request.getParameter("id");
		    String password = request.getParameter("password");
		    String confirmPassword = request.getParameter("confirmpassword");
		    String nickname = request.getParameter("nickname");

		    // 유효성 검사
		    if (id == null || id.isEmpty()) {
		        errors.put("id", "아이디를 입력해주세요.");
		    } else if (dao.findById(id) != null) {
		        errors.put("id", "이미 존재하는 아이디입니다.");
		    }

		    if (password == null || password.isEmpty()) {
		        errors.put("password", "비밀번호를 입력해주세요.");
		    } else if (!password.equals(confirmPassword)) {
		        errors.put("confirmPassword", "비밀번호가 일치하지 않습니다.");
		    }

		    if (nickname == null || nickname.isEmpty()) {
		        errors.put("nickname", "닉네임을 입력해주세요.");
		    }

		    // 에러가 있다면 회원가입 페이지로 돌아감
		    if (!errors.isEmpty()) {
		        forwardReq(request, response, "/register.jsp");
		        return;
		    }

		    // 사용자 등록
		    User user = new User();
		    user.setId(id);
		    user.setPassword(password); // 암호화 추천
		    user.setNickname(nickname);
		    user.setRegdate(LocalDateTime.now());

		    dao.addUser(user);
		    response.sendRedirect("/posteat/main.jsp"); // 메인 페이지로 리다이렉트
		}
		else if(action.equals("login.do") && request.getMethod().equalsIgnoreCase("GET")) {
	    	  forwardReq(request, response, "/login/login.jsp");
	    }
	    else if(action.equals("login.do") && request.getMethod().equalsIgnoreCase("POST")) {
	    	  forwardReq(request, response, processLogin(request, response));
	    }
	    else if(action.equals("logout.do")) {
	    	  HttpSession session = request.getSession(false);
	    	  if(session != null)
	    		  session.invalidate();
	    	  response.sendRedirect("/test/webjsp/main.jsp");
	    }
	}
	
	protected void forwardReq(HttpServletRequest request, HttpServletResponse response, String nextPage) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

	
	protected String processLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		HashMap<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		if(id == null || id.isEmpty())
			errors.put("id", true);
		if(password == null || password.isEmpty())
			errors.put("password", true);
		
		if(errors.isEmpty())
			return "/webjsp/login.jsp";
		
		User user = dao.findById(id);
		if(password.equals(user.getPassword())) {
			errors.put("mismatch", true);
			return "/webjsp/login.jsp";
		}
		
		req.getSession().setAttribute("user", user);
		res.sendRedirect("/forse/webjsp/main.jsp");
		

		return null;
	}
}
