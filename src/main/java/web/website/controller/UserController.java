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
@RequestMapping("/api") // 주소 앞에 무조건 /api 가 붙음
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("redirect:/login.html");
    }

    // 1. 회원가입 API
    @PostMapping("/signup")
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

    // 2. 로그인 API
    @PostMapping("/login")
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
