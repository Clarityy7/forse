package web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/빈칸/*")
public class RecipeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RecipeDAO recipeDAO;
    
    public RecipeController() {
    	this.recipeDAO = new RecipeDAO();
    }
    
    //http get 요청 처리 -> 조회
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
    
    //http post 요청 처리 -> 전송
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }
    
    protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null || action.equals("/list")) {
            listRecipes(request, response);
        } else if (action.equals("/add") && request.getMethod().equalsIgnoreCase("POST")) {
            addRecipe(request, response);
        } else if (action.startsWith("/view")) {
            viewRecipe(request, response);
        } else if (action.startsWith("/edit")) {
            editRecipe(request, response);
        } else if (action.startsWith("/delete")) {
            deleteRecipe(request, response);
        }
    }

    // 레시피 목록 보기
    private void listRecipes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Recipe> recipes = recipeDAO.FindAllRrecipe();
        request.setAttribute("recipes", recipes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/webjsp/recipeList.jsp");
        dispatcher.forward(request, response);
    }

    // 새로운 레시피 추가
    private void addRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/posteat/login.do");
            return;
        }

        User user = (User) session.getAttribute("user");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String instruction = request.getParameter("instruction");
        String imageUrl = request.getParameter("image_url");

        Recipe recipe = new Recipe();
        recipe.setUserId(user.getId());
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setInstruction(instruction);
        recipe.setImageUrl(imageUrl);
        recipe.setCreatedAt(LocalDateTime.now());

        recipeDAO.addRecipe(recipe);
        response.sendRedirect(request.getContextPath() + "/recipe/list");
    }

    // 레시피 상세 보기
    private void viewRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long recipeId = Long.parseLong(request.getParameter("id"));
        Recipe recipe = recipeDAO.FindrecipebyId(recipeId);
        request.setAttribute("recipe", recipe);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/webjsp/viewRecipe.jsp");
        dispatcher.forward(request, response);
    }

    // 레시피 수정 (GET 요청)
    private void editRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long recipeId = Long.parseLong(request.getParameter("id"));
        Recipe recipe = recipeDAO.FindrecipebyId(recipeId); // 수정할 레시피 조회
        request.setAttribute("recipe", recipe);  // 수정할 레시피 정보를 JSP에 전달
        RequestDispatcher dispatcher = request.getRequestDispatcher("/webjsp/editRecipe.jsp");
        dispatcher.forward(request, response);
    }

    // 레시피 수정 (POST 요청)
    private void updateRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long recipeId = Long.parseLong(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String instruction = request.getParameter("instruction");
        String imageUrl = request.getParameter("image_url");

        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setInstruction(instruction);
        recipe.setImageUrl(imageUrl);
        recipe.setUpdatedAt(LocalDateTime.now());

        recipeDAO.updateRecipe(recipe); // 레시피 수정
        response.sendRedirect(request.getContextPath() + "/recipe/view?id=" + recipeId); // 수정 후 해당 레시피 상세 페이지로 리다이렉트
    }

    // 레시피 삭제
    private void deleteRecipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long recipeId = Long.parseLong(request.getParameter("id"));
        recipeDAO.deleteRecipe(recipeId); // 레시피 삭제
        response.sendRedirect(request.getContextPath() + "/recipe/list"); // 삭제 후 레시피 목록 페이지로 리다이렉트
    }
}
