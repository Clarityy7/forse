package web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;


public class UserDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url =
	"jdbc:mysql://localhost/spring4fs";
	
	void connect() {
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url, "spring4", "spring4");
		}catch(Exception e) {
			System.out.println(e);
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
	      ArrayList<User> list = new ArrayList<>();
	      String sql = "select * from member where id=?";
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
	    String sql = "INSERT INTO member (id, password, nickname, regdate) VALUES (?, ?, ?, ?)";
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

}
