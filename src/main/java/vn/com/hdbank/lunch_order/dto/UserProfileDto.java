package vn.com.hdbank.lunch_order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
    private String name;
    private String username;
    private String email;
    private BigDecimal balance;
    private String phoneNumber;
    private String avatar;
    private String team;
    private String bio;
    private LocalDateTime birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<String> authorities;
    private String employeeCode;
    private String displayName;
}
