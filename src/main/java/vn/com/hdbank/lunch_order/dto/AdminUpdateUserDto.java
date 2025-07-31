package vn.com.hdbank.lunch_order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class AdminUpdateUserDto {
    private String name;
    private String displayName;
    private BigDecimal balance;
    private String email;
    private String phoneNo;
    private String bio;
    private String team;
    private LocalDate birthday;
    private String avatar;
    private String employeeCode;
    private Set<String> authorities;
}
