package web.DTO;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {
    private long likeId;         // 좋아요 고유 ID
    private long recipeId;       // 레시피 ID
    private String userId;       // 사용자 ID
    private LocalDateTime createdAt;  // 좋아요 누른 시간
}

/*
	CREATE TABLE Like (
		user_id VARCHAR(255) NOT NULL, -- 좋아요 한 사용자의 ID
		recipe_id BIGINT NOT NULL, -- 좋아요 한 레시피 고유 ID
		created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 좋아요 한 시간
		PRIMARY KEY (user_id, recipe_id), -- 키 2개
		FOREIGN KEY (user_id) REFERENCES user(user_id), -- 외래 키로 user의 user_id
		FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id) -- 외래 키로 recipe의 recipe_id
	);
	테이블은 이런 형태로 짰었어요.
*/