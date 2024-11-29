package web;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Recipe {
	private long recipeId;
	private String userId;
	private String title;
	private String description;
	private String instruction;
	private String imageUrl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
