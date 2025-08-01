package vn.com.hdbank.lunch_order.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileDto {
    private String name;
    private String email;
    private String phoneNo;
    private String bio;
    private String team;
    private LocalDate birthday;
    private String avatar;
    private String employeeCode;
    private String displayName;
}
