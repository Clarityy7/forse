package web;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private final String jdbcDriver = "com.mysql.cj.jdbc.Driver"; // 일단 그대로 적어두기
	private final String jdbcUrl = "jdbc:mysql://localhost/recipe_app"; // 같음
	
	// DB 연결
	private void connect() {
		try {
			Class.forName(jdbcDriver); // 위가 jdbcDriver
			conn = DriverManager.getConnection(jdbcUrl, "root", "password");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 연결 해제
	private void disconnect() {
		try {
			if(pstmt!=null)
				pstmt.close();
			if(conn!=null)
				conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//레시피 추가
	public void addRecipe(Recipe recipe) {
		connect();
		String sql = "INSERT INTO recipe (user_id, title,description, instruction, image_url) VALUES(?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql); // 연결을 위한 객체 생성
			pstmt.setString(1, recipe.getUserId());
			pstmt.setString(2, recipe.getTitle());
			pstmt.setString(3, recipe.getDescription());
			pstmt.setString(4, recipe.getInstruction());
			pstmt.setString(5, recipe.getImageUrl());
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace(); // 예외처리 자리
		}finally {
			disconnect();
		}
	}
	
	//레시피 삭제
	public void deleteRecipe(long recipeId) {
		connect();
		String sql = "DELETE FROM recipe WHERE recipe_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,recipeId);
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		
	}
	
	// 레시피 수정
	public void updateRecipe(Recipe recipe) {
		connect();
		String sql = "UPDATE recipe SET title = ? , description = ?, instruction = ?, image_url = ?, updated_at = CURRENT_TIMESTAMP WHERE recipe_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, recipe.getTitle());
			pstmt.setString(2, recipe.getDescription());
			pstmt.setString(3, recipe.getInstruction());
			pstmt.setString(4, recipe.getImageUrl());
			pstmt.setString(5, recipe.getUserId());
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
	}
	
	//레시피 찾기
	public Recipe FindrecipebyId(long recipeId) {
		connect();
		Recipe recipe = null;
		String sql = "SELECT * FROM recipe WHRER recipe_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, recipeId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				recipe = new Recipe();
				recipe.setRecipeId(rs.getLong("recipe_id"));
				recipe.setUserId(rs.getString("user_id"));
				recipe.setTitle(rs.getString("title"));
				recipe.setDescription(rs.getString("description"));
				recipe.setInstruction(rs.getString("instruction"));
				recipe.setImageUrl(rs.getString("image_url"));
				recipe.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                recipe.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			rs.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return recipe;
		
	}
	
	
	/*
	 * // 모든 레시피 불러오기 public List<Recipe> FindAllRrecipe(){ connect(); List<Recipe>
	 * recipes = new ArrayList<>(); String sql = "SELECT * FROM recipe"; // 끝인가 이게?
	 * try { pstmt = conn.prepareStatement(sql); ResultSet rs =
	 * pstmt.executeQuery(); while (rs.next()) { Recipe recipe = new Recipe();
	 * recipe.setRecipeId(rs.getLong("recipe_id"));
	 * recipe.setUserId(rs.getString("user_id"));
	 * recipe.setTitle(rs.getString("title"));
	 * recipe.setDescription(rs.getString("description"));
	 * recipe.setInstruction(rs.getString("instruction"));
	 * recipe.setImageUrl(rs.getString("image_url"));
	 * recipe.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
	 * recipe.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
	 * recipes.add(recipe); } rs.close(); }catch (Exception e) {
	 * e.printStackTrace(); }finally { disconnect(); } return recipes;
	 */
}
