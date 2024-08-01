package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.SignDao;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SignDaoImpl implements SignDao {

    private final UserRepository userRepository;

    @Override
    public User SignUp(User user) {
        User signUpUser = userRepository.save(user);
        return signUpUser;
    }

    @Override
    public void deleteUser(String phoneNum) throws Exception{
        User selectUser = userRepository.findByPhoneNum(phoneNum);

        if(selectUser!=null){

            userRepository.delete(selectUser);
        }else{
            throw new Exception();

        }
    }
}
