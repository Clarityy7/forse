package web.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import web.Util.DatabaseUtil;
import web.DTO.User;

public class UserDAO {

    // disconnect
    void disconnect(PreparedStatement pstmt, Connection conn) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // id로 사용자 찾기
    public User findById(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        User user = null;

        String sql = "SELECT * FROM user WHERE id = ?";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setRegdate(rs.getTimestamp("regdate").toLocalDateTime());
            } else {
                System.out.println("DB에서 데이터를 찾을 수 없음");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            disconnect(pstmt, conn);
        }
        return user;
    }

    // 사용자 추가
    public void addUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO user (id, password, nickname, regdate) VALUES (?, ?, ?, ?)";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setTimestamp(4, Timestamp.valueOf(user.getRegdate()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(pstmt, conn);
        }
    }

    // 사용자 정보 업데이트
    public void updateUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "UPDATE user SET nickname = ?, password = ? WHERE id = ?";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getNickname());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(pstmt, conn);
        }
    }
}