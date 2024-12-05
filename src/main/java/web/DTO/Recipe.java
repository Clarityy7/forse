package web.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recipe {
	private int recipeID;
	private String title;
	private String description;
	private String imagePath;
	private String userID;
	private LocalDateTime regdate;
    private int likes;
	
	public void setRegdate(LocalDateTime regdate) {
        this.regdate = regdate;
    }
}
