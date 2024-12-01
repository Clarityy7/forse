package web.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import web.DTO.User;


public class UserDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/spring4fs";
	
	void connect() {
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url, "spring4", "spring4");
			System.out.println("Connection connected");
		}catch(Exception e) {
			System.out.println("failed");
	         e.printStackTrace();
		}
	}
	
	void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		if(conn != null) {
			try {
				conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public User findById(String id) {
	      connect();
	      User user = null;
	      String sql = "select * from user where id=?";
	      System.out.println("SQL 실행: SELECT * FROM user WHERE id=" + id);
	      try {
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, id);
	         ResultSet rs = pstmt.executeQuery();
	         if (rs.next()) {
	            user = new User();
	            user.setId(rs.getString("id"));
	            user.setPassword(rs.getString("password"));
	            user.setNickname(rs.getString("nickname"));
	            user.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
	         }else {
	        	    System.out.println("DB에서 데이터를 찾을 수 없음");
	         }
	         rs.close();
	      } catch (Exception e) {
	         System.out.println(e);
	         return null;
	      } finally {
	         disconnect();
	      }
	      return user;
	   }
	
	public void addUser(User user) {
	    connect();
	    String sql = "INSERT INTO user (id, password, nickname, regdate) VALUES (?, ?, ?, ?)";
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, user.getId());
	        pstmt.setString(2, user.getPassword());
	        pstmt.setString(3, user.getNickname());
	        pstmt.setTimestamp(4, Timestamp.valueOf(user.getRegdate()));
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        disconnect();
	    }
	}

	public void updateUser(User user) {
	    connect();
	    String sql = "UPDATE user SET nickname = ?, password = ? WHERE id = ?";
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, user.getNickname());
	        pstmt.setString(2, user.getPassword());
	        pstmt.setString(3, user.getId());
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        disconnect();
	    }
	}
}
