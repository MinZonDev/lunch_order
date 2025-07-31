package vn.com.hdbank.lunch_order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.com.hdbank.lunch_order.dto.AdminUpdateUserDto;
import vn.com.hdbank.lunch_order.dto.UpdateProfileDto;
import vn.com.hdbank.lunch_order.dto.UserProfileDto;
import vn.com.hdbank.lunch_order.dto.response.ApiResponse;
import vn.com.hdbank.lunch_order.entity.User;
import vn.com.hdbank.lunch_order.service.UserService;
import vn.com.hdbank.lunch_order.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserProfileDto>> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        UserProfileDto profile = userService.getMyProfile(username);
        return ResponseUtil.success("Lấy thông tin cá nhân thành công", profile);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateMyProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto, Authentication authentication) {
        String username = authentication.getName();
        UserProfileDto updatedProfile = userService.updateMyProfile(username, updateProfileDto);
        return ResponseUtil.success("Cập nhật thông tin thành công", updatedProfile);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserProfileDto>> getUserProfile(@PathVariable Long id) {
        UserProfileDto profile = userService.getUserProfile(id);
        return ResponseUtil.success("Lấy thông tin người dùng thành công", profile);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserProfileDto>>> getAllUsers() {
        List<UserProfileDto> users = userService.getAllUsers();
        return ResponseUtil.success("Lấy danh sách người dùng thành công", users);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserProfileDto>>> searchUsers(@RequestParam String keyword) {
        List<UserProfileDto> users = userService.searchUsers(keyword);
        return ResponseUtil.success("Tìm kiếm người dùng thành công", users);
    }

    @PutMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminUpdateUserProfile(@PathVariable Long id, @RequestBody AdminUpdateUserDto adminUpdateUserDto) {
        User updatedUser = userService.adminUpdateUserProfile(id, adminUpdateUserDto);
        // Decide what to return. Returning the full User object might be a security risk.
        // Let's return a success message or a DTO.
        return ResponseUtil.success("Cập nhật người dùng thành công");
    }
}
