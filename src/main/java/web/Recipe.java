package web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recipe {
	private String recipeID;
	private String title;
	private String description;
	private String imagePath;
}
