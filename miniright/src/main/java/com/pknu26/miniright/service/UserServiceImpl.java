package com.pknu26.miniright.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pknu26.miniright.dto.User;
import com.pknu26.miniright.mapper.UserMapper;
import com.pknu26.miniright.validation.UserJoinForm;
import com.pknu26.miniright.validation.UserLoginForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Override
    public void join(UserJoinForm form) {
        String loginId = form.getLoginId().trim();

        if (isLoginIdDuplicated(loginId)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            throw new IllegalArgumentException("패스워드와 패스워드 확인이 일치하지 않습니다.");
        }

        User user = new User();
        user.setLoginId(loginId);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setName(form.getName().trim());
        user.setRole("ROLE_USER");

        try {
            this.userMapper.insertUser(user);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("이미 사용 중인 아이디이거나 회원 번호가 중복되었습니다. DB 시퀀스를 확인해주세요.");
        }
    }

    // 로그인
    @Override
    public User login(UserLoginForm form) {
        String loginId = form.getLoginId().trim();

        User user = this.userMapper.findByLoginId(loginId);

        if (user == null) {
            return null;
        }

        boolean matches = passwordEncoder.matches(form.getPassword(), user.getPassword());

        if (!matches) {
            return null;
        }

        return user;
    }

    // 아이디 중복 확인
    @Override
    public boolean isLoginIdDuplicated(String loginId) {
        return this.userMapper.findByLoginId(loginId) != null;
    }
}