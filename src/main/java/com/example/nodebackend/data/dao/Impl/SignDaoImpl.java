package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.SignDao;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignDaoImpl implements SignDao {

    private final UserRepository userRepository;

    @Override
    public User SignUp(User user) {
        User signUpUser = userRepository.save(user);
        return signUpUser;
    }

}
