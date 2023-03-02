package com.example.pytest.Auth;

import com.example.pytest.Auth.payload.*;
import com.example.pytest.Model.Role;
import com.example.pytest.Model.User;
import com.example.pytest.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationRes register(RegisterRequest request) {


        var user = User.builder()
                .account(request.getAccount())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        Optional<User> userOptional = repository.findUserByAccount(user.getAccount());
        //是否已註冊
        if(userOptional.isPresent()){
            AuthenticationFailRes failRes = new AuthenticationFailRes();
            failRes.setStatus(1);
            failRes.setMessage("This Account Has Register");
            return failRes;
        }

        //信箱手機擇一
        if ((Objects.isNull(user.getEmail()) || user.getEmail().isEmpty())
                && ( Objects.isNull(user.getPhone()) || user.getPhone().isEmpty())){
            AuthenticationFailRes failRes = new AuthenticationFailRes();
            failRes.setStatus(1);
            failRes.setMessage("Email and Phone Can Not Be Null");
            return failRes;
        } else {
            AuthenticationSuccessesRes successesRes = new AuthenticationSuccessesRes();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            successesRes.setStatus(0);
            successesRes.setToken(jwtToken);
            return successesRes;
        }
    }

    public AuthenticationRes authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        var jwtToken = jwtService.generateToken(user);

        AuthenticationSuccessesRes res = new AuthenticationSuccessesRes();
        res.setStatus(1);
        res.setToken(jwtToken);
        return res;
    }

    public AuthenticationRes registerCheck(User user){
        Optional<User> userOptional = repository.findUserByAccount(user.getAccount());
        if(userOptional.isPresent()){
            return new AuthenticationRes(0);
//            return new Status(0,"This Account Has Registered");
        } else {
            return new AuthenticationRes(1);
        }
    }
}
