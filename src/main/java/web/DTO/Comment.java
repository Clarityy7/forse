package web.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
	private int commentID;
	private int recipeID;
	private String userID;
	private String content;
	private LocalDateTime regdate;
}
