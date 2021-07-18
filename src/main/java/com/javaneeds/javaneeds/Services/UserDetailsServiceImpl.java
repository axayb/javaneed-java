package com.javaneeds.javaneeds.Services;

import java.util.Optional;

import com.javaneeds.javaneeds.Repository.UserRepository;
import com.javaneeds.javaneeds.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
	UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User>  user = userRepository.findByUsername(username);
        if (user.isEmpty()) user = userRepository.findByEmail(username);
        return UserDetailsImpl.build(user.get());
    }
    
}
