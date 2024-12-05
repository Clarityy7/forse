	package web.Service;
	
	import web.DAO.RecommendationDAO;
	
	public class RecommendationService {
	    private RecommendationDAO recommendationDAO;
	
	    public RecommendationService() {
	        recommendationDAO = new RecommendationDAO();
	    }
	
	    public boolean addRecommendation(int recipeID, String userID) {
	    	if (recommendationDAO.hasAlreadyRecommended(recipeID, userID)) {
	            return false; // 이미 추천한 경우
	        }
	        return recommendationDAO.addRecommendation(recipeID, userID); // 추천 추가
	    }
	
	    public int getRecommendationCount(int recipeID) {
	        return recommendationDAO.getRecommendationCount(recipeID);
	    }
	}
