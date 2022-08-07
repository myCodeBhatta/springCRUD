package com.anand.springCRUD.services;


import com.anand.springCRUD.model.Role;
import com.anand.springCRUD.model.User;

import com.anand.springCRUD.repository.RoleRepository;
import com.anand.springCRUD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void registerDefaultUser(User user) {
        Role roleUser = roleRepo.findByName("User");
        user.addRole(roleUser);
        encodePassword(user);
        userRepository.save(user);
    }

    public List<Role> listRoles() {
        return roleRepo.findAll();
    }

    public User get(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}
