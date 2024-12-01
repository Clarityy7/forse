package web.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import web.DAO.RecipeDAO;
import web.DTO.Recipe;

public class RecipeService {
	private RecipeDAO dao;
	
	public RecipeService() {
		this.dao = new RecipeDAO();
	}
	
	// 해당 사용자 ID로 작성한 레시피 모두 가져오기 
	public List<Recipe> getRecipesByUserId(String userId) {
	    return dao.getRecipesByUserId(userId); 
	}
	
	// 레시피 목록 조회
    public List<Recipe> getRecipes(int page, int pageSize) {
        return dao.getRecipes(page, pageSize);
    }

    // 레시피 상세 조회
    public Recipe getRecipeById(int recipeID) {
        return dao.getRecipeById(recipeID);
    }
    
    // 레시피 추가 처리
    public HashMap<String, String> addRecipe(String title, String description, String userID) {
        HashMap<String, String> errors = new HashMap<>();

        if (title == null || title.isEmpty()) {
            errors.put("title", "제목을 입력해주세요.");
        }
        if (description == null || description.isEmpty()) {
            errors.put("description", "내용을 입력해주세요.");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setUserID(userID);
        recipe.setRegdate(LocalDateTime.now());

        // 디버깅 로그
        System.out.println("레시피 추가 - Title: " + recipe.getTitle() + ", Description: " + recipe.getDescription());
        dao.addRecipe(recipe);
        
        return errors;
    }
    
    // 레시피 수정 처리
    public HashMap<String, String> updateRecipe(int recipeID, String title, String description, String userID) {
        HashMap<String, String> errors = new HashMap<>();
        Recipe recipe = dao.getRecipeById(recipeID);

        if (recipe == null || !recipe.getUserID().equals(userID)) {
            errors.put("authorization", "수정 권한이 없습니다.");
            return errors;
        }

        if (title == null || title.isEmpty()) {
            errors.put("title", "제목을 입력해주세요.");
        }
        if (description == null || description.isEmpty()) {
            errors.put("description", "내용을 입력해주세요.");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        recipe.setTitle(title);
        recipe.setDescription(description);
        dao.updateRecipe(recipe);
        return errors;
    }

    // 레시피 삭제 처리
    public boolean deleteRecipe(int recipeID, String userID) {
        Recipe recipe = dao.getRecipeById(recipeID);

        if (recipe == null || !recipe.getUserID().equals(userID)) {
            return false;
        }

        dao.deleteRecipe(recipeID);
        return true;
    }

    // 레시피 총 개수
    public int getTotalRecipeCount() {
        return dao.getRecipeCount();
    }
}
