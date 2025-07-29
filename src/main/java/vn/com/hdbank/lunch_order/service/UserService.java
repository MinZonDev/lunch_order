package vn.com.hdbank.lunch_order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.hdbank.lunch_order.dto.AdminUpdateUserDto;
import vn.com.hdbank.lunch_order.dto.UpdateProfileDto;
import vn.com.hdbank.lunch_order.dto.UserProfileDto;
import vn.com.hdbank.lunch_order.entity.User;
import vn.com.hdbank.lunch_order.exception.BusinessException;
import vn.com.hdbank.lunch_order.repository.RoleRepository;
import vn.com.hdbank.lunch_order.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserProfileDto getMyProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> BusinessException.notFound("Người dùng không tồn tại"));
        return convertToUserProfileDto(user);
    }

    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> BusinessException.notFound("Người dùng không tồn tại"));
        UserProfileDto profile = convertToUserProfileDto(user);
        profile.setBalance(null); // Hide balance for privacy
        return profile;
    }

    public List<UserProfileDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserProfileDto)
                .collect(Collectors.toList());
    }

    public List<UserProfileDto> searchUsers(String keyword) {
        List<User> users = userRepository.findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(keyword, keyword);
        return users.stream()
                .map(user -> {
                    UserProfileDto profile = convertToUserProfileDto(user);
                    profile.setBalance(null); // Hide balance in search results
                    return profile;
                })
                .collect(Collectors.toList());
    }

    public UserProfileDto updateMyProfile(String username, UpdateProfileDto updateProfileDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> BusinessException.notFound("Người dùng không tồn tại"));

        if (updateProfileDto.getEmail() != null && !user.getEmail().equals(updateProfileDto.getEmail()) &&
                userRepository.existsByEmail(updateProfileDto.getEmail())) {
            throw BusinessException.conflict("Email đã được sử dụng");
        }

        if (updateProfileDto.getName() != null) {
            user.setName(updateProfileDto.getName());
        }
        if (updateProfileDto.getDisplayName() != null) {
            user.setDisplayName(updateProfileDto.getDisplayName());
        }
        if (updateProfileDto.getEmail() != null) {
            user.setEmail(updateProfileDto.getEmail());
        }
        if (updateProfileDto.getPhoneNo() != null) {
            user.setPhoneNo(updateProfileDto.getPhoneNo());
        }
        if (updateProfileDto.getBio() != null) {
            user.setBio(updateProfileDto.getBio());
        }
        if (updateProfileDto.getTeam() != null) {
            user.setTeam(updateProfileDto.getTeam());
        }
        if (updateProfileDto.getBirthday() != null) {
            user.setBirthday(updateProfileDto.getBirthday());
        }
        if (updateProfileDto.getAvatar() != null) {
            user.setAvatar(updateProfileDto.getAvatar());
        }
        if (updateProfileDto.getEmployeeCode() != null) {
            user.setEmployeeCode(updateProfileDto.getEmployeeCode());
        }

        User updatedUser = userRepository.save(user);
        return convertToUserProfileDto(updatedUser);
    }

    public User adminUpdateUserProfile(Long userId, AdminUpdateUserDto adminUpdateUserDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        if (adminUpdateUserDto.getName() != null) {
            user.setName(adminUpdateUserDto.getName());
        }
        if (adminUpdateUserDto.getDisplayName() != null) {
            user.setDisplayName(adminUpdateUserDto.getDisplayName());
        }
        if (adminUpdateUserDto.getBalance() != null) {
            user.setBalance(adminUpdateUserDto.getBalance());
        }
        if (adminUpdateUserDto.getEmail() != null) {
            if (!user.getEmail().equals(adminUpdateUserDto.getEmail()) && userRepository.existsByEmail(adminUpdateUserDto.getEmail())) {
                throw BusinessException.conflict("Email đã được sử dụng");
            }
            user.setEmail(adminUpdateUserDto.getEmail());
        }
        if (adminUpdateUserDto.getPhoneNo() != null) {
            user.setPhoneNo(adminUpdateUserDto.getPhoneNo());
        }
        if (adminUpdateUserDto.getBio() != null) {
            user.setBio(adminUpdateUserDto.getBio());
        }
        if (adminUpdateUserDto.getTeam() != null) {
            user.setTeam(adminUpdateUserDto.getTeam());
        }
        if (adminUpdateUserDto.getBirthday() != null) {
            user.setBirthday(adminUpdateUserDto.getBirthday());
        }
        if (adminUpdateUserDto.getAvatar() != null) {
            user.setAvatar(adminUpdateUserDto.getAvatar());
        }
        if (adminUpdateUserDto.getEmployeeCode() != null) {
            user.setEmployeeCode(adminUpdateUserDto.getEmployeeCode());
        }
        if (adminUpdateUserDto.getAuthorities() != null && !adminUpdateUserDto.getAuthorities().isEmpty()) {
            Set<String> requestedRoles = adminUpdateUserDto.getAuthorities();
            // You might want to validate these roles against the Role table
            user.setAuthorities(requestedRoles);
        }

        return userRepository.save(user);
    }

    private UserProfileDto convertToUserProfileDto(User user) {
        Set<String> authorityNames = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return UserProfileDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .balance(user.getBalance())
                .phoneNumber(user.getPhoneNo())
                .avatar(user.getAvatar())
                .team(user.getTeam())
                .bio(user.getBio())
                .birthday(user.getBirthday() != null ? user.getBirthday().atStartOfDay() : null)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .authorities(authorityNames)
                .employeeCode(user.getEmployeeCode())
                .displayName(user.getDisplayName())
                .build();
    }
}
