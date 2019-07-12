package com.xcy.video.service.impl;


import com.xcy.video.mapper.UserMapper;
import com.xcy.video.pojo.User;
import com.xcy.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


//@Transactional// 在一个类上加上该注解，表示该类中所有方法，都添加事务管理
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    public boolean validateEmail(String email) {

        int count = userMapper.selectUserByEmail(email);
        return count > 0 ? true :false ;
    }


    //@Transactional(propagation= Propagation.REQUIRED) //一个方法上如果添加了注解，表示该方法进行spring的事务管理

    public int insertUser(User user) {
        user.setCreatetime(new Date());
        return userMapper.insertUser(user);
    }


    public boolean loginUser(User user) {

        int result = userMapper.loginUser(user);
        return result >0? true : false;
    }

    public User selectUserByEmail(String email) {
        return userMapper.queryUserByEmail(email);

    }

    public void updateUserById(User user) {
        userMapper.updateUserById(user);
    }

    public void updateUserImageByEmail(String fileName, String email) {
        User user =new User();
        user.setImgurl(fileName);
        user.setEmail(email);
        userMapper.updateUserImageByEmail(user);
    }

    public void updateUserValidateCodeByEmail(User user) {
        userMapper.updateUserValidateCodeByEmail(user);
    }

    public boolean validateEmailCode(User user) {

        int count =userMapper.validateEmailCode(user);
        return count > 0 ? true:false;
    }


}
