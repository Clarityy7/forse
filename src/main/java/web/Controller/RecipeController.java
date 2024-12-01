package web.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.DAO.RecipeDAO;
import web.DTO.Recipe;
import web.DTO.User;
import web.Service.RecipeService;

import java.time.LocalDateTime;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Servlet implementation class RecipeController
 */

@WebServlet("/recipe/*")
public class RecipeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	RecipeDAO recipeDAO;
	RecipeService recipeService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecipeController() {
    	 recipeDAO = new RecipeDAO();
    	 recipeService = new RecipeService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo();
		System.out.println("RecipeController action: " + action);
		
		 if (action == null || action.equals("/list.do")) {
	            // 레시피 목록 보기
	            listRecipes(request, response);
	        } else if (action.equals("/add.do")) {
	            // 레시피 추가 폼 보여주기
	            showAddForm(request, response);
	        } else if (action.equals("/insert.do")) {
	            // 레시피 추가 처리
	            insertRecipe(request, response);
	        } else if (action.equals("/edit.do")) {
	        	// 레시피 수정 폼 보여주기
	        	showEditForm(request, response);
	        } else if (action.equals("/update.do")) {
	        	// 레시피 수정 처리
	        	updateRecipe(request, response);
	        } else if (action.equals("/delete.do")) {
	        	// 레시피 삭제 처리
	        	deleteRecipe(request, response);
	        } else if (action.equals("/view.do")) {
	        	// 레시피 디테일 보기 
	        	showRecipeDetail(request, response);
	        }
	}
	
	protected void forwardReq(HttpServletRequest request, HttpServletResponse response, String nextPage) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
	
	// 레시피 목록 보기
    private void listRecipes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int pageSize = 10; // 페이지당 레시피 개수

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

     // 레시피 목록과 총 레시피 수 가져오기
        List<Recipe> recipes = recipeDAO.getRecipes(page, pageSize);
        
        int totalRecipes = recipeDAO.getRecipeCount();
        int totalPages = (int) Math.ceil((double) totalRecipes / pageSize);

        request.setAttribute("recipes", recipes);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        forwardReq(request, response, "/webjsp/recipeList.jsp");
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }

        forwardReq(request, response, "/webjsp/recipeAdd.jsp");
    }
    
    // 레시피 추가 처리
    private void insertRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }

        User user = (User) session.getAttribute("user");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        // 이미지 업로드 기능은 추후 구현
        
        HashMap<String, String> errors = recipeService.addRecipe(title, description, user.getId());

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            forwardReq(request, response, "/webjsp/recipeAdd.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/recipe/list.do");
        }
    }
    
    // 레시피 수정 처리
    private void updateRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }

        User user = (User) session.getAttribute("user");
        int recipeID = Integer.parseInt(request.getParameter("recipeID"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        HashMap<String, String> errors = recipeService.updateRecipe(recipeID, title, description, user.getId());

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("recipeID", recipeID);
            forwardReq(request, response, "/webjsp/recipeEdit.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/recipe/view.do?recipeID=" + recipeID);
        }
    }
    
    // 레시피 삭제 처리 
    private void deleteRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }

        User user = (User) session.getAttribute("user");
        int recipeID = Integer.parseInt(request.getParameter("recipeID"));
        
        if (!recipeService.deleteRecipe(recipeID, user.getId())) {
            response.sendRedirect(request.getContextPath() + "/recipe/list.do");
        } else {
            response.sendRedirect(request.getContextPath() + "/recipe/list.do");
        }
    }
    
    // 수정 폼 보여주기
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }

        User user = (User) session.getAttribute("user");
        int recipeID = Integer.parseInt(request.getParameter("recipeID"));
        Recipe recipe = recipeDAO.getRecipeById(recipeID);

        if (recipe == null || !recipe.getUserID().equals(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/recipe/list.do");
            return;
        }

        request.setAttribute("recipe", recipe);
        forwardReq(request, response, "/webjsp/recipeEdit.jsp");
    }
    
    // 레시피 상세 보기 처리
    private void showRecipeDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recipeID = Integer.parseInt(request.getParameter("recipeID"));

        // 레시피 정보 조회
        Recipe recipe = recipeDAO.getRecipeById(recipeID);

        if (recipe == null) {
            response.sendRedirect(request.getContextPath() + "/recipe/list.do"); // 레시피가 없으면 목록으로
            return;
        }

        request.setAttribute("recipe", recipe);

        // JSP로 포워딩
        forwardReq(request, response, "/webjsp/recipeDetail.jsp");
    }

}
