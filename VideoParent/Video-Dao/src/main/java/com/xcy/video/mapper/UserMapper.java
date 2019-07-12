package com.xcy.video.mapper;


import com.xcy.video.pojo.User;

public interface UserMapper {
    int selectUserByEmail(String email);

    //新增，修改，删除操作，默认都是有返回值的，虽然我们在mapper.xml中没有定义返回值类型
    // 但是可以取到该值
    int insertUser(User user);

    int loginUser(User user);

    User queryUserByEmail(String email);

    void updateUserById(User user);

    void updateUserImageByEmail(User user);

    void updateUserValidateCodeByEmail(User user);

    int validateEmailCode(User user);
}
