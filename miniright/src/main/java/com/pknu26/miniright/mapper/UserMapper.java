package com.pknu26.miniright.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.pknu26.miniright.dto.User;

@Mapper
public interface UserMapper {

    User findByLoginId(String loginId);

    int insertUser(User user);

}
