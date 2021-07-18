package com.javaneeds.javaneeds.seeders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.javaneeds.javaneeds.Repository.RoleRepository;
import com.javaneeds.javaneeds.Repository.UserRepository;
import com.javaneeds.javaneeds.models.ERole;
import com.javaneeds.javaneeds.models.Role;
import com.javaneeds.javaneeds.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
public class DatabaseSeeder {

@Autowired
RoleRepository roleRepository;

@Autowired
UserRepository userRepository;

@Autowired
PasswordEncoder encoder;


@EventListener
public void seed(ContextRefreshedEvent event) {
    seedRolesTable();
    seedUserTable();
    
}
private void seedRolesTable() {
    List<Role> roles =  roleRepository.findAll();
    if(roles == null || roles.size() <= 0) {
        Role r_user = new Role();
        r_user.setName(ERole.ROLE_USER);
        roleRepository.save(r_user);

        Role r_admin = new Role();
        r_admin.setName(ERole.ROLE_ADMIN);
        roleRepository.save(r_admin);
    }
}
private void seedUserTable() {
    List<User> users =  userRepository.findAll();
    if(users == null || users.size() <= 0) {
      
        Set<Role> roles  =new  HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);

		roles.add(userRole);
        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setPassword(encoder.encode("123456"));
        admin.setUsername("admin");
        admin.setRoles(roles);
        userRepository.save(admin);
    }
}
}