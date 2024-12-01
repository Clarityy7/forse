package web.DTO;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Comment {
	private long commentId;       // 댓글 고유 ID
    private long recipeId;        // 댓글이 달린 레시피의 ID
    private String userId;        // 댓글 작성자 ID
    private String content;       // 댓글 내용
    private LocalDateTime createdAt; // 댓글 작성 시간
}


/*
	CREATE TABLE Comment (
		comment_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 댓글 고유 ID 번호
		user_id VARCHAR(255) NOT NULL, -- 댓글 작성자 ID
		recipe_id BIGINT NOT NULL, -- 댓글을 작성한 레시피의 고유 ID
		content TEXT NOT NULL, -- 작성한 댓글
		created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 댓글 작성한 시간
		FOREIGN KEY (user_id) REFERENCES user(user_id), -- 외래 키로 user의 user_id
		FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id) -- 외래 키로 recipe의 recipe_id
	);
	테이블은 이런 형태로 짰었어요.
*/