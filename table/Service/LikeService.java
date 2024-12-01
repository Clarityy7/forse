package web.Service;

import java.util.HashMap;
import web.DAO.LikeDAO;

public class LikeService {
    private LikeDAO dao;

    public LikeService() {
        this.dao = new LikeDAO();
    }

    // 좋아요 추가
    public HashMap<String, String> addLike(long recipeId, String userId) {
        HashMap<String, String> errors = new HashMap<>();

        if (dao.hasLiked(recipeId, userId)) {
            errors.put("alreadyLiked", "이미 좋아요를 눌렀습니다.");
            return errors;
        }

        dao.addLike(recipeId, userId);
        return errors;
    }
    
    // 좋아요 개수 조회
    public int getLikeCount(long recipeId) {
        return dao.getLikeCount(recipeId);
    }

    // 사용자가 해당 레시피에 좋아요를 눌렀는지 확인
    public boolean hasLiked(long recipeId, String userId) {
        return dao.hasLiked(recipeId, userId);
    }

    // 좋아요 삭제
    public HashMap<String, String> removeLike(long recipeId, String userId) {
        HashMap<String, String> errors = new HashMap<>();

        if (!dao.hasLiked(recipeId, userId)) {
            errors.put("notLiked", "좋아요를 누르지 않았습니다.");
            return errors;
        }

        dao.removeLike(recipeId, userId);
        return errors;
    }

}
