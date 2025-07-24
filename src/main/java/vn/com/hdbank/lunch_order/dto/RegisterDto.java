package vn.com.hdbank.lunch_order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Size(min = 6, max = 50, message = "Mật khẩu phải từ 6 đến 50 ký tự")
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

}