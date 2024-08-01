package com.example.nodebackend.data.dao;

import com.example.nodebackend.data.entity.User;


public interface SignDao {
    User SignUp(User user);
    void deleteUser(String phone_num)throws Exception;
}
