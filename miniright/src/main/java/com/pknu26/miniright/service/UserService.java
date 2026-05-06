package com.pknu26.miniright.service;

import com.pknu26.miniright.dto.User;
import com.pknu26.miniright.validation.UserJoinForm;
import com.pknu26.miniright.validation.UserLoginForm;

public interface UserService {

    void join(UserJoinForm form);

    User login(UserLoginForm form);

    boolean isLoginIdDuplicated(String loginId);

}
