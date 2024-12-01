package web.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

import jakarta.servlet.http.HttpSession;
import web.DAO.UserDAO;
import web.DTO.User;

public class UserService {
	private UserDAO dao;

    public UserService() {
        this.dao = new UserDAO();
    }

    // 회원가입 처리 메서드
    public HashMap<String, String> processRegister(String id, String password, String confirmpassword, String nickname) {
        HashMap<String, String> errors = new HashMap<>();

        // 유효성 검사
        if (id == null || id.isEmpty()) {
            errors.put("id", "아이디를 입력해주세요.");
        } else if (dao.findById(id) != null) {
            errors.put("id", "이미 존재하는 아이디입니다.");
        }

        if (password == null || password.isEmpty()) {
            errors.put("password", "비밀번호를 입력해주세요.");
        } else if (!password.equals(confirmpassword)) {
            errors.put("confirmpassword", "비밀번호가 일치하지 않습니다.");
        }

        if (nickname == null || nickname.isEmpty()) {
            errors.put("nickname", "닉네임을 입력해주세요.");
        }

        // 에러가 있다면 회원가입 페이지로 돌아감
        if (!errors.isEmpty()) {
            return errors;
        }

        // 사용자 등록
        User user = new User();
        user.setId(id);
        user.setPassword(password); // 실제로는 비밀번호를 해싱하여 저장
        user.setNickname(nickname);
        user.setRegdate(LocalDateTime.now());

        dao.addUser(user);

        return errors;
    }
    
    
    // 로그인 처리 메서드
    public HashMap<String, String> processLogin(String id, String password, HttpSession session) {
        HashMap<String, String> errors = new HashMap<>();
        if (id == null || id.isEmpty()) {
            errors.put("id", "아이디를 입력해주세요.");
        }
        if (password == null || password.isEmpty()) {
            errors.put("password", "비밀번호를 입력해주세요.");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        System.out.println("입력된 아이디:" + id);
		System.out.println("입력된 비밀번호:" + password);
		
        User user = dao.findById(id);
        if (user == null || !password.equals(user.getPassword())) {
            errors.put("mismatch", "아이디 또는 비밀번호가 올바르지 않습니다.");
        } else {
            session.setAttribute("user", user); // 세션에 사용자 저장
        }

        return errors;
    }
    
    // 프로필 수정 처리 메서드
    public HashMap<String, String> processModify(User currentUser, String nickname, String password, String confirmPassword) {
        HashMap<String, String> errors = new HashMap<>();

        if (nickname == null || nickname.isEmpty()) {
            errors.put("nickname", "닉네임을 입력해주세요.");
        }

        if (password != null && !password.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                errors.put("password", "비밀번호가 일치하지 않습니다.");
            } else {
                currentUser.setPassword(password); // 실제로는 해싱 필요
            }
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        currentUser.setNickname(nickname);
        // 데이터베이스 업데이트
        dao.updateUser(currentUser); 

        return errors;
    }
    
}
