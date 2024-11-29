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
    
    //doHandle 어떻게 만들지..
}