package com.example.pytest.Auth.payload;

import com.example.pytest.util.validator.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "帳號不得為空")
    public String account;
    @NotBlank(message = "電話不得為空")
    @Phone
    public String phone;
    @Email(message = "email 不得為空")
    public String email;
    @NotBlank(message = "密碼不得為空")
    public String password;

}
