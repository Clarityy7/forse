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

/*
 * 작성자: 박준형
 * 작성일: 2024-11-21
 */

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

	
	protected void processLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		HashMap<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		System.out.println("입력된 아이디:" + id);
		System.out.println("입력된 비밀번호:" + password);
		
		if(id == null || id.isEmpty())
			errors.put("id", true);
		if(password == null || password.isEmpty())
			errors.put("password", true);
		
		if(!errors.isEmpty()){
			forwardReq(req, res, "/webjsp/login.jsp");
			return;
		}
		
		User user = dao.findById(id);
		
		if(user == null) {
	        System.out.println("DB에서 데이터를 찾을 수 없음");
	        errors.put("mismatch", true);
	        forwardReq(req, res, "/webjsp/login.jsp");
	        return;
	    }
		
		System.out.println("db 아이디:" + user.getId());
		System.out.println("db 비밀번호:" + user.getPassword());
		
		if(!password.equals(user.getPassword())) {
			errors.put("mismatch", true);
			forwardReq(req, res, "/webjsp/login.jsp");
			return;
		}
		
		req.getSession().setAttribute("user", user);
		res.sendRedirect(req.getContextPath() + "/posteat/main.do");
	}
	
	protected void processRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 에러 메시지를 저장할 맵
		HashMap<String, String> errors = new HashMap<>();
		request.setAttribute("errors", errors);

		 // 폼에서 전달된 파라미터 추출
		String id = request.getParameter("id");
		String password = request.getParameter("password");
	    String confirmpassword = request.getParameter("confirmpassword");
	    String nickname = request.getParameter("nickname");
	    
	    System.out.println("입력된 아이디:" + id);
	    System.out.println("입력된 비밀번호:" + password);
	    System.out.println("입력된 비밀번호확인:" + confirmpassword);
	    System.out.println("입력된 닉네임:" + nickname);
	    
	    // 유효성 검사
	    if (id == null || id.isEmpty()) {
	        errors.put("id", "아이디를 입력해주세요.");
	    } else if (dao.findById(id) != null) {
	        errors.put("id", "이미 존재하는 아이디입니다.");
	    }
	    
	    if (password == null || password.isEmpty()) {
	        errors.put("password", "비밀번호를 입력해주세요.");
	    } else if (!password.equals(confirmpassword)) {
	        errors.put("confirmpassword", "비밀번호가 일치하지 않습니다.");
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
	    user.setPassword(password); // 실제로는 비밀번호를 해싱하여 저장해야 합니다.
	    user.setNickname(nickname);
	    user.setRegdate(LocalDateTime.now());

	    dao.addUser(user);

	    // 회원가입 성공 메시지를 세션 또는 리퀘스트에 저장할 수 있습니다.
	    request.getSession().setAttribute("message", "회원가입이 성공적으로 완료되었습니다.");

	    response.sendRedirect(request.getContextPath() + "/posteat/main.do");
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
	    
	    HashMap<String, String> errors = new HashMap<>();
	    request.setAttribute("errors", errors);
	    
	    if(nickname == null || nickname.isEmpty()) {
	        errors.put("nickname", "닉네임을 입력해주세요.");
	    }
	    
	    if(password != null && !password.isEmpty()) {
	        if(!password.equals(confirmPassword)) {
	            errors.put("password", "비밀번호가 일치하지 않습니다.");
	        }
	    }
	    
	    if(!errors.isEmpty()) {
	        forwardReq(request, response, "/modify.jsp");
	        return;
	    }
	    
	    // 사용자 정보 업데이트
	    currentUser.setNickname(nickname);
	    if(password != null && !password.isEmpty()) {
	        currentUser.setPassword(password); // 실제로는 해싱 필요
	    }
	    
	    // 데이터베이스 업데이트
	    dao.updateUser(currentUser);
	    
	    // 세션 정보 업데이트
	    session.setAttribute("user", currentUser);
	    
	    response.sendRedirect(request.getContextPath() + "/posteat/profile.do");
	}
	
}
