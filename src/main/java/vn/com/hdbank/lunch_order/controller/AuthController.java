package vn.com.hdbank.lunch_order.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.lunch_order.dto.LoginDto;
import vn.com.hdbank.lunch_order.dto.RegisterDto;
import vn.com.hdbank.lunch_order.dto.UpdateProfileDto;
import vn.com.hdbank.lunch_order.dto.UserProfileDto;
import vn.com.hdbank.lunch_order.dto.response.ApiResponse;
import vn.com.hdbank.lunch_order.dto.response.AuthResponse;
import vn.com.hdbank.lunch_order.entity.User;
import vn.com.hdbank.lunch_order.service.AuthService;
import vn.com.hdbank.lunch_order.util.ResponseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, Object>>> register(@Valid @RequestBody RegisterDto registerDto) {
        User user = authService.register(registerDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("username", user.getUsername());
//        responseData.put("name", user.getName());
//        responseData.put("email", user.getEmail());

        return ResponseUtil.success("Đăng ký thành công", responseData);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginDto loginDto) {
        AuthResponse authResponse = authService.login(loginDto);
        return ResponseUtil.success("Đăng nhập thành công", authResponse);
    }

}
