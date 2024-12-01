package web.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private String id;
	private String password;
	private String nickname;
	private LocalDateTime regdate;
}
