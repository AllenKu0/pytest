package com.example.pytest.Auth;

import com.example.pytest.Auth.payload.AuthenticationRequest;
import com.example.pytest.Auth.payload.AuthenticationRes;
import com.example.pytest.Auth.payload.RegisterRequest;
import com.example.pytest.Model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "驗證API", description = "驗證API")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(summary = "註冊", description = "輸入使用者資訊進行註冊")
    @Parameters({
            @Parameter(name = "account", description = "帳號", required = true),
            @Parameter(name = "phone", description = "電話", required = true),
            @Parameter(name = "email", description = "電子郵件", required = true),
            @Parameter(name = "password", description = "密码", required = true)
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationRes> register(
            @RequestBody @Validated RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @Operation(summary = "註冊確認", description = "輸入使用者確認是否註冊")
    @Parameters({
            @Parameter(name = "account", description = "帳號", required = true),
            @Parameter(name = "phone", description = "電話", required = true),
            @Parameter(name = "email", description = "電子郵件", required = true),
            @Parameter(name = "password", description = "密碼", required = true),
            @Parameter(name = "role", description = "角色", required = false)
    })

    @PostMapping("/register/check")
    public ResponseEntity<?> registerCheck(@RequestPart("user") User user) {
        try {
            return ResponseEntity.ok(service.registerCheck(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "驗證", description = "登入驗證")
    @Parameters({
            @Parameter(name = "account", description = "帳號", required = true),
            @Parameter(name = "phone", description = "電話", required = true),
    })

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationRes> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
