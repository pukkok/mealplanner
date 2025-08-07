package com.training.mealplanner.member;

import com.training.mealplanner.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> signup(@RequestParam String username,
                                                      @RequestParam String name,
                                                      @RequestParam String password) {

        Map<String, Object> res = new HashMap<>();
        RegisterResult result = memberService.register(username, name, password);

        HttpStatus status;

        // 성공 여부에 따라 키 결정
        if (result == RegisterResult.SUCCESS) {
            res.put("msg", result.getMessage());
            status = HttpStatus.OK;
        } else if (result == RegisterResult.USERNAME_EXISTS) {
            res.put("error", result.getMessage());
            status = HttpStatus.CONFLICT;
        } else {
            res.put("error", result.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(res, status);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpServletResponse response) {
        boolean result = memberService.login(username, password);

        if (result) {

            String token = jwtUtil.generateToken(username);

            // JWT를 쿠키에 담아서 클라이언트로 전송
            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(Map.of(
                            "msg", "로그인 성공",
                            "name", memberService.getNameByUsername(username),
                            "href", "/mealplan"
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인 실패"));
        }
    }

}
