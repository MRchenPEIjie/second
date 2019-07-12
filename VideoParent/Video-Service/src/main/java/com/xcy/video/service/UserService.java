package com.xcy.video.service;


import com.xcy.video.pojo.User;

public interface UserService {
    boolean validateEmail(String email);

    int insertUser(User user);

    boolean loginUser(User user);

    User selectUserByEmail(String email);

    void updateUserById(User user);

    void updateUserImageByEmail(String s, String email);

    void updateUserValidateCodeByEmail(User user);

    boolean validateEmailCode(User user);
}
