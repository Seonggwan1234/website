package web.website.controller;

import web.website.domain.User;
import web.website.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
// @RequestMapping("/api") <-- [삭제함] 이걸 지워야 루트 경로(/)를 잡을 수 있습니다.
public class UserController {

    private final UserRepository userRepository;

    // 1. 첫 접속 시(localhost:8080) -> main.html로 이동
    @GetMapping("/")
    public ModelAndView home() {
        // 로그인 안 한 사람은 main.html 로 가면 알아서 로그인 버튼이 보이니까 main으로 보냅니다.
        return new ModelAndView("redirect:/main.html");
    }

    // 2. 회원가입 API (주소에 /api를 직접 붙임)
    @PostMapping("/api/signup")
    public String signup(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");

        // 아이디 중복 체크
        if (userRepository.findByUserId(userId).isPresent()) {
            return "이미 존재하는 아이디입니다.";
        }

        User user = new User();
        user.setName(request.get("name"));
        user.setUserId(userId);
        user.setPassword(request.get("password"));

        userRepository.save(user); // DB에 저장
        return "회원가입 성공";
    }

    // 3. 로그인 API (주소에 /api를 직접 붙임)
    @PostMapping("/api/login")
    public String login(@RequestBody Map<String, String> request, HttpSession session) {
        String userId = request.get("userId");
        String password = request.get("password");

        // DB에서 아이디로 조회
        User user = userRepository.findByUserId(userId).orElse(null);

        // 유저가 있고, 비밀번호가 맞다면?
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loginUser", user); // 세션에 로그인 정보 저장
            return "로그인 성공";
        } else {
            return "로그인 실패";
        }
    }
}