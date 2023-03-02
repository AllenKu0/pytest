package com.example.pytest.Auth.payload;

import com.example.pytest.Auth.payload.AuthenticationRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationSuccessesRes extends AuthenticationRes {
    public String token;
}
