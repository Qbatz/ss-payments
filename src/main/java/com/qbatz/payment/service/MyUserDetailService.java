package com.qbatz.payment.service;

import com.qbatz.payment.config.ServicePrinciple;
import com.qbatz.payment.config.UserPrinciple;
import com.qbatz.payment.dao.Credentials;
import com.qbatz.payment.dao.Users;
import com.qbatz.payment.repositories.CredentialsRepository;
import com.qbatz.payment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository usersRepository;
    @Autowired
    private CredentialsRepository credentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        Users user = usersRepository.findUserByUserId(username);
//
//        if (user == null) {
//            return null;
//        }
//
//        return new UserPrinciple(user);

        Credentials credential = credentialsRepository.findByService(username);

        if (credential == null) {
            return null;
        }

        return new ServicePrinciple(credential);
    }
}
