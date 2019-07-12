package com.xcy.video.service.impl;


import com.xcy.video.mapper.AdminMapper;
import com.xcy.video.pojo.Admin;
import com.xcy.video.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;


    public boolean validateLogin(Admin admin) {

        int result = adminMapper.validateLogin(admin);
        return result >0 ? true:false;
    }
}
